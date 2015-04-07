package com.setvect.nowhappy.user.dao;

import com.setvect.common.util.GenericPage;
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

}
