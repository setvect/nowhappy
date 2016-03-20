package com.setvect.nowhappy.board.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.board.vo.BoardArticleVo;

/**
 * 게시물
 * 
 * @version $Id$
 */
public interface BoardArticleDao extends JpaRepository<BoardArticleVo, Integer>, BoardArticleDaoCustom {
}
