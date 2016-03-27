package com.setvect.nowhappy.note.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.note.vo.NoteVo;

/**
 * 노트 내용
 *
 * @version $Id$
 */
public interface NoteDao extends JpaRepository<NoteVo, Integer>, NoteDaoCustom {
}