package com.setvect.nowhappy.comment.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.comment.service.CommentSearch;
import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 코멘트
 */
public class CommentDaoImpl implements CommentDaoCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<Comment> getCommentPagingList(CommentSearch pageCondition) {
		String q = "select count(*) from Comment c" + getWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select c from Comment c " + getWhereClause(pageCondition) + " order by c.commentSeq desc";
		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<Comment> resultList = query.getResultList();
		GenericPage<Comment> resultPage = new GenericPage<Comment>(resultList, pageCondition.getStartCursor(), totalCount);

		return resultPage;
	}

	private String getWhereClause(CommentSearch pageCondition) {
		String where = " where c.moduleName = " + StringUtilAd.getSqlString(pageCondition.getModuleName().name());
		return where;
	}

}