package com.setvect.nowhappy.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.auth.vo.AuthMapVo;
import com.setvect.nowhappy.auth.vo.AuthVo;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * @version $Id$
 */
public class AccessChecker {

	public static final String LOGIN_URL = "/l.do";

	public static final String LOGOUT = "/app/login/logout.do";
	/**
	 * 로그인 체크 제외 주소
	 */
	private static final String[] EXCUSE_URL = { LOGIN_URL, LOGOUT };

	/**
	 * 로그인된 사용자가 URL과 그에 따른 파라미터에 접근 가능하지 여부 확인
	 * 
	 * @param request
	 * @param param
	 * @return 접근 가능하면 true, 아니면 false
	 * @throws Exception
	 */
	public static boolean isAccessToUrl(HttpServletRequest request, Map<String, String> param) {
		return isAccessToUrl(request, param, null);
	}

	/**
	 * 로그인된 사용자가 URL과 그에 따른 파라미터에 접근 가능하지 여부 확인
	 * 
	 * @param request
	 * @param param
	 * @param appendAccess
	 *            URL 체크 별도로 모듈 성격에 따른 Access 체크 로직
	 * @return
	 * @throws Exception
	 */
	public static boolean isAccessToUrl(HttpServletRequest request, Map<String, String> param, AccessRule appendAccess) {
		UserVo user = ApplicationUtil.getLoginSession(request);
		String currentUrl = (String) request.getAttribute(WebAttributeKey.SERVLET_URL);
		return isAccessToUrl(user, currentUrl, param, appendAccess);
	}

	/**
	 * 로그인된 사용자가 URL과 그에 따른 파라미터에 접근 가능하지 여부 확인
	 * 
	 * @param user
	 * @param currentUrl
	 * @param param
	 *            URL 파라미터
	 * @return 접근 가능하면 true, 아니면 false
	 * @throws Exception
	 */
	public static boolean isAccessToUrl(UserVo user, String currentUrl, Map<String, String> param)  {
		return isAccessToUrl(user, currentUrl, param, null);
	}

	/**
	 * 로그인된 사용자가 URL과 그에 따른 파라미터에 접근 가능하지 여부 확인
	 * 
	 * @param user
	 * @param currentUrl
	 * @param param
	 *            URL 파라미터
	 * @param appendAccess
	 *            URL 체크 별도로 모듈 성격에 따른 Access 체크 로직
	 * @return 접근 가능하면 true, 아니면 false
	 * @throws Exception
	 */
	public static boolean isAccessToUrl(UserVo user, String currentUrl, Map<String, String> param, AccessRule appendAccess) {
		List<AuthVo> matchAuthList = getMathAuth(currentUrl, param);
		// 접근 권한 정보가 없으면 통과
		if (StringUtilAd.isInclude(currentUrl, EXCUSE_URL) || matchAuthList.size() == 0) {
			return true;
		}

		if (user == null) {
			return false;
		}

		// 전체관리자면 부여된 권한 확인 안함
		if (user.isAdminF()) {
			return true;
		}

		// 해당 사용자가 가지고 있는 맴핑 정보
		Collection<AuthMapVo> authMap = AuthCache.getAuthMapCache(user.getUserId());
		for (AuthVo auth : matchAuthList) {
			for (AuthMapVo map : authMap) {
				if (auth.getAuthSeq() == map.getAuthSeq()) {
					if (appendAccess != null) {
						return appendAccess.isAccess();
					}
					else {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * URL과 파라미터 정보가 매치되는 권한 정보를 찾음
	 * 
	 * @param currentUrl
	 *            현 URL 주소
	 * @param param
	 *            query string 파라미터
	 * @return 현재 URL에 맞는 접근 권한 정보. 해당 사항 없으면 빈 List
	 */
	private static List<AuthVo> getMathAuth(String currentUrl, Map<String, String> param) {
		// 현 접근 URL이 권한 맵리스트에 포함되어 있는지 여부
		List<AuthVo> matchAuthList = new ArrayList<AuthVo>();
		Collection<AuthVo> authList = AuthCache.getAuthCache();
		for (AuthVo auth : authList) {
			boolean urlMatch = false;
			boolean paramMath = false;
			urlMatch = isUrlMatch(currentUrl, auth);
			if (urlMatch) {
				paramMath = isParamMatch(param, auth);
			}
			if (urlMatch && paramMath) {
				matchAuthList.add(auth);
			}
		}
		return matchAuthList;
	}

	/**
	 * 현재 전달 파라미터를 기준으로 권한 정보 설정값이 매칭 되는직 검사
	 * 
	 * @param param
	 *            파라미터 정보
	 * @param auth
	 * @return
	 */
	private static boolean isParamMatch(Map<String, String> param, AuthVo auth) {
		String paramString = auth.getParameter();
		if (paramString.equals("*")) {
			return true;
		}
		String urlSplit[] = paramString.split("[&]");
		for (String token : urlSplit) {
			String[] t = token.split("[=]");
			// "키=값" 형태가 아니면 무시
			if (t.length != 2) {
				continue;
			}
			String name = t[0];
			String value = t[1];
			String paramValue = param.get(name);
			if (paramValue == null) {
				return false;
			}

			if (value.endsWith("*")) {
				String subValue = value.substring(0, value.length() - 1);
				if (!paramValue.startsWith(subValue)) {
					return false;
				}
			}
			else {
				if (!paramValue.equals(value)) {
					return false;
				}
			}

		}
		return true;
	}

	/**
	 * @param currentUrl
	 *            현 URL
	 * @param auth
	 *            매치를 비교할 권한 정보
	 * @return URL이 매치 되면 true
	 */
	private static boolean isUrlMatch(String currentUrl, AuthVo auth) {
		boolean urlMatch = false;
		String url = auth.getUrl();
		if (url.endsWith("*")) {
			url = url.substring(0, url.length() - 1);
			if (currentUrl.startsWith(url)) {
				urlMatch = true;
			}
		}
		else {
			if (currentUrl.equals(url)) {
				urlMatch = true;
			}
		}
		return urlMatch;
	}
}
