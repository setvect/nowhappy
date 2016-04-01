package com.setvect.nowhappy.ctmemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;

/**
 * 메모장 Repository
 *
 */
public interface CtmemoRepository extends JpaRepository<CtmemoVo, Integer>, CtmemoRepositoryCustom {
	@Query("select c from CtmemoVo c where workspaceSeq =?1 and deleteF = 'N' order by c.uptDate desc")
	List<CtmemoVo> findByWorkspaceSeq(int workspaceSeq);
}
