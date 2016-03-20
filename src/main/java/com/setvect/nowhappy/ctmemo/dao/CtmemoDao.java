package com.setvect.nowhappy.ctmemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;

/**
 * 메모장 DAO
 *
 */
public interface CtmemoDao extends JpaRepository<CtmemoVo, Integer>, CtmemoDaoCustom {
	@Query("select c from CtmemoVo c where workspaceSeq =?1 and deleteF = 'N' order by c.uptDate desc")
	List<CtmemoVo> findByWorkspaceSeq(int workspaceSeq);
}
