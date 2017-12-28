package com.setvect.nowhappy.board.service;

import com.setvect.common.util.SearchListVo;
import com.setvect.common.util.StringUtilAd;

/**
 * 게시물 관리 목록 페이징 및 검색 조건
 */
public class BoardManagerSearch extends SearchListVo {

	private String searchName;
	private String searchCode;

	public BoardManagerSearch(int startCursor, int returnCount) {
		super(startCursor, returnCount);
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
	public String getSearchCode() {
		return searchCode;
	}

	/**
	 * @param searchCode
	 *            the searchCode to set
	 */
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	/**
	 * @return 검색 단어중 값이 있는 하나를 반환. 아무도 없으면 null. 2개 이상 값이 있을 경우 어떤걸 반환 할지 모름
	 */
	public String getWord() {
		if (!StringUtilAd.isEmpty(searchCode)) {
			return searchCode;
		}
		if (!StringUtilAd.isEmpty(searchName)) {
			return searchName;
		}
		return null;
	}

}
