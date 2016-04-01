package com.setvect.nowhappy.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.comment.repository.CommentRepository;
import com.setvect.nowhappy.comment.vo.CommentVo;

/**
 * 코멘트 관리
 */
@Service("CommentService")
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public CommentVo getComment(int commentSeq) {
		return commentRepository.findOne(commentSeq);
	}

	public GenericPage<CommentVo> getCommentPagingList(CommentSearch pageCondition) {
		return commentRepository.getCommentPagingList(pageCondition);
	}

	public void insertComment(CommentVo comment) {
		commentRepository.save(comment);
	}

	public void updateComment(CommentVo comment) {
		commentRepository.save(comment);
	}

	public void deleteComment(int commentSeq) {
		commentRepository.delete(commentSeq);
	}

}
