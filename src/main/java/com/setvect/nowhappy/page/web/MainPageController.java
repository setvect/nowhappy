package com.setvect.nowhappy.page.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 전체 페이지 틀
 */
@Controller
public class MainPageController {

	@RequestMapping("/home.do")
	public String ctmemoPage() {
		return "/main";
	}
}
