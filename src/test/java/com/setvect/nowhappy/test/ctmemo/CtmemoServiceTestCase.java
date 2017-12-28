package com.setvect.nowhappy.test.ctmemo;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.setvect.nowhappy.ctmemo.CtmemoSearchCondition;
import com.setvect.nowhappy.ctmemo.service.CtmemoService;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.test.MainTestBase;
import com.setvect.nowhappy.test.UnitTestUtil;

public class CtmemoServiceTestCase extends MainTestBase {
	@Autowired
	private CtmemoService service;

	@Test
	@Transactional
	public void test() {
		CtmemoVo ctmemo = UnitTestUtil.getCtmemoTestData();
		service.insertCtmemo(ctmemo);

		CtmemoVo getmemo = service.getCtmemo(ctmemo.getCtmemoSeq());
		Assert.assertThat(ctmemo, CoreMatchers.is(getmemo));

		CtmemoSearchCondition condition = new CtmemoSearchCondition();
		condition.setSearchWorkspaceSeq(1);
		List<CtmemoVo> list = service.listCtmemo(condition);
		Assert.assertThat(list.size(), CoreMatchers.is(19));

		String content = "내사랑 복슬이";
		ctmemo.setContent(content);
		service.updateCtmemo(ctmemo);

		CtmemoVo result = service.getCtmemo(ctmemo.getCtmemoSeq());
		Assert.assertThat(content, CoreMatchers.is(result.getContent()));

		service.removeCtmemo(ctmemo.getCtmemoSeq());
		list = service.listCtmemo(condition);
		Assert.assertThat(list.size(), CoreMatchers.is(18));

	}
}
