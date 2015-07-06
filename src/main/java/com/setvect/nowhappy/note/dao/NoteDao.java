package com.setvect.nowhappy.note.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.board.service.BoardArticleSearch;
import com.setvect.nowhappy.board.vo.BoardArticleVo;
import com.setvect.nowhappy.note.service.NoteCategorySearch;
import com.setvect.nowhappy.note.service.NoteSearch;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;
import com.setvect.nowhappy.note.vo.NoteVo;

/**
 * 노트 카테고리, 노트 내용
 * 
 * @version $Id$
 */
public interface NoteDao {

	// --------------- 카테고리

	public NoteCategoryVo getNoteCategory(int categorySeq);

	/**
	 * @param pageCondition
	 * @return 게시판생성 정보 항목
	 */
	public GenericPage<NoteCategoryVo> getNoteCategoryPagingList(NoteCategorySearch pageCondition);

	/**
	 * @param noteCategory
	 */
	public void insertNoteCategory(NoteCategoryVo noteCategory);

	/**
	 * @param noteCategory
	 */
	public void updateNoteCategory(NoteCategoryVo noteCategory);

	/**
	 * @param categorySeq
	 */
	public void deleteNoteCategory(int categorySeq);

	// --------------- 내용
	/**
	 * @param noteSeq
	 * @return
	 */
	public NoteVo getNote(int noteSeq);

	/**
	 * @param pageCondition
	 *            노트 검색 정보
	 * @return 노트 목록
	 */
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition);

	/**
	 * 노트 등록
	 * 
	 * @param note
	 *            노트
	 */
	public void insertNote(NoteVo note);

	/**
	 * @param note
	 */
	public void updateNote(NoteVo note);

	/**
	 * @param noteSeq
	 */
	public void deleteArticle(int noteSeq);
}