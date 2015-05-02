package com.setvect.nowhappy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 로그인 체크<br>
 * 모든 액션에 대해서 로그인 여부를 검사하여 로그인이 되지 않으면 로그인 페이지로 이동
 */
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionCheckInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws UnsupportedEncodingException, IOException {
		String currentUrl = request.getRequestURI();
		LOGGER.info("[Connect] IP: " + request.getRemoteAddr() + ", " + request.getHeader("User-Agent"));

		// 로그인 권한 체크

		// 호출한 서블릿 주소(~~.do 시작하는)를 저장
		// JSP에서 form action에 주소로 사용
		UserVo user = ApplicationUtil.getLoginSession(request);

		// 개발중에는 자동 로그인
		// if(user == null){
		// user = userService.getUser("setvect");
		// }

		request.setAttribute(WebAttributeKey.USER_SESSION_KEY, user);
		return true;
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