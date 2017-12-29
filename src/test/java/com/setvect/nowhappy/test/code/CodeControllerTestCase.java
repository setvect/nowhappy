package com.setvect.nowhappy.test.code;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.nowhappy.code.vo.CodeVo;
import com.setvect.nowhappy.code.web.CodeController;
import com.setvect.nowhappy.test.MainTestBase;

public class CodeControllerTestCase extends MainTestBase {
	@Autowired
	private CodeController controller;

	@Before
	public void after() {
		insertInitValue();
	}

	@Test
	public void test() {
		CodeVo code = new CodeVo();
		code.setCodeValue("CODE1");
		code.setMajorCode("AA");
		code.setMinorCode("B");
		code.setOrderNo(1);
		controller.add(code);

		List<CodeVo> rtnValue = controller.list(code.getMajorCode());
		Assert.assertThat(rtnValue.size(), CoreMatchers.is(1));
		Assert.assertThat(code, CoreMatchers.is(rtnValue.get(0)));

		CodeVo v = controller.get(code.getCodeSeq());
		Assert.assertThat(code, CoreMatchers.is(v));

		code.setCodeValue("BBBBB");
		controller.update(code);
		rtnValue = controller.list(code.getMajorCode());
		Assert.assertThat(rtnValue.size(), CoreMatchers.is(1));
		Assert.assertThat(code, CoreMatchers.is(rtnValue.get(0)));
		System.out.println(rtnValue.get(0));

		CodeVo p = new CodeVo();
		p.setCodeSeq(code.getCodeSeq());
		controller.delete(p.getCodeSeq());
		rtnValue = controller.list(code.getMajorCode());
		Assert.assertThat(rtnValue.size(), CoreMatchers.is(0));

	}
}
