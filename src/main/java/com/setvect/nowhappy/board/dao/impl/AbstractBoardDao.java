package com.setvect.nowhappy.board.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.board.dao.BoardDao;
import com.setvect.nowhappy.board.service.BoardManagerSearch;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시물 DAO
 */
public abstract class AbstractBoardDao implements BoardDao {
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public BoardVo getBoard(String code) {
		Session session = sessionFactory.getCurrentSession();
		return (BoardVo) session.get(BoardVo.class, code);
	}

	@Override
	public GenericPage<BoardVo> getBoardPagingList(BoardManagerSearch pageCondition) {

		Session session = sessionFactory.getCurrentSession();

		String q = "select count(*) from BoardVo " + getManagerWhereClause(pageCondition);
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from BoardVo " + getManagerWhereClause(pageCondition) + " order by boardCode ";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<BoardVo> resultList = query.list();

		GenericPage<BoardVo> resultPage = new GenericPage<BoardVo>(resultList, pageCondition.getStartCursor(),
				totalCount, pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getManagerWhereClause(BoardManagerSearch pageCondition) {
		String where = "where deleteF = 'N'";

		// 두개가 동새에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(pageCondition.getSearchCode())) {
			where += " and boardCode like " + StringUtilAd.getSqlStringLike(pageCondition.getSearchCode());
		}
		else if (!StringUtilAd.isEmpty(pageCondition.getSearchName())) {
			where += " and name like " + StringUtilAd.getSqlStringLike(pageCondition.getSearchName());
		}
		return where;
	}

	@Override
	public void insertBoard(BoardVo board) {
		Session session = sessionFactory.getCurrentSession();
		session.save(board);
		session.flush();
	}

	@Override
	public void updateBoard(BoardVo board) {
		Session session = sessionFactory.getCurrentSession();
		session.update(board);
		session.flush();
	}

	@Override
	public void deleteBoard(String code) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getBoard(code));
		session.flush();
	}

}