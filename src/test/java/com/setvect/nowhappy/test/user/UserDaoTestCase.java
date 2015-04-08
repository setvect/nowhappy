package com.setvect.nowhappy.test.user;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.test.MainTestBase;
import com.setvect.nowhappy.test.UnitTestUtil;
import com.setvect.nowhappy.user.dao.UserDao;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;

public class UserDaoTestCase extends MainTestBase {

	@Inject
	private UserDao dao;

	@Test
	public void test() throws InterruptedException {
		UserVo user = UnitTestUtil.getUserTestData();
		dao.insertUser(user);

		UserVo u = dao.getUser(user.getUserId());
		Assert.assertThat(u, CoreMatchers.is(user));

		UserSearchCondition condition = new UserSearchCondition(1);
		GenericPage<UserVo> list = dao.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(2));

		condition.setSearchName("복슬");
		list = dao.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(1));

		condition.setSearchName("복실");
		list = dao.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(0));

		String name = "내사랑";
		user.setName(name);
		dao.updateUser(user);

		UserVo vo = dao.getUser(user.getUserId());
		Assert.assertThat(name, CoreMatchers.is(vo.getName()));

		condition = new UserSearchCondition(1);
		dao.deleteUser(user.getUserId());
		list = dao.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(1));

	}

}
