package com.setvect.nowhappy.user.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.util.StringEtcUtil;

/**
 * 로그아웃
 */
@Controller
public class LogoutController {
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/app/login/logout.do")
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Cookie loginCookie = new Cookie(ApplicationConstant.WebCommon.USER_COOKIE_KEY, null);
		loginCookie.setPath("/");
		response.addCookie(loginCookie);
		String returnUrl = "";
		returnUrl = StringEtcUtil.null2str(returnUrl, "/");

		mav.setViewName("redirect:/");
		return mav;
	}

}