package com.setvect.nowhappy.note.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.note.vo.NoteCategoryVo;

/**
 * 노트 카테고리
 *
 * @version $Id$
 */
public interface NoteCategoryDao extends JpaRepository<NoteCategoryVo, Integer>, NoteCategoryDaoCustom {

}