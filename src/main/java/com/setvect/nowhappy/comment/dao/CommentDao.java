package com.setvect.nowhappy.comment.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.comment.service.CommentSearch;
import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 코멘트
 * 
 * @version $Id$
 */
public interface CommentDao {
	/**
	 * @param commentSeq
	 *            일련번호
	 * @return 코멘트
	 */
	public Comment getComment(int commentSeq);

	/**
	 * @param pageCondition
	 *            가져올 갯수
	 * @return 코멘트 목록
	 */
	public GenericPage<Comment> getCommentPagingList(CommentSearch pageCondition);

	/**
	 * @param comment
	 *            코멘트 저장
	 */
	public void createComment(Comment comment);

	/**
	 * @param comment
	 *            코멘트 수정
	 */
	public void updateComment(Comment comment);

	/**
	 * @param commentSeq
	 *            일련번호
	 */
	public void removeComment(int commentSeq);

}
