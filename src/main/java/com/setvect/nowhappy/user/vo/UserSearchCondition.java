package com.setvect.nowhappy.user.vo;

import com.setvect.common.util.SearchListVo;

/**
 * 게시물 관리 목록 페이징 및 검색 조건
 */
public class UserSearchCondition extends SearchListVo {

	/** */
	private static final long serialVersionUID = -4496364103835682025L;
	private String searchName;
	private String searchId;

	public UserSearchCondition(int startCursor, int endCursor) {
		super(startCursor, endCursor);
	}

	/**
	 * @return the searchName
	 */
	public String getSearchName() {
		return searchName;
	}

	/**
	 * @param searchName
	 *            the searchName to set
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	/**
	 * @return the searchCode
	 */
	public String getSearchId() {
		return searchId;
	}

	/**
	 * @param searchId
	 *            the searchCode to set
	 */
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
}
