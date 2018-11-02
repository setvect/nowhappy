package com.setvect.nowhappy.network.web;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.setvect.common.util.SearchListVo;

public class NetworkSearch extends SearchListVo {
	private String searchTitle;

	public NetworkSearch(int startCursor, int returnCount) {
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
	public void setSearchTitle(final String searchTitle) {
		this.searchTitle = searchTitle;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
