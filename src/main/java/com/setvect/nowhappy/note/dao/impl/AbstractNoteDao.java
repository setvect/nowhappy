package com.setvect.nowhappy.note.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.board.service.BoardManagerSearch;
import com.setvect.nowhappy.board.vo.BoardVo;
import com.setvect.nowhappy.note.dao.NoteDao;
import com.setvect.nowhappy.note.service.NoteCategorySearch;
import com.setvect.nowhappy.note.service.NoteSearch;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;
import com.setvect.nowhappy.note.vo.NoteVo;

public class AbstractNoteDao implements NoteDao {
	@Autowired
	private SessionFactory sessionFactory;

	// --------------- 카테고리
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

		q = " from NoteCategoryVo " + getCategoryWhereClause(pageCondition) + " order by categorySeq ";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<NoteCategoryVo> resultList = query.list();

		GenericPage<NoteCategoryVo> resultPage = new GenericPage<NoteCategoryVo>(resultList,
				pageCondition.getStartCursor(), totalCount, pageCondition.getReturnCount());
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

	// --------------- 내용
	@Override
	public NoteVo getNote(int noteSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (NoteVo) session.get(NoteVo.class, noteSeq);
	}

	@Override
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition) {
		Session session = sessionFactory.getCurrentSession();

		String q = "select count(*) from NoteVo " + getNoteWhereClause(pageCondition);
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from NoteVo " + getNoteWhereClause(pageCondition) + " order by uptDate desc ";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<NoteVo> resultList = query.list();

		GenericPage<NoteVo> resultPage = new GenericPage<NoteVo>(resultList, pageCondition.getStartCursor(),
				totalCount, pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getNoteWhereClause(NoteSearch search) {
		String where = " where ";

		// 삭제 게시물 보여 주지 않음
		where += " and deleteF = 'N' ";

		if (search.getSearchCategorySeq() != 0) {
			where += " and categorySeq = " + search.getSearchCategorySeq();
		}

		// 두개 이상 동시에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(search.getSearchTitle())) {
			where += " and title like " + StringUtilAd.getSqlStringLike(search.getSearchTitle());
		}
		else if (!StringUtilAd.isEmpty(search.getSearchContent())) {
			where += " and content like " + StringUtilAd.getSqlStringLike(search.getSearchContent());
		}
		return where;
	}

	@Override
	public void insertNote(NoteVo note) {
		Session session = sessionFactory.getCurrentSession();
		session.save(note);
		session.flush();

	}

	@Override
	public void updateNote(NoteVo note) {
		Session session = sessionFactory.getCurrentSession();
		session.update(note);
		session.flush();
	}

	@Override
	public void deleteArticle(int noteSeq) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getNote(noteSeq));
		session.flush();
	}

}
