package com.setvect.nowhappy.note.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.note.dao.NoteCategoryDao;
import com.setvect.nowhappy.note.service.NoteCategorySearch;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;

/**
 * 복슬노트 카테고리 
 */
public class AbstractNoteCategoryDao implements NoteCategoryDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public NoteCategoryVo getNoteCategory(int NoteCategoryVo) {
		Session session = sessionFactory.getCurrentSession();
		return (NoteCategoryVo) session.get(NoteCategoryVo.class, NoteCategoryVo);
	}

	@Override
	public GenericPage<NoteCategoryVo> getNoteCategoryPagingList(NoteCategorySearch pageCondition) {
		Session session = sessionFactory.getCurrentSession();

		String q = "select count(*) from NoteCategoryVo " + getCategoryWhereClause(pageCondition);
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from NoteCategoryVo " + getCategoryWhereClause(pageCondition) + " order by name ";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<NoteCategoryVo> resultList = query.list();

		GenericPage<NoteCategoryVo> resultPage = new GenericPage<NoteCategoryVo>(resultList, pageCondition.getStartCursor(),
				totalCount, pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getCategoryWhereClause(NoteCategorySearch pageCondition) {
		String where = " where deleteF = 'N' ";
		return where;
	}

	@Override
	public void insertNoteCategory(NoteCategoryVo noteCategory) {
		Session session = sessionFactory.getCurrentSession();
		session.save(noteCategory);
		session.flush();
	}

	@Override
	public void updateNoteCategory(NoteCategoryVo noteCategory) {
		Session session = sessionFactory.getCurrentSession();
		session.update(noteCategory);
		session.flush();
	}

	@Override
	public void deleteNoteCategory(int categorySeq) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getNoteCategory(categorySeq));
		session.flush();
	}

}
