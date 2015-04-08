package com.setvect.nowhappy.user.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant;

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
	@RequestMapping("/user/logout.do")
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Cookie loginCookie = new Cookie(ApplicationConstant.WebCommon.USER_COOKIE_KEY, null);
		loginCookie.setPath("/");
		response.addCookie(loginCookie);
		String returnUrl = "";
		returnUrl = StringUtilAd.null2str(returnUrl, "/");

		String referer = request.getHeader("Referer");
		if (StringUtilAd.isNotEmpty(referer) && referer.contains("/m/")) {
			mav.setViewName("redirect:/m/");
		}
		else {
			mav.setViewName("redirect:/");
		}
		return mav;
	}

}