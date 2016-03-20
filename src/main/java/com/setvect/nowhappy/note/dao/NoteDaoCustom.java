package com.setvect.nowhappy.note.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.note.service.NoteSearch;
import com.setvect.nowhappy.note.vo.NoteVo;

/**
 * 노트 내용
 */
public interface NoteDaoCustom {

	/**
	 * @param pageCondition
	 *            노트 검색 정보
	 * @return 노트 목록
	 */
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition);

}