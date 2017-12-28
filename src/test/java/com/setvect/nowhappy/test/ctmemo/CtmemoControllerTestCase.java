package com.setvect.nowhappy.test.ctmemo;

import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.ctmemo.web.CtmemoController;
import com.setvect.nowhappy.test.MainTestBase;

public class CtmemoControllerTestCase extends MainTestBase {
	private static final String CONTENT_STRING = "복슬이 멍멍";
	@Autowired
	private CtmemoController controller;

	@Test
	@Transactional
	public void test() {
		String jspPage = controller.ctmemoPage();
		Assert.assertThat(jspPage, CoreMatchers.is("/app/ctmemo/main"));

		jspPage = controller.ctmemoMobile();
		Assert.assertThat(jspPage, CoreMatchers.is("/app/ctmemo/mobile"));

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("workspaceSeq", "1");

		List<CtmemoVo> result = controller.listAllCtmemo(request);
		Assert.assertThat(result.size(), CoreMatchers.is(18));

		CtmemoVo ctmemo = controller.newMemo(request);
		ctmemo.setContent(CONTENT_STRING);
		controller.saveMemo(ctmemo, request);

		result = controller.listAllCtmemo(request);
		Assert.assertThat(result.size(), CoreMatchers.is(19));

		// 최근 등록
		CtmemoVo newest = result.get(0);
		Assert.assertThat(newest.getContent(), CoreMatchers.is(CONTENT_STRING));

		request.setParameter("ctmemoSeq", String.valueOf(newest.getCtmemoSeq()));
		controller.deleteMemo(request);

		result = controller.listAllCtmemo(request);
		Assert.assertThat(result.size(), CoreMatchers.is(18));

		controller.undelete(request);
		result = controller.listAllCtmemo(request);
		Assert.assertThat(result.size(), CoreMatchers.is(19));

		Map<String, List<String>> styleList = controller.listUsagestyle();
		Assert.assertThat(styleList.get("font"), CoreMatchers.is(ApplicationConstant.Style.FONTSTYLE_LIST));
		Assert.assertThat(styleList.get("bg"), CoreMatchers.is(ApplicationConstant.Style.BGSTYLE_LIST));

	}
}
