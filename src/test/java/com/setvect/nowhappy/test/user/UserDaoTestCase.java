package com.setvect.nowhappy.test.user;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.test.MainTestBase;
import com.setvect.nowhappy.user.dao.UserDao;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;

public class UserDaoTestCase extends MainTestBase {

	@Inject
	private UserDao dao;

	@Test
	public void test() throws InterruptedException {
		UserVo user = new UserVo();
		user.setUserId("mylove");
		user.setName("복슬이");
		user.setPasswd("1234pw");
		user.setEmail("test@test.com");
		user.setAdminF(true);
		user.setDeleteF(false);
		dao.insertUser(user);

		UserVo u = dao.getUser(user.getUserId());
		Assert.assertThat(u, CoreMatchers.is(user));

		UserSearchCondition condition = new UserSearchCondition(1);
		GenericPage<UserVo> list = dao.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(1));

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

		dao.deleteUser(user.getUserId());
		list = dao.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(0));

	}
}
