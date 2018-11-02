package com.setvect.nowhappy.network.repository;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.network.vo.NetworkVo;
import com.setvect.nowhappy.network.web.NetworkSearch;

/**
 * 네트워크 내용
 */
public interface NetworkRepositoryCustom {

	/**
	 * @param pageCondition
	 *            노트 검색 정보
	 * @return 노트 목록
	 */
	public GenericPage<NetworkVo> getNetworkPagingList(NetworkSearch pageCondition);

}