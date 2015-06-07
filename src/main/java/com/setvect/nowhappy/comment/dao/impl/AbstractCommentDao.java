package com.setvect.nowhappy.comment.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.comment.dao.CommentDao;
import com.setvect.nowhappy.comment.service.CommentSearch;
import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 코멘트
 * 
 * @version $Id$
 */
public abstract class AbstractCommentDao implements CommentDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Comment getComment(int commentSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (Comment) session.get(Comment.class, commentSeq);
	}

	@Override
	public GenericPage<Comment> getCommentPagingList(CommentSearch pageCondition) {
		Session session = sessionFactory.getCurrentSession();

		String q = "select count(*) from Comment " + getWhereClause(pageCondition);
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from Comment " + getWhereClause(pageCondition) + " order by commentSeq desc";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<Comment> resultList = query.list();
		GenericPage<Comment> resultPage = new GenericPage<Comment>(resultList, pageCondition.getStartCursor(),
				totalCount);

		return resultPage;
	}

	private String getWhereClause(CommentSearch pageCondition) {
		String where = " where moduleName = " + StringUtilAd.getSqlString(pageCondition.getModuleName().name());
		return where;
	}

	@Override
	public void insertComment(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		session.save(comment);
		session.flush();
	}

	@Override
	public void updateComment(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		session.update(comment);
		session.flush();
	}

	public void deleteComment(int commentSeq) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getComment(commentSeq));
		session.flush();
	}
}