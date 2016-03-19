package com.setvect.nowhappy.user.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.auth.AuthSearch;
import com.setvect.nowhappy.auth.vo.AuthVo;

/**
 */
public interface AuthDao {
	/**
	 * @param authSeq
	 * @return
	 */
	public AuthVo getAuth(int authSeq);

	/**
	 * @param paging
	 * @return
	 * @throws Exception
	 */
	public GenericPage<AuthVo> getAuthPagingList(AuthSearch paging);

	/**
	 * @param auth
	 * @throws Exception
	 */
	public void createAuth(AuthVo auth);

	/**
	 * @param auth
	 */
	public void updateAuth(AuthVo auth);

	/**
	 * @param authSeq
	 */
	public void removeAuth(int authSeq);
}
