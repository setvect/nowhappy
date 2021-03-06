package com.setvect.nowhappy.comment.service;

import com.setvect.common.util.SearchListVo;

/**
 * 코멘트 검색 조건
 */
public class CommentSearch extends SearchListVo {

	private final CommentModule moduleName;

	public CommentSearch(CommentModule moduleName, int startCursor, int returnCount) {
		super(startCursor, returnCount);
		this.moduleName = moduleName;
	}

	public CommentModule getModuleName() {
		return moduleName;
	}

}
