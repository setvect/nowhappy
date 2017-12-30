package com.setvect.nowhappy.knowledge.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.knowledge.service.KnowledgeSearch;
import com.setvect.nowhappy.knowledge.vo.KnowledgeVo;

/**
 * 게시물 Repository
 */
public class KnowledgeRepositoryImpl implements KnowledgeRepositoryCustom {
	/** 쿼리 실행 */
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<KnowledgeVo> getKnowledgePagingList(final KnowledgeSearch pageCondition) {
		String q = "select count(*) from KnowledgeVo b " + getArticleWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select b from KnowledgeVo b " + getArticleWhereClause(pageCondition) + " order by knowledgeSeq desc";

		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<KnowledgeVo> resultList = query.getResultList();

		GenericPage<KnowledgeVo> resultPage = new GenericPage<KnowledgeVo>(resultList, pageCondition.getStartCursor(),
				totalCount, pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * 검색 조건
	 *
	 * @param search
	 *            검색 조건
	 * @return where절
	 */
	private String getArticleWhereClause(final KnowledgeSearch search) {
		String where = " where b.deleteF = 'N' ";

		String searchWord = search.getSearchWord();
		String searchClassify = search.getSearchClassifyC();

		if (StringUtilAd.isNotEmpty(searchWord)) {
			String wordLikeString = StringUtilAd.getSqlStringLike(searchWord);
			where += " and ( upper(b.problem) like " + wordLikeString.toUpperCase() + " OR upper(b.solution) like "
					+ wordLikeString.toUpperCase() + " )";
		}
		if (StringUtilAd.isNotEmpty(searchClassify)) {
			where += " and b.classifyC = " + StringUtilAd.getSqlString(searchClassify);
		}
		return where;
	}
}