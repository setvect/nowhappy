package com.setvect.nowhappy.knowledge.repository;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.knowledge.service.KnowledgeSearch;
import com.setvect.nowhappy.knowledge.vo.KnowledgeVo;

public interface KnowledgeRepositoryCustom {

	/**
	 * @param pageCondition
	 *            게시물 검색 정보
	 * @return 게시물 페이지 값
	 */
	public GenericPage<KnowledgeVo> getKnowledgePagingList(KnowledgeSearch pageCondition);
}
