package com.setvect.nowhappy.test.user;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.test.MainTestBase;
import com.setvect.nowhappy.test.UnitTestUtil;
import com.setvect.nowhappy.user.dao.UserService;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;

public class UserServiceTestCase extends MainTestBase {
	@Inject
	private UserService service;

	@Test
	public void test() {
		UserVo user = UnitTestUtil.getUserTestData();
		service.insertUser(user);

		UserVo getuser = service.getUser(user.getUserId());
		Assert.assertThat(user, CoreMatchers.is(getuser));

		UserSearchCondition condition = new UserSearchCondition(1);
		GenericPage<UserVo> list = service.listUser(condition);
		Assert.assertThat(list.getTotalCount(), CoreMatchers.is(2));

		String name = "복슬";
		user.setName(name);
		service.updateUser(user);

		UserVo result = service.getUser(user.getUserId());
		Assert.assertThat(name, CoreMatchers.is(result.getName()));

		service.deleteUser(user.getUserId());
		list = service.listUser(condition);
		Assert.assertThat(list.getTotalCount(), CoreMatchers.is(1));

	}
}
