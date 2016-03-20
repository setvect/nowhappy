package com.setvect.nowhappy.board.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.board.service.BoardArticleSearch;
import com.setvect.nowhappy.board.vo.BoardArticleVo;

/**
 * 게시물
 * 
 * @version $Id$
 */
public interface BoardArticleDaoCustom {

	/**
	 * @param pageCondition
	 *            게시물 검색 정보
	 * @return 게시물 페이지 값
	 */
	public GenericPage<BoardArticleVo> getArticlePagingList(BoardArticleSearch pageCondition);

	/**
	 * 답변 등록
	 * 
	 * @param article
	 *            게시물
	 * @param parentId
	 *            부모 게시물
	 */
	public void insertArticleReply(BoardArticleVo article, int parentId);

}
