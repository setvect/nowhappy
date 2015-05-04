package com.setvect.nowhappy.board.service;

import java.util.List;

import com.setvect.common.util.SearchListVo;
import com.setvect.common.util.StringUtilAd;

/**
 * 게시물 관리 목록 페이징 및 검색 조건
 */
public class BoardArticleSearch extends SearchListVo {

	private String searchName;
	private String searchCode;
	/**
	 * 복수의 게시물 조회<br>
	 * 본 필드에 값이 있으면 searchCode 값은 무시함
	 */
	private List<String> searchCodes;
	private String searchTitle;
	private String searchContent;

	/** 삭제된 게시물도 보여 줄 것이지 */
	private boolean deleteView;

	public BoardArticleSearch(int startCursor, int returnCount) {
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
	 * 조회할 게시판 코드<br>
	 * 
	 * @return the searchCode
	 */
	public String getSearchCode() {
		return searchCode;
	}

	/**
	 * 조회할 게시판 코드<br>
	 * 
	 * @param searchCode
	 *            the searchCode to set
	 */
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	/**
	 * 복수의 게시물 조회
	 * 
	 * @return the searchCodes
	 */
	public List<String> getSearchCodes() {
		return searchCodes;
	}

	/**
	 * 복수의 게시물 조회
	 * 
	 * @param searchCodes
	 *            the searchCodes to set
	 */
	public void setSearchCodes(List<String> searchCodes) {
		this.searchCodes = searchCodes;
	}

	/**
	 * @return the searchTitle
	 */
	public String getSearchTitle() {
		return searchTitle;
	}

	/**
	 * @param searchTitle
	 *            the searchTitle to set
	 */
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	/**
	 * @return the searchContent
	 */
	public String getSearchContent() {
		return searchContent;
	}

	/**
	 * @param searchContent
	 *            the searchContent to set
	 */
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	/**
	 * 삭제된 게시물도 보여 줄 것이지. true 삭제 게시물도 보여줌.
	 * 
	 * @return the deleteView
	 */
	public boolean isDeleteView() {
		return deleteView;
	}

	/**
	 * 삭제된 게시물도 보여 줄 것이지. true 삭제 게시물도 보여줌.
	 * 
	 * @param deleteView
	 *            the deleteView to set
	 */
	public void setDeleteView(boolean deleteView) {
		this.deleteView = deleteView;
	}

	/**
	 * @return 검색 단어중 값이 있는 하나를 반환. 아무도 없으면 null. 2개 이상 값이 있을 경우 어떤걸 반환 할지 모름
	 */
	public String getWord() {

		if (!StringUtilAd.isEmpty(searchName)) {
			return searchName;
		}

		if (!StringUtilAd.isEmpty(searchTitle)) {
			return searchTitle;
		}

		if (!StringUtilAd.isEmpty(searchContent)) {
			return searchContent;
		}
		return null;
	}

}
