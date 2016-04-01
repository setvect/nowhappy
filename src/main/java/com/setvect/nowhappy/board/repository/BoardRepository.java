package com.setvect.nowhappy.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시판 관리
 * 
 * @version $Id$
 */
public interface BoardRepository extends JpaRepository<BoardVo, String>, BoardRepositoryCustom {
}
