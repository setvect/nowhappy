package com.setvect.nowhappy.knowledge.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.knowledge.service.KnowledgeSearch;
import com.setvect.nowhappy.knowledge.vo.KnowledgeVo;

/**
 * 게시물 Repository
 */
public class KnowledgeRepositoryImpl implements KnowledgeRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private KnowledgeRepository KnowledgeRepository;

	@Override
	public GenericPage<KnowledgeVo> getKnowledgePagingList(KnowledgeSearch pageCondition) {
		String q = "select count(*) from KnowledgeVo b " + getArticleWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select b from KnowledgeVo b " + getArticleWhereClause(pageCondition) + " order by knowledgeSeq desc";

		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<KnowledgeVo> resultList = query.getResultList();

		GenericPage<KnowledgeVo> resultPage = new GenericPage<KnowledgeVo>(resultList, pageCondition.getStartCursor(), totalCount,
				pageCondition.getReturnCount());
		return resultPage;
	}

	private String getArticleWhereClause(KnowledgeSearch search) {
		String where = " where 1=1 ";

		// 두개 이상 동시에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(search.getSearchTitle())) {
			where += " and b.title like " + StringUtilAd.getSqlStringLike(search.getSearchTitle());
		} else if (!StringUtilAd.isEmpty(search.getSearchProblem())) {
			where += " and b.problem like " + StringUtilAd.getSqlStringLike(search.getSearchProblem());
		} else if (!StringUtilAd.isEmpty(search.getSearchSolution())) {
			where += " and b.solution like " + StringUtilAd.getSqlStringLike(search.getSearchSolution());
		}
		return where;
	}

}