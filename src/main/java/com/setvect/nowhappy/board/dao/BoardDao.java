package com.setvect.nowhappy.board.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시판 관리
 * 
 * @version $Id$
 */
public interface BoardDao extends JpaRepository<BoardVo, String>, BoardDaoCustom {
}
