package com.setvect.nowhappy.comment.repository;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.comment.service.CommentSearch;
import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 코멘트
 *
 * @version $Id$
 */
public interface CommentRepositoryCustom {

	/**
	 * @param pageCondition
	 *            가져올 갯수
	 * @return 코멘트 목록
	 */
	public GenericPage<Comment> getCommentPagingList(CommentSearch pageCondition);

}
