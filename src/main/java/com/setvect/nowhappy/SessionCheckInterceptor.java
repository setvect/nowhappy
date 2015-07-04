package com.setvect.nowhappy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.setvect.common.util.SerializerUtil;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.user.dao.UserService;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 로그인 체크<br>
 * 모든 액션에 대해서 로그인 여부를 검사하여 로그인이 되지 않으면 로그인 페이지로 이동
 */
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionCheckInterceptor.class);

	@Autowired
	private UserService userService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws UnsupportedEncodingException, IOException {
		String currentUrl = request.getRequestURI();
		LOGGER.info("[Connect] IP: " + request.getRemoteAddr() + ", " + request.getHeader("User-Agent"));

		// 로그인 권한 체크

		// 호출한 서블릿 주소(~~.do 시작하는)를 저장
		// JSP에서 form action에 주소로 사용
		UserVo user = ApplicationUtil.getLoginSession(request);

		// 개발중에는 자동 로그인
		// if (user == null) {
		// user = forceLogin(response);
		// }

		request.setAttribute(WebAttributeKey.USER_SESSION_KEY, user);
		return true;
	}

	/**
	 * 강제 로그인
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private UserVo forceLogin(HttpServletResponse response) throws IOException {
		UserVo user;
		user = userService.getUser("setvect");

		// 로그인 성공
		// 비밀번호는 노출 되지 않게 하기 위함
		user.setPasswd(null);

		String cookieData = SerializerUtil.makeBase64Encode(user);

		// iis에서는 줄바꿈 문제가 있으면 쿠키가 셋팅이 안된다. 그래서 줄 바꿈을 제거
		cookieData = cookieData.replaceAll("\r", "");
		cookieData = cookieData.replaceAll("\n", "");

		Cookie loginCookie = new Cookie(ApplicationConstant.WebCommon.USER_COOKIE_KEY, cookieData);
		loginCookie.setPath("/");
		response.addCookie(loginCookie);
		return user;
	}

	/**
	 * 파라미터 맵을 만듬 <br>
	 * 참고로 request.getParameterMap()을 사용하면 값에 타입이 String[] 됨
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> makeParamMap(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();

		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement();
			param.put(key, request.getParameter(key));
		}
		return param;
	}

}