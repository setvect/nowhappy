package com.setvect.nowhappy.comment.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.comment.service.CommentSearch;
import com.setvect.nowhappy.comment.vo.CommentVo;

/**
 * 코멘트
 */
public class CommentRepositoryImpl implements CommentRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<CommentVo> getCommentPagingList(CommentSearch pageCondition) {
		String q = "select count(*) from CommentVo c" + getWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select c from CommentVo c " + getWhereClause(pageCondition) + " order by c.commentSeq desc";
		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<CommentVo> resultList = query.getResultList();
		GenericPage<CommentVo> resultPage = new GenericPage<CommentVo>(resultList, pageCondition.getStartCursor(), totalCount);

		return resultPage;
	}

	private String getWhereClause(CommentSearch pageCondition) {
		String where = " where c.moduleName = " + StringUtilAd.getSqlString(pageCondition.getModuleName().name());
		return where;
	}

}