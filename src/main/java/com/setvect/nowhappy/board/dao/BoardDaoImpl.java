package com.setvect.nowhappy.board.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.board.service.BoardManagerSearch;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시물 DAO
 */
class BoardDaoImpl implements BoardDaoCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<BoardVo> getBoardPagingList(BoardManagerSearch pageCondition) {
		String q = "select count(*) from BoardVo " + getManagerWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select b from BoardVo b " + getManagerWhereClause(pageCondition) + " order by b.boardCode ";
		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<BoardVo> resultList = query.getResultList();

		GenericPage<BoardVo> resultPage = new GenericPage<BoardVo>(resultList, pageCondition.getStartCursor(), totalCount,
				pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getManagerWhereClause(BoardManagerSearch pageCondition) {
		String where = "where b.deleteF = 'N'";

		// 두개가 동새에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(pageCondition.getSearchCode())) {
			where += " and b.boardCode like " + StringUtilAd.getSqlStringLike(pageCondition.getSearchCode());
		} else if (!StringUtilAd.isEmpty(pageCondition.getSearchName())) {
			where += " and b.name like " + StringUtilAd.getSqlStringLike(pageCondition.getSearchName());
		}
		return where;
	}

}