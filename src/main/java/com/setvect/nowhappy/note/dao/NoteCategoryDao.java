package com.setvect.nowhappy.note.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.note.service.NoteCategorySearch;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;

/**
 * 노트 카테고리
 * 
 * @version $Id$
 */
public interface NoteCategoryDao {

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
}