package com.setvect.nowhappy.test.ctmemo;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.ctmemo.web.CtmemoController;
import com.setvect.nowhappy.test.MainTestBase;

public class CtmemoControllerTestCase extends MainTestBase {
	private static final String CONTENT_STRING = "복슬이 멍멍";
	@Inject
	private CtmemoController controller;

	@Test
	public void test() {
		String jspPage = controller.ctmemoPage();
		Assert.assertThat(jspPage, CoreMatchers.is("/app/ctmemo/main"));

		jspPage = controller.ctmemoMobile();
		Assert.assertThat(jspPage, CoreMatchers.is("/app/ctmemo/mobile"));

		List<CtmemoVo> result = controller.listAllCtmemo();
		Assert.assertThat(result.size(), CoreMatchers.is(3));

		CtmemoVo ctmemo = controller.newMemo();
		ctmemo.setContent(CONTENT_STRING);
		MockHttpServletRequest request = new MockHttpServletRequest();
		controller.saveMemo(ctmemo, request);

		result = controller.listAllCtmemo();
		Assert.assertThat(result.size(), CoreMatchers.is(4));

		// 최근 등록
		CtmemoVo newest = result.get(0);
		Assert.assertThat(newest.getContent(), CoreMatchers.is(CONTENT_STRING));

		request.setParameter("ctmemoSeq", String.valueOf(newest.getCtmemoSeq()));
		controller.deleteMemo(request);

		result = controller.listAllCtmemo();
		Assert.assertThat(result.size(), CoreMatchers.is(3));

		controller.undelete(request);
		result = controller.listAllCtmemo();
		Assert.assertThat(result.size(), CoreMatchers.is(4));

		Map<String, List<String>> styleList = controller.listUsagestyle();
		Assert.assertThat(styleList.get("font"), CoreMatchers.is(ApplicationConstant.Style.FONTSTYLE_LIST));
		Assert.assertThat(styleList.get("bg"), CoreMatchers.is(ApplicationConstant.Style.BGSTYLE_LIST));

	}
}
