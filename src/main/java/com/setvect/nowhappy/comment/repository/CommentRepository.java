package com.setvect.nowhappy.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 코멘트
 */
public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentRepositoryCustom {

}
