package com.setvect.nowhappy.home.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.ApplicationConstant.WebCommon;

/**
 * @version $Id$
 */
@Controller
public class HomeController {
	@RequestMapping("/home.do")
	public String home(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(WebAttributeKey.LOAD_PAGE, "/app/main.do");
		return "/home";
	}

	@RequestMapping("/l.do")
	public String loginForm(HttpServletRequest request, HttpServletResponse response) {
		String rtnUrl = StringUtilAd.null2str(request.getParameter(WebCommon.RETURN_URL)) ;
		request.setAttribute(WebAttributeKey.LOAD_PAGE, "/app/login/form.do?" + WebCommon.RETURN_URL +"=" + rtnUrl);
		return "/home";
	}

	@RequestMapping("/app/main.do")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		return "/app/main";
	}
}
