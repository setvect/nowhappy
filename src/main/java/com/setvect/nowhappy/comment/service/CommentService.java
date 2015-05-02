package com.setvect.nowhappy.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.comment.dao.CommentDao;
import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 코멘트 관리
 */
@Service("CommentService")
public class CommentService {

	@Autowired
	private CommentDao commentDao;

	public Comment getComment(int commentSeq) {
		return commentDao.getComment(commentSeq);
	}

	public GenericPage<Comment> getCommentPagingList(CommentSearch pageCondition) {
		return commentDao.getCommentPagingList(pageCondition);
	}

	public void createComment(Comment comment) {
		commentDao.createComment(comment);
	}

	public void updateComment(Comment comment) {
		commentDao.updateComment(comment);
	}

	public void removeComment(int commentSeq) {
		commentDao.removeComment(commentSeq);
	}

}
