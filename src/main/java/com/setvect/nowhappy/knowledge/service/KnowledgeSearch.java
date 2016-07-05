package com.setvect.nowhappy.knowledge.service;

import com.setvect.common.util.SearchListVo;

/**
 * 게시물 관리 목록 페이징 및 검색 조건
 */
public class KnowledgeSearch extends SearchListVo {

	/**
	 * 복수의 게시물 조회<br>
	 * 본 필드에 값이 있으면 searchCode 값은 무시함
	 */
	private String searchTitle;
	private String searchProblem;
	private String searchSolution;

	public KnowledgeSearch(int startCursor, int returnCount) {
		super(startCursor, returnCount);
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
	 * @return the searchProblem
	 */
	public String getSearchProblem() {
		return searchProblem;
	}

	/**
	 * @param searchProblem
	 *            the searchProblem to set
	 */
	public void setSearchProblem(String searchProblem) {
		this.searchProblem = searchProblem;
	}

	/**
	 * @return the searchSolution
	 */
	public String getSearchSolution() {
		return searchSolution;
	}

	/**
	 * @param searchSolution
	 *            the searchSolution to set
	 */
	public void setSearchSolution(String searchSolution) {
		this.searchSolution = searchSolution;
	}

}
