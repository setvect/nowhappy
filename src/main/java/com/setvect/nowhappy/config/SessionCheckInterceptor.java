package com.setvect.nowhappy.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.setvect.common.util.SerializerUtil;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.ApplicationException;
import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.ApplicationConstant.WebCommon;
import com.setvect.nowhappy.auth.AccessChecker;
import com.setvect.nowhappy.user.service.UserService;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 로그인 체크<br>
 * 모든 액션에 대해서 로그인 여부를 검사하여 로그인이 되지 않으면 로그인 페이지로 이동
 */
@Service
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionCheckInterceptor.class);

	@Autowired
	private UserService userService;

	/**
	 * Application 시작과 동시에 최초 한번 실행
	 */
	@PostConstruct
	public void init() {
		userService.initAuth();
		LOGGER.info("init. application.");
	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws UnsupportedEncodingException, IOException {
		String currentUrl = removeHeadSlash(request);

		LOGGER.info("[Connect] IP: " + request.getRemoteAddr() + ", " + request.getHeader("User-Agent"));

		// 호출한 서블릿 주소(~~.do 시작하는)를 저장
		// JSP에서 form action에 주소로 사용
		request.setAttribute(WebAttributeKey.SERVLET_URL, currentUrl);
		UserVo user = ApplicationUtil.getLoginSession(request);

		Map<String, String> param = makeParamMap(request);

		// 개발중에는 자동 로그인
		// if (user == null) {
		// user = forceLogin(response);
		// }

		request.setAttribute(WebAttributeKey.USER_SESSION_KEY, user);

		boolean hasAuth = AccessChecker.isAccessToUrl(user, currentUrl, param);
		if (hasAuth) {
			return true;
		}

		if (!hasAuth && user == null) {
			String returnUrl = currentUrl + "?" + StringUtilAd.null2str(request.getQueryString(), "");
			String loginPage = AccessChecker.LOGIN_URL + "?" + WebCommon.RETURN_URL + "="
					+ URLEncoder.encode(returnUrl, request.getCharacterEncoding());
			response.sendRedirect(loginPage);
			return false;
		}

		// 로그인 했는데 권한이 없으면 에러 메시지 표시
		throw new ApplicationException(user.getUserId() + "는 해당 경로의 접근 권한이 없습니다.");
	}

	/**
	 * http://도메인//abc/a.do 이런식으로 도메인 다음 슬래시가 두 번 들어오면 URL 권한 체크가 안되는 문제가 있음. <br/>
	 * 중복된 슬래시를 제거해 하나만 표시하도록 함.
	 *
	 * @param request
	 * @return
	 */
	private String removeHeadSlash(HttpServletRequest request) {
		String url = request.getRequestURI();
		StringBuffer currentUrlBuffer = new StringBuffer("/");
		for (int i = 0; i < url.length(); i++) {
			if (url.charAt(i) != '/') {
				currentUrlBuffer.append(url.substring(i));
				break;
			}
		}

		String currentUrl = currentUrlBuffer.toString();
		return currentUrl;
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

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement();
			param.put(key, request.getParameter(key));
		}
		return param;
	}

}