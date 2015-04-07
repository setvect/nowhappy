package com.setvect.nowhappy.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.setvect.common.log.LogPrinter;

/**
 * @version $Id$
 */
public class StringEtcUtil {

	/**
	 * @param word
	 * 
	 * @return sql String 값에 들어가도록 변경
	 */
	public static String getSqlString(String word) {

		word = null2str(word);
		word = StringUtils.replace(word, "'", "''");
		word = word.trim();
		return new String("'" + word + "'");
	}

	/**
	 * @param word
	 * @return sql String 값에 like 형식으로 검색 되도록 변경<br>
	 *         예) '%단어%'
	 */
	public static String getSqlStringLike(String word) {
		word = null2str(word);
		word = StringUtils.replace(word, "'", "''");
		word = word.trim();
		return new String("'%" + word + "%'");
	}

	/**
	 * @param word
	 * @return sql String 값에 like 형식으로 검색 되도록 변경<br>
	 *         예) '%단어'
	 */
	public static String getSqlStringLikeLeft(String word) {
		word = null2str(word);
		word = StringUtils.replace(word, "'", "''");
		word = word.trim();
		return new String("'%" + word + "'");
	}

	/**
	 * @param word
	 * @return sql String 값에 like 형식으로 검색 되도록 변경<br>
	 *         예) '단어%'
	 */
	public static String getSqlStringLikeRight(String word) {
		word = null2str(word);
		word = StringUtils.replace(word, "'", "''");
		word = word.trim();
		return new String("'" + word + "%'");
	}

	/**
	 * 폼에 맞게 문자를 바꾸어 준다.
	 * 
	 * @param src
	 *            source String
	 * @return 변환된 String
	 */
	public static String toForm(String src) {
		String strBuffer = src;
		if (src == null)
			return StringUtils.EMPTY;
		strBuffer = StringUtils.replace(strBuffer, "<", "&lt;");
		strBuffer = StringUtils.replace(strBuffer, ">", "&gt;");
		strBuffer = StringUtils.replace(strBuffer, "\"", "&quot;");
		strBuffer = StringUtils.replace(strBuffer, "\'", "&#039;");

		// &#33324의 글자를 보존 하기 위해서
		strBuffer = StringUtils.replace(strBuffer, "&amp;#", "&#");
		return strBuffer;
	}

	/**
	 * Html 코드를 텍스트로 코드화 변환
	 * 
	 * @param src
	 *            html문자열
	 * @return text로 변경된 문자열
	 */
	public static String toText(String src) {
		String strBuffer = null2str(src, "");
		strBuffer = StringUtils.replace(strBuffer, "&", "&amp;");
		strBuffer = StringUtils.replace(strBuffer, "<", "&lt;");
		strBuffer = StringUtils.replace(strBuffer, ">", "&gt;");
		strBuffer = StringUtils.replace(strBuffer, "\"", "&quot;");
		strBuffer = StringUtils.replace(strBuffer, "\'", "&#039;");
		strBuffer = StringUtils.replace(strBuffer, " ", "&nbsp;");
		// strBuffer.replaceAll(" ", "&nbsp;");
		strBuffer = StringUtils.replace(strBuffer, "\n", "<br>");

		// &#33324의 글자를 보존 하기 위해서
		strBuffer = StringUtils.replace(strBuffer, "&amp;#", "&#");
		return strBuffer;
	}

	/**
	 * 개행문자를 br 테그를 변경
	 * 
	 * @param src
	 *            source String
	 * @return 변환된 String
	 */
	public static String toBr(String src) {
		if (src == null) {
			return StringUtils.EMPTY;
		}
		String strBuffer = src;
		return StringUtils.replace(strBuffer, "\n", "<br>");
	}

	/**
	 * s2 배열에중에 s1과 같은 문장이 있는지 검사
	 * 
	 * @param s1
	 *            원본값
	 * @param s2
	 *            비교데이터
	 * @return 포함된 문장이 있으면 true
	 */
	public static boolean isInclude(String s1, String[] s2) {
		return positionInclude(s1, s2) != -1;
	}

	/**
	 * @param word
	 *            문자열
	 * @return word가 null 이면 빈문자열로 변환
	 */
	public static String null2str(String word) {
		return word == null ? "" : word;
	}

