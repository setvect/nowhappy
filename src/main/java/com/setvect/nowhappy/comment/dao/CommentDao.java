package com.setvect.nowhappy.comment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 코멘트
 */
public interface CommentDao extends JpaRepository<Comment, Integer>, CommentDaoCustom {

}
