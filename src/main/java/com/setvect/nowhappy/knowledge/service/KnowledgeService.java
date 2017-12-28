package com.setvect.nowhappy.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.knowledge.repository.KnowledgeRepository;
import com.setvect.nowhappy.knowledge.vo.KnowledgeVo;

/**
 */
@Service("KnowledgeService")
public class KnowledgeService {

	@Autowired
	private KnowledgeRepository knowledgeRepository;

	/**
	 * @param knowledgeSeq
	 * @return
	 */
	public KnowledgeVo getKnowledge(int knowledgeSeq) {
		return knowledgeRepository.findOne(knowledgeSeq);
	}

	/**
	 * @param pageCondition
	 *            검색 정보
	 * @return 노트
	 */
	public GenericPage<KnowledgeVo> getKnowledgePagingList(KnowledgeSearch pageCondition) {
		return knowledgeRepository.getKnowledgePagingList(pageCondition);
	}

	/**
	 * 노트 등록
	 *
	 * @param article
	 *            노트
	 */
	public void insertKnowledge(KnowledgeVo article) {
		knowledgeRepository.save(article);
	}

	/**
	 * @param article
	 */
	public void updateKnowledge(KnowledgeVo article) {
		knowledgeRepository.save(article);
	}

	/**
	 * @param articleSeq
	 */
	public void deleteKnowledge(int knowledgeSeq) {
		knowledgeRepository.delete(knowledgeSeq);
	}
}
