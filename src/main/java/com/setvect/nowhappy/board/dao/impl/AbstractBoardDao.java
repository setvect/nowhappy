package com.setvect.nowhappy.board.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.board.dao.BoardDao;
import com.setvect.nowhappy.board.service.BoardArticleSearch;
import com.setvect.nowhappy.board.service.BoardManagerSearch;
import com.setvect.nowhappy.board.vo.BoardArticleVo;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시물 DAO
 * 
 * @version $Id: AbstractBoardDao.java 121 2010-10-03 05:59:11Z
 *          setvect@naver.com $
 */
public abstract class AbstractBoardDao implements BoardDao {
	@Autowired
	SessionFactory sessionFactory;

	// --------------- 관리
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
				totalCount);
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

	// --------------- 게시물
	@Override
	public BoardArticleVo getArticle(int articleSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (BoardArticleVo) session.get(BoardArticleVo.class, articleSeq);
	}

	// TODO 목록 검색시 불필요한 항목(내용 TEXT)까지 가져오는 경우 발생. 성능 문제 발생시 수정
	@Override
	public GenericPage<BoardArticleVo> getArticlePagingList(BoardArticleSearch pageCondtion) {
		Session session = sessionFactory.getCurrentSession();

		String q = "select count(*) from BoardArticleVo " + getArticleWhereClause(pageCondtion);
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from BoardArticleVo " + getArticleWhereClause(pageCondtion) + getOrder(pageCondtion);

		query = session.createQuery(q);
		query.setFirstResult(pageCondtion.getStartCursor());
		query.setMaxResults(pageCondtion.getReturnCount());

		@SuppressWarnings("unchecked")
		List<BoardArticleVo> resultList = query.list();

		GenericPage<BoardArticleVo> resultPage = new GenericPage<BoardArticleVo>(resultList,
				pageCondtion.getStartCursor(), totalCount);
		return resultPage;
	}

	/**
	 * 정렬 조건
	 * 
	 * @param search
	 *            검색 조건
	 * @return
	 */
	private String getOrder(BoardArticleSearch search) {
		// 게시판 두개 이상인 경우 계층형 구조로 정렬은 하지 않고 입력순 으로 정렬
		if (search.getSearchCodes() != null) {
			return " order by articleSeq desc";
		}
		else {
			return " order by IDX2 desc, IDX3 ASC ";
		}
	}

	/**
	 * @param search
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getArticleWhereClause(BoardArticleSearch search) {
		String where = " where ";
		List<String> codes = search.getSearchCodes();
		if (codes == null) {
			where += " boardCode = " + StringUtilAd.getSqlString(search.getSearchCode());
		}
		else {
			where += " boardCode in ( '___dummyCode'";
			for (String c : codes) {
				where += "," + StringUtilAd.getSqlString(c);
			}
			where += " ) ";
		}

		if (!search.isDeleteView()) {
			// 삭제 게시물 보여 주지 않음
			where += " and deleteF = 'N' ";
		}

		// 두개 이상 동시에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(search.getSearchName())) {
			where += " and name like " + StringUtilAd.getSqlStringLike(search.getSearchName());
		}
		else if (!StringUtilAd.isEmpty(search.getSearchTitle())) {
			where += " and title like " + StringUtilAd.getSqlStringLike(search.getSearchTitle());
		}
		else if (!StringUtilAd.isEmpty(search.getSearchContent())) {
			where += " and content like " + StringUtilAd.getSqlStringLike(search.getSearchContent());
		}
		return where;
	}

	@Override
	public void insertArticle(BoardArticleVo article) {
		Session session = sessionFactory.getCurrentSession();
		String q;

		// 인덱스 순서
		q = "select COALESCE(max(idx1) + 1, 1)  from BoardArticleVo WHERE boardCode = ?";
		Query query = session.createQuery(q);
		query.setParameter(0, article.getBoardCode());
		int idx1 = ((Integer) query.uniqueResult()).intValue();
		article.setIdx1(idx1);

		q = "select COALESCE(max(idx2) + 1, 1) from BoardArticleVo WHERE boardCode = ?";
		query = session.createQuery(q);
		query.setParameter(0, article.getBoardCode());
		int idx2 = ((Integer) query.uniqueResult()).intValue();
		article.setIdx2(idx2);

		// 기본 값
		article.setIdx3(1);
		article.setDepthLevel(1);

		session.save(article);
		session.flush();
	}

	@Override
	public void insertArticleReply(BoardArticleVo article, int parentId) {
		Session session = sessionFactory.getCurrentSession();

		// IDX1
		String q = "select COALESCE(max(IDX1) + 1, 1) AS CNT from BoardArticleVo WHERE boardCode = ?";
		Query query = session.createQuery(q);
		query.setParameter(0, article.getBoardCode());
		article.setIdx1(((Integer) query.uniqueResult()).intValue());

		BoardArticleVo target = getArticle(parentId);

		// IDX2
		article.setIdx2(target.getIdx2());

		// 게시물의 다음 번호
		article.setIdx3(target.getIdx3() + 1);

		// 답글 대상 글 깊이 + 1
		article.setDepthLevel(target.getDepthLevel() + 1);

		// 현재 답변이 달릴 게시판으로 부터 이하의 이전 답변 게시판에 idx3를 +1를 해준다.
		q = " UPDATE BoardArticleVo SET                                 "
				+ " 	idx3 = idx3 + 1                               "
				+ " WHERE	boardCode = ? AND idx2 = ? AND idx3 >=	? ";
		query = session.createQuery(q);
		query.setParameter(0, article.getBoardCode());
		query.setParameter(1, new Integer(article.getIdx2()));
		query.setParameter(2, new Integer(article.getIdx3()));
		query.executeUpdate();

		session.save(article);
		session.flush();
	}

	@Override
	public void updateArticle(BoardArticleVo article) {
		Session session = sessionFactory.getCurrentSession();
		session.update(article);
		session.flush();
	}

	@Override
	public void updateArticleIncrementHit(int articleSeq) {
		Session session = sessionFactory.getCurrentSession();
		// IDX1
		String q = "update BoardArticleVo set  hitCount = hitCount + 1 WHERE articleSeq = ?";
		Query query = session.createQuery(q);
		query.setParameter(0, articleSeq);
		query.executeUpdate();
		session.flush();
	}

	@Override
	public void deleteArticle(int articleSeq) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getArticle(articleSeq));
		session.flush();
	}

}