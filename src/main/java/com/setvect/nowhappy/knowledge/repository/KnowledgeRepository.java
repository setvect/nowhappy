package com.setvect.nowhappy.knowledge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.knowledge.vo.KnowledgeVo;

public interface KnowledgeRepository extends JpaRepository<KnowledgeVo, Integer>, KnowledgeRepositoryCustom {

}
