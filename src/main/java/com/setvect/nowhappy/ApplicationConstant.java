package com.setvect.nowhappy;

import java.util.Arrays;
import java.util.List;

/**
 * 각종 상수값
 */
@SuppressWarnings("unchecked")
public class ApplicationConstant {

	/**
	 * 메모장에 사용할 스타일
	 */
	public static class Style {

		/** 다른 스타일과 구분하기 위해 prefix를 줌. */
		public static final String PREFIX = "_ct_";

		public static final String BGSTYLE_1 = PREFIX + "bgstyle_1";
		public static final String BGSTYLE_2 = PREFIX + "bgstyle_2";
		public static final String BGSTYLE_3 = PREFIX + "bgstyle_3";

		public static final String FONTSTYLE_1 = PREFIX + "fontstyle_1";
		public static final String FONTSTYLE_2 = PREFIX + "fontstyle_2";
		public static final String FONTSTYLE_3 = PREFIX + "fontstyle_3";

		public static final List<String> BGSTYLE_LIST = Arrays.asList(BGSTYLE_1, BGSTYLE_2, BGSTYLE_3);
		public static final List<String> FONTSTYLE_LIST = Arrays.asList(FONTSTYLE_1, FONTSTYLE_2, FONTSTYLE_3);
	}

	public static class WebCommon {
		/** 업로드 파일 확장자 */
		public final static String[] ALLOW_UPLOAD_FILE;
		/** 이미지 확장자 */
		public final static List<String> IMAGE_EXT;

		/** 로그인 쿠키 키값 */
		public static final String USER_COOKIE_KEY = "_user_cookie_key";

		/** 로그인 attribute 키값 */
		public static final String USER_SESSION_KEY = "_user_session_key";
		/** 리턴 URL 파라미터 이름 */
		public static final String RETURN_URL = "returnUrl";

		/** 로그인 상태 유지 시간(단위: sec) */
		public final static int COOKIE_TIME = EnvirmentProperty.getInt("com.setvect.nowhappy.login.cookie_time");

		/**
		 * 모바일 브라우저 Agent <br>
		 * request.getHeader("user-agent") 확인한 값
		 */
		public static final String[] MOBILE_BROWSER_AGENT = { "iPhone", "iPod", "Android", "Windows CE", "BlackBerry",
				"Symbian", "Windows Phone", "webOS", "Opera Mini", "Opera Mobi", "POLARIS", "IEMobile", "lgtelecom",
				"nokia", "SonyEricsson", "LG", "SAMSUNG", "Samsung" };
		static {
			List<String> s = EnvirmentProperty.getList("com.setvect.literatureboy.allow_upload_file");
			ALLOW_UPLOAD_FILE = new String[s.size()];
			for (int i = 0; i < s.size(); i++) {
				ALLOW_UPLOAD_FILE[i] = s.get(i).toLowerCase().trim();
			}

			IMAGE_EXT = EnvirmentProperty.getList("com.setvect.literatureboy.image_ext");
		}
	}

	/**
	 * 전역적으로 사용되는
	 * request.setAttribute()에 Key값.
	 */
	public static class WebAttributeKey {
		public static final String LOAD_PAGE = "LOAD_PAGE";
	}

	/** 패스워드 암호화 알고리즘 */
	public final static String PASSWD_ALGORITHM = "MD5";

}
