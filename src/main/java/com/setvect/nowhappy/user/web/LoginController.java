package com.setvect.nowhappy.user.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.setvect.common.util.SerializerUtil;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.user.dao.UserService;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 환경설정>운영자 관리 메뉴 컨트롤러
 */
@Controller
public class LoginController {

	/**
	 * 서브 명령어 정의
	 */
	public static enum Mode {
		LOGIN_FORM, LOGIN_ACTION
	}

	/**
	 * 뷰에 전달할 객체를 가르키는 키
	 */
	public static enum AttributeKey {
		// 로그인 결과
		LOGIN_FAIL,
	}

	@Autowired
	private UserService userService;

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/login.do")
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String mode = request.getParameter("mode");

		Mode m;
		if (StringUtilAd.isEmpty(mode)) {
			m = Mode.LOGIN_FORM;
		}
		else {
			m = Mode.valueOf(mode);
		}

		String rtnUrl = request.getParameter(ApplicationConstant.WebCommon.RETURN_URL);
		if (m == Mode.LOGIN_ACTION) {
			String userId = request.getParameter("userId");
			String passwd = request.getParameter("passwd");

			UserVo user = userService.getUser(userId);
			boolean loginStat = false;
			if (user != null) {
				String passwdEncode = StringUtilAd.encodePassword(passwd, ApplicationConstant.PASSWD_ALGORITHM);
				loginStat = user.getPasswd().equals(passwdEncode);
			}
			// 로그인 성공
			if (loginStat) {
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
				String returnUrl = rtnUrl;
				returnUrl = StringUtilAd.null2str(returnUrl, "/");

				mav.setViewName("redirect:" + returnUrl);
				return mav;
			}
			// 로그인 실패
			else {
				mav.addObject(AttributeKey.LOGIN_FAIL.name(), true);
				m = Mode.LOGIN_FORM;
			}
		}

		if (m == Mode.LOGIN_FORM) {
			mav.addObject(ApplicationConstant.WebCommon.RETURN_URL, rtnUrl);
		}
		return mav;
	}
}