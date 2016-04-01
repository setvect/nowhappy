package com.setvect.nowhappy.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.board.vo.BoardArticleVo;

/**
 * 게시물
 * 
 * @version $Id$
 */
public interface BoardArticleRepository extends JpaRepository<BoardArticleVo, Integer>, BoardArticleRepositoryCustom {
}
