package com.setvect.nowhappy.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.note.vo.NoteCategoryVo;

/**
 * 노트 카테고리
 *
 * @version $Id$
 */
public interface NoteCategoryRepository extends JpaRepository<NoteCategoryVo, Integer>, NoteCategoryRepositoryCustom {

}