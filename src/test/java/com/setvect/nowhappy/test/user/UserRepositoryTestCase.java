package com.setvect.nowhappy.test.user;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.test.MainTestBase;
import com.setvect.nowhappy.test.UnitTestUtil;
import com.setvect.nowhappy.user.repository.UserRepository;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;

public class UserRepositoryTestCase extends MainTestBase {

	@Autowired
	private UserRepository repository;

	@Before
	public void after() {
		insertInitValue();
	}

	@Test
	public void test() throws InterruptedException {
		UserVo user = UnitTestUtil.getUserTestData();
		repository.save(user);

		UserVo u = repository.findOne(user.getUserId());
		Assert.assertThat(u, CoreMatchers.is(user));

		UserSearchCondition condition = new UserSearchCondition(1, 1);
		GenericPage<UserVo> list = repository.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(1));

		condition.setSearchName("복슬");
		list = repository.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(0));

		condition.setSearchName("복실");
		list = repository.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(0));

		String name = "내사랑";
		user.setName(name);
		repository.save(user);

		UserVo vo = repository.findOne(user.getUserId());
		Assert.assertThat(name, CoreMatchers.is(vo.getName()));

		condition = new UserSearchCondition(1, 2);
		repository.findOne(user.getUserId());
		list = repository.listUser(condition);
		Assert.assertThat(list.getList().size(), CoreMatchers.is(1));

	}

}