	/**
	 * @param word
	 *            문자열
	 * @param substitution
	 *            대체 문자열
	 * @return word가 null 이면 대체 문자열로 변환
	 */
	public static String null2str(String word, String substitution) {
		if (word == null || word.equals("")) {
			return substitution;
		}
		else {
			return word;
		}
	}

	/**
	 * s2 배열에중에 s1과 같은 문장이 있는지 검사. 만약 있으면 존제하는 배열위치 리턴
	 * 
	 * @param s1
	 *            원본값
	 * @param s2
	 *            비교데이터
	 * @return 존제하는 배열이 있으면 위치 리턴/없으면 -1리턴
	 */
	public static int positionInclude(String s1, String[] s2) {

		if (s2 == null) {
			return -1;
		}
		for (int i = 0; i < s2.length; i++) {
			if (s1.equals(s2[i]))
				return i;
		}
		return -1;
	}

	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			LogPrinter.out.error("Exception: " + e);
			return password;
		}

		md.reset();

		md.update(unencodedPassword);

		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; ++i) {
			if ((encodedPassword[i] & 0xFF) < 16) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
		}

		return buf.toString();
	}

	/**
	 * @param str
	 *            base64로 된 문자열
	 * @return 디코딩 된 문자열
	 */
	public static String decodeString(String str) {
		BASE64Decoder dec = new BASE64Decoder();
		try {
			return new String(dec.decodeBuffer(str));
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	/**
	 * @param str
	 *            인코딩 할 문자열
	 * @return base64 문자열
	 */
	public static String encodeString(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		return new String(encoder.encodeBuffer(str.getBytes())).trim();
	}

	/**
	 * 바이트 단위 스트링 절삭 (한글 인코딩에 따라 안될 수도 있음)
	 * 
	 * @param str
	 *            스트링
	 * @param sz
	 *            자를 바이트수
	 * @return 잘라진 스트링
	 */
	public static String substringByte(String str, int limit) {

		if (str == null)
			return str;

		int len = str.length();
		int cnt = 0, index = 0;

		while (index < len && cnt < limit) {
			if (str.charAt(index++) < 256) // 1바이트 문자라면...
				cnt++; // 길이 1 증가
			else {
				cnt += 2; // 길이 2 증가
			}
		}

		if (index < len && limit >= cnt)
			str = str.substring(0, index);
		else if (index < len && limit < cnt)
			str = str.substring(0, index - 1);
		return str;
	}

	/**
	 * 제목등에 표시할때 스트링 줄임을 표현 (...)<br>
	 * 바이트 단위
	 * 
	 * @param s
	 *            원본스트링
	 * @param limit
	 *            표현 한계
	 * @return 줄임 처리된 스트링
	 */
	public static String lessen(String s, int limit) {
		String t;
		// ... 문자열 사이즈
		t = substringByte(s, limit - 3);
		if (s == null || s.equals(t)) {
			return t;
		}
		else {
			return t + "...";
		}
	}

	/**
	 * html 테그 제거
	 * 
	 * @param src
	 *            Html 코드가 들어간 텍스트 데이터
	 * @return 순수 TEXT 데이터만 리턴
	 */
	public static String clearHtml(String src) {
		Pattern pa = Pattern.compile("<style>.+?</style>", Pattern.CASE_INSENSITIVE);
		Matcher ma = pa.matcher(src);
		String s = ma.replaceAll("");

		pa = Pattern.compile("<.+?>", Pattern.CASE_INSENSITIVE);
		ma = pa.matcher(s);
		// return src.replaceAll(
		// "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		String clearStr = ma.replaceAll("").replaceAll("&nbsp;", " ").replaceAll("&quot;", "\"").replaceAll("&gt;", "")
				.replaceAll("&lt;", "").replaceAll("&amp;", "");
		// 중간 부분에 스페이스 영역을 하나로 축소
		// "bcde    feafg" ===> "bcde feafg"
		StringTokenizer st = new StringTokenizer(clearStr, " \r\n");
		String rtnValue = "";
		while (st.hasMoreTokens()) {
			rtnValue += st.nextToken() + " ";
		}
		return rtnValue;
	}

	/**
	 * 특수 문자 제거
	 * 
	 * @param str
	 * @return
	 */
	public static String removeSpecialChar(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, " ");
		return str;
	}
}
