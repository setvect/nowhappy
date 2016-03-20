package com.setvect.nowhappy.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.note.dao.NoteCategoryDao;
import com.setvect.nowhappy.note.dao.NoteDao;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;
import com.setvect.nowhappy.note.vo.NoteVo;

/**
 * @version $Id$
 */
@Service("NoteService")
public class NoteService {

	@Autowired
	private NoteDao noteDao;
	@Autowired
	private NoteCategoryDao noteCategoryDao;

	// --------------- 관리

	/**
	 * @param categorySeq
	 *            일련번호
	 * @return 정보
	 */
	public NoteCategoryVo getNoteCategory(int categorySeq) {
		return noteCategoryDao.findOne(categorySeq);
	}

	/**
	 * @param pageCondition
	 * @return 정보 항목
	 */
	public GenericPage<NoteCategoryVo> getNoteCategoryPagingList(NoteCategorySearch pageCondition) {

		return noteCategoryDao.getNoteCategoryPagingList(pageCondition);
	}

	/**
	 * @param noteCategory
	 */
	public void insertNoteCategory(NoteCategoryVo noteCategory) {
		noteCategoryDao.save(noteCategory);
	}

	/**
	 * @param noteCategory
	 */
	public void updateNoteCategory(NoteCategoryVo noteCategory) {
		noteCategoryDao.save(noteCategory);
	}

	/**
	 * @param code
	 *            일련번호
	 */
	public void deleteBoard(int categorySeq) {
		noteCategoryDao.delete(categorySeq);
	}

	// --------------- 노트
	/**
	 * @param noteSeq
	 * @return
	 */
	public NoteVo getNote(int noteSeq) {
		return noteDao.findOne(noteSeq);
	}

	/**
	 * @param pageCondition
	 *            검색 정보
	 * @return 노트
	 */
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition) {
		return noteDao.getNotePagingList(pageCondition);
	}

	/**
	 * 노트 등록
	 *
	 * @param article
	 *            노트
	 */
	public void insertNote(NoteVo article) {
		noteDao.save(article);
	}

	/**
	 * @param article
	 */
	public void updateNote(NoteVo article) {
		noteDao.save(article);
	}

	/**
	 * @param articleSeq
	 */
	public void deleteNote(int noteSeq) {
		noteDao.delete(noteSeq);
	}
}
