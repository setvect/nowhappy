package com.setvect.nowhappy.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.note.vo.NoteVo;

/**
 * 노트 내용
 *
 * @version $Id$
 */
public interface NoteRepository extends JpaRepository<NoteVo, Integer>, NoteRepositoryCustom {
}