package com.setvect.nowhappy.note.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.note.service.NoteSearch;
import com.setvect.nowhappy.note.vo.NoteVo;

/**
 * 노트 내용
 * 
 * @version $Id$
 */
public interface NoteDao {
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
	public void deleteNote(int noteSeq);
}