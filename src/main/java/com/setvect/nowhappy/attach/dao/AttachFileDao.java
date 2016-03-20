package com.setvect.nowhappy.attach.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.attach.vo.AttachFileVo;

/**
 * 첨부파일
 * 
 * @version $Id$
 */
public interface AttachFileDao extends JpaRepository<AttachFileVo, Integer> {

	public List<AttachFileVo> findByModuleNameAndModuleId(AttachFileModule moduleName, String moduleId);

}
