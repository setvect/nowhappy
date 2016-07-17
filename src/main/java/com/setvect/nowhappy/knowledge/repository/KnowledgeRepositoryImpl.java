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
		String where = " where b.deleteF = 'N' ";

		String searchWord = search.getSearchWord();
		String searchClassify = search.getSearchClassifyC();

		if (StringUtilAd.isNotEmpty(searchWord)) {
			String wordLikeString = StringUtilAd.getSqlStringLike(searchWord);
			where += " and ( b.problem like " + wordLikeString + " OR b.solution like " + wordLikeString + " )";
		} else if (StringUtilAd.isNotEmpty(searchClassify)) {
			where += " and b.classifyC like " + StringUtilAd.getSqlStringLike(searchClassify);
		}
		return where;
	}

}