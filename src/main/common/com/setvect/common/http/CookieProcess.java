package com.setvect.common.http;

/**
 * request에서 쿠키 값을 파싱한뒤 쿠키 명과 이름을 Hashtable에 저장 시켜 놓고 get()을 이용하여 쿠키이름에 해당하는 값을
 * 가져온다.
 * 
 * @version $Id: CookieProcess.java,v 1.5 2005/06/20 09:25:46 setvect Exp $
 */
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieProcess {
	private Map<String, Cookie> cook = new HashMap<String, Cookie>();

	/**
	 * 쿠키 파싱
	 * 
	 * @param request
	 */
	public CookieProcess(HttpServletRequest request) {
		Cookie ck[] = request.getCookies();

		// 쿠키값이 존제 하지 않으면.
		if (ck == null) {
			ck = new Cookie[0];
		}

		// 쿠키값을 저장한다.
		for (int i = 0; i < ck.length; i++) {
			cook.put(ck[i].getName(), ck[i]);
		}
	}

	/**
	 * @param name
	 *            쿠키이름
	 * @return 쿠키이름에 대한 값. 이름에 해당하는 값이 없으면 null
	 */
	public String get(String name) {
		// 쿠키에 해당하는 값이 null
		Cookie c = cook.get(name);

		if (c == null) {
			return null;
		}
		return c.getValue();
	}

	/**
	 * @param name
	 *            쿠키이름
	 * @return 쿠키 객체 , 없으면 null
	 */
	public Cookie getCookie(String name) {
		return cook.get(name);
	}
}