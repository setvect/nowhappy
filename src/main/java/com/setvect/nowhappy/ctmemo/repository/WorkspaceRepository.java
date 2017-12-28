package com.setvect.nowhappy.ctmemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.ctmemo.vo.WorkspaceVo;

/**
 * 메모장 Repository
 */
public interface WorkspaceRepository extends JpaRepository<WorkspaceVo, Integer> {

}
