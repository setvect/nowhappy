package com.setvect.nowhappy.test.ctmemo;

import java.util.List;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.setvect.nowhappy.ctmemo.CtmemoSearchCondition;
import com.setvect.nowhappy.ctmemo.dao.CtmemoDao;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.test.MainTestBase;
import com.setvect.nowhappy.test.UnitTestUtil;

public class CtmemoDaoTestCase extends MainTestBase {

	@Inject
	private CtmemoDao ctmemoDao;

	@Test
	public void test() throws InterruptedException {
		System.out.println(ctmemoDao);

		CtmemoVo ctmemo = UnitTestUtil.getCtmemoTestData();
		ctmemoDao.save(ctmemo);

		CtmemoVo getmemo = ctmemoDao.findOne(ctmemo.getCtmemoSeq());
		System.out.println(getmemo);
		Assert.assertThat(ctmemo, CoreMatchers.is(getmemo));

		CtmemoSearchCondition condition = new CtmemoSearchCondition();
		List<CtmemoVo> list = ctmemoDao.findByWorkspaceSeq(condition.getSearchWorkspaceSeq());
		Assert.assertThat(list.size(), CoreMatchers.is(1));

		String content = "내사랑 복슬이";
		ctmemo.setContent(content);
		ctmemoDao.save(ctmemo);

		CtmemoVo result = ctmemoDao.findOne(ctmemo.getCtmemoSeq());
		Assert.assertThat(content, CoreMatchers.is(result.getContent()));

		ctmemoDao.delete(ctmemo.getCtmemoSeq());
		list = ctmemoDao.findByWorkspaceSeq(condition.getSearchWorkspaceSeq());
		Assert.assertThat(list.size(), CoreMatchers.is(0));

	}

}
