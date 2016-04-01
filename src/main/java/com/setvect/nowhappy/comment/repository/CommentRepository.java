package com.setvect.nowhappy.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.comment.vo.CommentVo;

/**
 * 코멘트
 */
public interface CommentRepository extends JpaRepository<CommentVo, Integer>, CommentRepositoryCustom {

}
