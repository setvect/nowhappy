package com.setvect.nowhappy.comment.service;

import com.setvect.common.util.SearchListVo;

/**
 * 코멘트 검색 조건
 * 
 * @version $Id$
 */
public class CommentSearch extends SearchListVo {
	/** */
	private static final long serialVersionUID = -996725466547025276L;

	private final CommentModule moduleName;

	public CommentSearch(int currentPage, CommentModule moduleName) {
		super(currentPage);
		this.moduleName = moduleName;
	}

	public CommentModule getModuleName() {
		return moduleName;
	}

}
