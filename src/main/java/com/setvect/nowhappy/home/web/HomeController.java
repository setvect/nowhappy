package com.setvect.nowhappy.home.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.ApplicationConstant.WebCommon;

/**
 */
@Controller
public class HomeController {
	private Logger logger = LogManager.getLogger(getClass());

	@RequestMapping("/")
	public String index() {
		return "redirect:/home.do";
	}

	@RequestMapping("/home.do")
	public String home(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(WebAttributeKey.LOAD_PAGE, "/app/main.do");
		return "/home";
	}

	@RequestMapping("/l.do")
	public String loginForm(HttpServletRequest request, HttpServletResponse response) {
		String rtnUrl = StringUtilAd.null2str(request.getParameter(WebCommon.RETURN_URL));
		request.setAttribute(WebAttributeKey.LOAD_PAGE, "/app/login/form.do?" + WebCommon.RETURN_URL + "=" + rtnUrl);
		return "/home";
	}

	@RequestMapping("/app/main.do")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		logger.log(Level.INFO, "Connect " + request.getRemoteAddr());
		return "/app/main";
	}
}
