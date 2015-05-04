package com.setvect.nowhappy.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.board.dao.BoardDao;
import com.setvect.nowhappy.board.vo.BoardArticleVo;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * @version $Id$
 */
@Service("BoardService")
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	// --------------- 관리

	/**
	 * @param code
	 *            코드
	 * @return 정보
	 */
	public BoardVo getBoard(String code) {
		return boardDao.getBoard(code);
	}

	/**
	 * @param pageCondition
	 * @return 정보 항목
	 */
	public GenericPage<BoardVo> getBoardPagingList(BoardManagerSearch pageCondition) {
		return boardDao.getBoardPagingList(pageCondition);
	}

	/**
	 * @param BoardVo
	 */
	public void insertBoard(BoardVo BoardVo) {
		boardDao.insertBoard(BoardVo);
	}

	/**
	 * @param BoardVo
	 *            게시물 아이디
	 */
	public void updateBoard(BoardVo BoardVo) {
		boardDao.updateBoard(BoardVo);
	}

	/**
	 * @param code
	 *            게시물 코드
	 */
	public void deleteBoard(String code) {
		boardDao.deleteBoard(code);
	}

	// --------------- 게시물
	/**
	 * @param articleSeq
	 * @return
	 */
	public BoardArticleVo getArticle(int articleSeq) {
		return boardDao.getArticle(articleSeq);
	}

	/**
	 * @param pageCondition
	 *            게시물 검색 정보
	 * @return 게시물 페이지 값
	 */
	public GenericPage<BoardArticleVo> getArticlePagingList(BoardArticleSearch pageCondition) {
		return boardDao.getArticlePagingList(pageCondition);
	}

	/**
	 * 게시물 등록
	 * 
	 * @param article
	 *            게시물
	 */
	public void insertArticle(BoardArticleVo article) {
		boardDao.insertArticle(article);
	}

	/**
	 * 답변 등록
	 * 
	 * @param article
	 *            게시물
	 * @param parentId
	 *            부모 게시물
	 */
	public void insertArticleReply(BoardArticleVo article, int parentId) {
		boardDao.insertArticleReply(article, parentId);
	}

	/**
	 * @param article
	 */
	public void updateArticle(BoardArticleVo article) {
		boardDao.updateArticle(article);
	}

	/**
	 * 조회수 증가
	 * 
	 * @param articleSeq
	 */
	public void updateArticleIncrementHit(int articleSeq) {
		boardDao.updateArticleIncrementHit(articleSeq);
	}

	/**
	 * @param articleSeq
	 */
	public void deleteArticle(int articleSeq) {
		boardDao.deleteArticle(articleSeq);
	}
}
