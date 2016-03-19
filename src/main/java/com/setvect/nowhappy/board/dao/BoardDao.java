package com.setvect.nowhappy.board.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.board.service.BoardManagerSearch;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시판 관리
 * 
 * @version $Id$
 */
public interface BoardDao {


	public BoardVo getBoard(String code);

	/**
	 * @param pageCondition
	 * @return 게시판생성 정보 항목
	 */
	public GenericPage<BoardVo> getBoardPagingList(BoardManagerSearch pageCondition);

	/**
	 * @param board
	 */
	public void insertBoard(BoardVo board);

	/**
	 * @param article
	 */
	public void updateBoard(BoardVo board);

	/**
	 * @param articleSeq
	 */
	public void deleteBoard(String code);
}
