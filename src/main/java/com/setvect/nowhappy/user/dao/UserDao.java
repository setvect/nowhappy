package com.setvect.nowhappy.user.dao;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.auth.AuthMapSearch;
import com.setvect.nowhappy.auth.AuthSearch;
import com.setvect.nowhappy.auth.vo.AuthVo;
import com.setvect.nowhappy.auth.vo.AuthMapVo;
import com.setvect.nowhappy.auth.vo.AuthMapVoKey;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 */
public interface UserDao {
	// ---------------- 사용자
	/**
	 * @param userId
	 * @return
	 */
	public UserVo getUser(String userId);

	/**
	 * @param condtion
	 * @return
	 * @throws Exception
	 */
	public GenericPage<UserVo> listUser(UserSearchCondition condtion);

	/**
	 * @param user
	 * @throws Exception
	 */
	public void insertUser(UserVo user);

	/**
	 * @param user
	 */
	public void updateUser(UserVo user);

	/**
	 * @param userId
	 */
	public void deleteUser(String userId);

	// ---------------- 권한
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

	// ---------------- 권한 맵핑
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
