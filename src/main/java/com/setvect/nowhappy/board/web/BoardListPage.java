package com.setvect.nowhappy.board.web;

/**
 * 게시판 목록 표시 유형
 */
public enum BoardListPage {
	/** 단순 목록만 표시 */
	NORMAL("/app/board/views/board_list"),
	/** 목록에서 콘텐츠 내용 표시 */
	CONTENT("/app/board/views/board_list_content"),
	/** 검색기능, 조회수, 상세 시간 표시(관리자 용) */
	MANAGE("/app/board/views/board_list_manage");

	private String listUrl;

	private BoardListPage(String url) {
		listUrl = url;
	}

	/**
	 * @return the listUrl
	 */
	public String getListUrl() {
		return listUrl;
	}
}
