package com.setvect.common.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 페이지 정보를 계산하기위해 사용
 */
public class SearchListVo implements Serializable {
	/** */
	private static final long serialVersionUID = 4784964094940288037L;

	/** 시작 커서 위치 */
	private int startCursor;
	/** 종료 커서 위치 */
	private int endCursor;

	/**
	 * @param startCursor
	 *            시작 지점 (1부터 시작)
	 * @param endCursor
	 *            종료 커서 위치
	 */
	public SearchListVo(int startCursor, int endCursor) {
		this.startCursor = startCursor;
		this.endCursor = endCursor;
	}

	/**
	 * @return 시작 지점. (1부터 시작)
	 */
	public int getStartCursor() {
		return this.startCursor;
	}

	/**
	 * @param startCursor
	 *            현 페이지. 1부터 시작
	 */
	public void setStartCursor(int startCursor) {
		this.startCursor = startCursor;
	}

	/**
	 * @return the endCursor
	 */
	public int getEndCursor() {
		return endCursor;
	}

	/**
	 * @param endCursor
	 *            the endCursor to set
	 */
	public void setEndCursor(int endCursor) {
		this.endCursor = endCursor;
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