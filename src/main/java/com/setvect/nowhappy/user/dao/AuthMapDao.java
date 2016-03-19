package com.setvect.nowhappy.user.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.auth.AuthMapSearch;
import com.setvect.nowhappy.auth.vo.AuthMapVo;
import com.setvect.nowhappy.auth.vo.AuthMapVoKey;

/**
 */
public interface AuthMapDao {
	/**
	 * @param key
	 * @return
	 */
	public AuthMapVo getAuthMap(AuthMapVoKey key);

	/**
	 * @param paging
	 * @return
	 * @throws Exception
	 */
	public GenericPage<AuthMapVo> getAuthMapPagingList(AuthMapSearch paging);

	/**
	 * @param auth
	 * @throws Exception
	 */
	public void createAuthMap(AuthMapVo authMap);

	/**
	 * @param key
	 */
	public void removeAuthMap(AuthMapVoKey key);

	/**
	 * @param userId
	 */
	public void removeAuthMapForUserId(String userId);
}
