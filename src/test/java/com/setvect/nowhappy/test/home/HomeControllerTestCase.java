package com.setvect.nowhappy.test.home;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.setvect.nowhappy.home.web.HomeController;
import com.setvect.nowhappy.test.MainTestBase;

public class HomeControllerTestCase extends MainTestBase {
	@Autowired
	private HomeController controller;

	@Test
	public void test() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		String jspPage = controller.home(request, response);
		Assert.assertThat(jspPage, CoreMatchers.is("/main"));

	}
}
