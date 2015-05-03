package com.setvect.common.util;

import java.util.List;

/**
 * Page에서 검색된 객체들의 타입을 지정함
 */
public class GenericPage<T> {

	private List<T> objects;
	private int startCursor;
	private int totalCount;

	/**
	 * @param objects
	 *            리스트
	 * @param startCursor
	 *            시작 항목(1부터 시작)
	 * @param totalCount
	 *            전체 항목 수
	 */
	public GenericPage(List<T> objects, int startCursor, int totalCount) {
		this.objects = objects;
		this.startCursor = startCursor;
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return objects;
	}

	/**
	 * @return the startCursor
	 */
	public int getStartCursor() {
		return startCursor;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

}