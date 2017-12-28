package com.setvect.nowhappy.note.repository;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.note.service.NoteCategorySearch;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;

/**
 * 노트 카테고리
 */
public interface NoteCategoryRepositoryCustom {

	/**
	 * @param pageCondition
	 * @return 게시판생성 정보 항목
	 */
	public GenericPage<NoteCategoryVo> getNoteCategoryPagingList(NoteCategorySearch pageCondition);

}