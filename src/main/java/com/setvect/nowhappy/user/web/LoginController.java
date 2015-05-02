package com.setvect.nowhappy.user.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.common.util.SerializerUtil;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.user.dao.UserService;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 로그인 처리
 */
@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	@RequestMapping("/app/login/form.do")
	public String loginForm(HttpServletRequest request, HttpServletResponse response) {
		return "/app/login/login";
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/app/login/action.do")
	@ResponseBody
	public boolean process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userId = request.getParameter("userId");
		String passwd = request.getParameter("passwd");

		UserVo user = userService.getUser(userId);
		boolean loginStat = false;
		if (user == null) {
			return false;
		}
		String passwdEncode = StringUtilAd.encodePassword(passwd, ApplicationConstant.PASSWD_ALGORITHM);
		loginStat = user.getPasswd().equals(passwdEncode);
		if (!loginStat) {
			return false;
		}

		// 로그인 성공
		// 비밀번호는 노출 되지 않게 하기 위함
		user.setPasswd(null);

		String cookieData = SerializerUtil.makeBase64Encode(user);
		boolean statusSave = StringUtilAd.isNotEmpty(request.getParameter("statusSave"));

		// iis에서는 줄바꿈 문제가 있으면 쿠키가 셋팅이 안된다. 그래서 줄 바꿈을 제거
		cookieData = cookieData.replaceAll("\r", "");
		cookieData = cookieData.replaceAll("\n", "");

		Cookie loginCookie = new Cookie(ApplicationConstant.WebCommon.USER_COOKIE_KEY, cookieData);
		loginCookie.setPath("/");
		if (statusSave) {
			loginCookie.setMaxAge(ApplicationConstant.WebCommon.COOKIE_TIME);
		}

		response.addCookie(loginCookie);
		String rtnUrl = request.getParameter(ApplicationConstant.WebCommon.RETURN_URL);
		request.setAttribute(WebAttributeKey.LOAD_PAGE, rtnUrl);
		return true;

	}
}