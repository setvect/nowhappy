package com.setvect.nowhappy.ctmemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.ctmemo.vo.WorkspaceVo;

/**
 * 메모장 DAO
 *
 * @version $Id$
 */
public interface WorkspaceDao extends JpaRepository<WorkspaceVo, Integer> {

}
