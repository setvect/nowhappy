package com.setvect.nowhappy.user.repository;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 */
public interface UserRepositoryCustom {
	/**
	 * @param condtion
	 * @return
	 * @throws Exception
	 */
	public GenericPage<UserVo> listUser(UserSearchCondition condtion);

}
