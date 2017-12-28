package com.setvect.nowhappy.board.repository;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.board.service.BoardManagerSearch;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시판 관리
 */
public interface BoardRepositoryCustom {
	/**
	 * @param pageCondition
	 * @return 게시판생성 정보 항목
	 */
	public GenericPage<BoardVo> getBoardPagingList(BoardManagerSearch pageCondition);

}
