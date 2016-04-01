package com.setvect.nowhappy.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.board.repository.BoardArticleRepository;
import com.setvect.nowhappy.board.repository.BoardRepository;
import com.setvect.nowhappy.board.vo.BoardArticleVo;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 *
 */
@Service("BoardService")
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardArticleRepository boardArticleRepository;

	/**
	 * @param code
	 *            코드
	 * @return 정보
	 */
	public BoardVo getBoard(String code) {
		return boardRepository.findOne(code);
	}

	/**
	 * @param pageCondition
	 * @return 정보 항목
	 */
	public GenericPage<BoardVo> getBoardPagingList(BoardManagerSearch pageCondition) {
		return boardRepository.getBoardPagingList(pageCondition);
	}

	/**
	 * @param BoardVo
	 */
	public void insertBoard(BoardVo BoardVo) {
		boardRepository.save(BoardVo);
	}

	/**
	 * @param BoardVo
	 *            게시물 아이디
	 */
	public void updateBoard(BoardVo BoardVo) {
		boardRepository.save(BoardVo);
	}

	/**
	 * @param code
	 *            게시물 코드
	 */
	public void deleteBoard(String code) {
		boardRepository.delete(code);
	}

	// --------------- 게시물
	/**
	 * @param articleSeq
	 * @return
	 */
	public BoardArticleVo getArticle(int articleSeq) {
		return boardArticleRepository.findOne(articleSeq);
	}

	/**
	 * @param pageCondition
	 *            게시물 검색 정보
	 * @return 게시물 페이지 값
	 */
	public GenericPage<BoardArticleVo> getArticlePagingList(BoardArticleSearch pageCondition) {
		return boardArticleRepository.getArticlePagingList(pageCondition);
	}

	/**
	 * 게시물 등록
	 *
	 * @param article
	 *            게시물
	 */
	public void insertArticle(BoardArticleVo article) {
		boardArticleRepository.insertArticle(article);
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
		boardArticleRepository.insertArticleReply(article, parentId);
	}

	/**
	 * @param article
	 */
	public void updateArticle(BoardArticleVo article) {
		boardArticleRepository.save(article);
	}

	/**
	 * 조회수 증가
	 *
	 * @param articleSeq
	 */
	public void updateArticleIncrementHit(int articleSeq) {
		BoardArticleVo article = boardArticleRepository.findOne(articleSeq);
		article.setHitCount(article.getHitCount() + 1);
	}

	/**
	 * @param articleSeq
	 */
	public void deleteArticle(int articleSeq) {
		boardArticleRepository.delete(articleSeq);
	}
}
