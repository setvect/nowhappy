package com.setvect.nowhappy.note.service;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.setvect.common.util.SearchListVo;
import com.setvect.common.util.StringUtilAd;

/**
 * 노트 페이징 및 검색 조건
 * 
 * @version $Id$
 */
public class NoteSearch extends SearchListVo {
	public enum NoteSort {
		REG, UPD;
	}

	private int searchCategorySeq;
	private String searchTitle;
	private String searchContent;
	private NoteSort sort;

	public NoteSearch(int startCursor, int returnCount) {
		super(startCursor, returnCount);
	}

	/**
	 * @return the searchCategorySeq
	 */
	public int getSearchCategorySeq() {
		return searchCategorySeq;
	}

	/**
	 * @param searchCategorySeq
	 *            the searchCategorySeq to set
	 */
	public void setSearchCategorySeq(int searchCategorySeq) {
		this.searchCategorySeq = searchCategorySeq;
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
	 * @return the sort
	 */
	public NoteSort getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(NoteSort sort) {
		this.sort = sort;
	}

	/**
	 * @return 검색 단어중 값이 있는 하나를 반환. 아무도 없으면 null. 2개 이상 값이 있을 경우 어떤걸 반환 할지 모름
	 */
	public String getWord() {
		if (!StringUtilAd.isEmpty(searchTitle)) {
			return searchTitle;
		}
		if (!StringUtilAd.isEmpty(searchContent)) {
			return searchContent;
		}
		return null;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
