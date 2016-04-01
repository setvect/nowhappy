package com.setvect.nowhappy.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.comment.repository.CommentRepository;
import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 코멘트 관리
 */
@Service("CommentService")
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public Comment getComment(int commentSeq) {
		return commentRepository.findOne(commentSeq);
	}

	public GenericPage<Comment> getCommentPagingList(CommentSearch pageCondition) {
		return commentRepository.getCommentPagingList(pageCondition);
	}

	public void insertComment(Comment comment) {
		commentRepository.save(comment);
	}

	public void updateComment(Comment comment) {
		commentRepository.save(comment);
	}

	public void deleteComment(int commentSeq) {
		commentRepository.delete(commentSeq);
	}

}
