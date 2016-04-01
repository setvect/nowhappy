package com.setvect.nowhappy.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.code.vo.CodeVo;

/**
 * 첨부파일
 *
 * @version $Id$
 */
public interface CodeRepository extends JpaRepository<CodeVo, Integer> {

	public List<CodeVo> findByMajorCode(String majorCode);

}
