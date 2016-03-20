package com.setvect.nowhappy.note.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.note.service.NoteSearch;
import com.setvect.nowhappy.note.vo.NoteVo;

/**
 * 노트 내용
 *
 * @version $Id$
 */
public interface NoteDao extends JpaRepository<NoteVo, Integer>, NoteDaoCustom {
	/**
	 * @param pageCondition
	 *            노트 검색 정보
	 * @return 노트 목록
	 */
	@Override
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition);
}