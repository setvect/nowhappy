package com.setvect.nowhappy;

import java.util.Arrays;
import java.util.List;

/**
 * 각종 상수값
 */
public class NowHappyConstant {

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
}
