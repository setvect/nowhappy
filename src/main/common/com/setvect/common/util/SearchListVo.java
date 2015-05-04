package com.setvect.common.util;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 페이지 정보를 계산하기위해 사용
 */
public class SearchListVo {

	/** 시작 커서 위치 */
	private int startCursor;
	/** 가져올 항목 갯수 */
	private int returnCount;

	/**
	 * @param startCursor
	 *            시작 지점 (0부터 시작)
	 * @param returnCount
	 *            가져올 항목 갯수
	 */
	public SearchListVo(int startCursor, int returnCount) {
		this.startCursor = startCursor;
		this.returnCount = returnCount;
	}

	/**
	 * @return 시작 커서 위치. (0부터 시작)
	 */
	public int getStartCursor() {
		return this.startCursor;
	}

	/**
	 * @param startCursor
	 *            시작 커서 위치. (0부터 시작)
	 */
	public void setStartCursor(int startCursor) {
		this.startCursor = startCursor;
	}

	/**
	 * @return the endCursor
	 */
	public int getReturnCount() {
		return returnCount;
	}

	/**
	 * @param endCursor
	 *            the endCursor to set
	 */
	public void setReturnCount(int endCursor) {
		this.returnCount = endCursor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}