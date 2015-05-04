package com.setvect.nowhappy.board.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.board.service.BoardArticleSearch;
import com.setvect.nowhappy.board.service.BoardManagerSearch;
import com.setvect.nowhappy.board.vo.BoardArticleVo;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시물, 첨부파일, 코멘트 DAO
 * 
 * @version $Id$
 */
public interface BoardDao {

	// --------------- 관리

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

	// --------------- 게시물
	/**
	 * @param articleSeq
	 * @return
	 */
	public BoardArticleVo getArticle(int articleSeq);

	/**
	 * @param pageCondition
	 *            게시물 검색 정보
	 * @return 게시물 페이지 값
	 */
	public GenericPage<BoardArticleVo> getArticlePagingList(BoardArticleSearch pageCondition);

	/**
	 * 게시물 등록
	 * 
	 * @param article
	 *            게시물
	 */
	public void insertArticle(BoardArticleVo article);

	/**
	 * 답변 등록
	 * 
	 * @param article
	 *            게시물
	 * @param parentId
	 *            부모 게시물
	 */
	public void insertArticleReply(BoardArticleVo article, int parentId);

	/**
	 * @param article
	 */
	public void updateArticle(BoardArticleVo article);

	/**
	 * @param articleSeq
	 */
	public void updateArticleIncrementHit(int articleSeq);

	/**
	 * @param articleSeq
	 */
	public void deleteArticle(int articleSeq);
}
