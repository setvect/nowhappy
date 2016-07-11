package com.setvect.nowhappy.knowledge.service;

import com.setvect.common.util.SearchListVo;

/**
 * 목록 페이징 및 검색 조건
 */
public class KnowledgeSearch extends SearchListVo {

	private String searchClassifyC;
	private String searchWord;

	public KnowledgeSearch(int startCursor, int returnCount) {
		super(startCursor, returnCount);
	}

	/**
	 * @return the searchClassifyC
	 */
	public String getSearchClassifyC() {
		return searchClassifyC;
	}

	/**
	 * @param searchClassifyC
	 *            the searchClassifyC to set
	 */
	public void setSearchClassifyC(String searchClassifyC) {
		this.searchClassifyC = searchClassifyC;
	}

	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord
	 *            the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
}
