package com.setvect.nowhappy.board.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.board.service.BoardArticleSearch;
import com.setvect.nowhappy.board.vo.BoardArticleVo;

/**
 * 게시물 DAO
 *
 * @version $Id: AbstractBoardDao.java 121 2010-10-03 05:59:11Z
 *          setvect@naver.com $
 */
public class BoardArticleDaoImpl implements BoardArticleDaoCustom {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private BoardArticleDao boardArticle;

	// TODO 목록 검색시 불필요한 항목(내용 TEXT)까지 가져오는 경우 발생. 성능 문제 발생시 수정
	@Override
	public GenericPage<BoardArticleVo> getArticlePagingList(BoardArticleSearch pageCondition) {

		String q = "select count(*) from BoardArticleVo b " + getArticleWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select b from BoardArticleVo b " + getArticleWhereClause(pageCondition) + getOrder(pageCondition);

		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<BoardArticleVo> resultList = query.getResultList();

		GenericPage<BoardArticleVo> resultPage = new GenericPage<BoardArticleVo>(resultList, pageCondition.getStartCursor(),
				totalCount, pageCondition.getReturnCount());
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
			return " order by b.articleSeq desc";
		} else {
			return " order by b.idx2 desc, b.idx3 ASC ";
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
			where += " b.boardCode = " + StringUtilAd.getSqlString(search.getSearchCode());
		} else {
			where += " b.boardCode in ( '___dummyCode'";
			for (String c : codes) {
				where += "," + StringUtilAd.getSqlString(c);
			}
			where += " ) ";
		}

		if (!search.isDeleteView()) {
			// 삭제 게시물 보여 주지 않음
			where += " and b.deleteF = 'N' ";
		}

		// 두개 이상 동시에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(search.getSearchName())) {
			where += " and b.name like " + StringUtilAd.getSqlStringLike(search.getSearchName());
		} else if (!StringUtilAd.isEmpty(search.getSearchTitle())) {
			where += " and b.title like " + StringUtilAd.getSqlStringLike(search.getSearchTitle());
		} else if (!StringUtilAd.isEmpty(search.getSearchContent())) {
			where += " and b.content like " + StringUtilAd.getSqlStringLike(search.getSearchContent());
		}
		return where;
	}

	@Override
	public void insertArticleReply(BoardArticleVo article, int parentId) {

		// IDX1
		String q = "select COALESCE(max(IDX1) + 1, 1) AS CNT from BoardArticleVo WHERE boardCode = ?";
		Query query = em.createQuery(q);
		query.setParameter(0, article.getBoardCode());
		article.setIdx1(((Integer) query.getSingleResult()).intValue());

		BoardArticleVo target = boardArticle.findOne(parentId);

		// IDX2
		article.setIdx2(target.getIdx2());

		// 게시물의 다음 번호
		article.setIdx3(target.getIdx3() + 1);

		// 답글 대상 글 깊이 + 1
		article.setDepthLevel(target.getDepthLevel() + 1);

		// 현재 답변이 달릴 게시판으로 부터 이하의 이전 답변 게시판에 idx3를 +1를 해준다.
		q = " UPDATE BoardArticleVo b SET                                 " //
				+ " 	idx3 = idx3 + 1                                       " //
				+ " WHERE	b.boardCode = ? AND b.idx2 = ? b.AND idx3 >=	? ";
		query = em.createQuery(q);
		query.setParameter(0, article.getBoardCode());
		query.setParameter(1, new Integer(article.getIdx2()));
		query.setParameter(2, new Integer(article.getIdx3()));
		query.executeUpdate();

		em.persist(article);
	}

}