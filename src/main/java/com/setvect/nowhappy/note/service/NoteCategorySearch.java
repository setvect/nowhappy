package com.setvect.nowhappy.note.service;

import com.setvect.common.util.SearchListVo;

/**
 * 노트 카테고리 페이지 검색 조건
 * 
 * @version $Id$
 */
public class NoteCategorySearch extends SearchListVo {

	public NoteCategorySearch(int startCursor, int returnCount) {
		super(startCursor, returnCount);
	}

}
