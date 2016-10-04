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
			List<String> s = EnvirmentProperty.getList("com.setvect.nowhappy.allow_upload_file");
			ALLOW_UPLOAD_FILE = new String[s.size()];
			for (int i = 0; i < s.size(); i++) {
				ALLOW_UPLOAD_FILE[i] = s.get(i).toLowerCase().trim();
			}

			IMAGE_EXT = EnvirmentProperty.getList("com.setvect.nowhappy.image_ext");
		}
	}

	/**
	 * 전역적으로 사용되는
	 * request.setAttribute()에 Key값.
	 */
	public static class WebAttributeKey {
		public static final String LOAD_PAGE = "LOAD_PAGE";
		public static final String PROCESS_RESULT = "PROCESS_RESULT";
		/** 로그인 attribute 키값 */
		public static final String USER_SESSION_KEY = "_user_session_key";
		public static final String BOARD_LIST_TYPE = "";
		public static final String MODE = "mode";
		public static final String IMAGE_URL = "IMAGE_URL";
		/** 서비스 주소 저장 */
		public static final String SERVLET_URL = "SERVLET_URL";
		public static final String RETURN_URL = "RETURN_URL";
		public static final String LOTTO = "LOTTO";
	}

	/** 패스워드 암호화 알고리즘 */
	public final static String PASSWD_ALGORITHM = "MD5";

	/**
	 * 파일 업로드 관련 상수
	 */
	public static class FileUpload {
		/** 웹루트 기준으로 업로드 파일 경로 */
		public static final String UPLOAD_PATH = EnvirmentProperty.getString("com.setvect.nowhappy.upload_path");
		/** 웹루트 기준으로 첨부파일 저장 경로 */
		public static final String ATTACH_PATH = EnvirmentProperty.getString("com.setvect.nowhappy.attach.upload_path");
		/** 웹루트 기준으로 이미지 파일 저장 경로 */
		public static final String IMAGE_PATH = EnvirmentProperty.getString("com.setvect.nowhappy.image.upload_path");
		/** 섬네일 이미지 저장 경로 */
		public static final String THUMBNAIL_PATH = EnvirmentProperty
				.getString("com.setvect.nowhappy.image.thumbnail_path");

		/** 첨부파일 업로드 한계 사이즈(byte) */
		public static final int ATTACH_LIMIT_SIZE = EnvirmentProperty.getInt("com.setvect.nowhappy.attach.limit_size") * 1024;
		/** 첨부파일 업로드 한계 사이즈(byte) */
		public static final int IMAGE_LIMIT_SIZE = EnvirmentProperty.getInt("com.setvect.nowhappy.image.limit_size") * 1024;

	}

	/**
	 * 게시판 환경 설정
	 */
	public static class BoardConfig {
		/** 목록에서 콘텐츠 내용이 보여지는 게시판 코드 */
		public static final List<String> LIST_CONTENT_VIEW = EnvirmentProperty
				.getList("com.setvect.nowhappy.board.list_content_view");

		/** 메뉴로 노출 시킬 게시판 코드 */
		public static final List<String> VIEW_LIST = EnvirmentProperty.getList("com.setvect.nowhappy.board.view_list");
	}
}
