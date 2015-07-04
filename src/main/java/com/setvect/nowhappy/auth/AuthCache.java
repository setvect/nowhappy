package com.setvect.nowhappy.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.setvect.nowhappy.auth.vo.AuthVo;
import com.setvect.nowhappy.auth.vo.AuthMapVo;


/**
 * 권한 맴핑 정보를 캐싱하고 있다. 회원이 특정 URL과 mode 파라미터에 접근해 접근 허가 여부를 제공함
 * 
 * @version $Id$
 */
@Service
public class AuthCache implements AuthChangeListener {

	private static Collection<AuthVo> authCache;

	private static Collection<AuthMapVo> authMapCache;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.literatureboy.service.user.AuthChangeListener#updateAuth(java.util.List)
	 */
	public void updateAuth(Collection<AuthVo> auth) {
		authCache = auth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.literatureboy.service.user.AuthChangeListener#updateAuthMap(java.util.List)
	 */
	public void updateAuthMap(Collection<AuthMapVo> authMap) {
		authMapCache = authMap;
	}

	/**
	 * @return the authCache
	 */
	public static Collection<AuthVo> getAuthCache() {
		return authCache;
	}

	/**
	 * @return the authMapCache
	 */
	public static Collection<AuthMapVo> getAuthMapCache() {
		return authMapCache;
	}

	/**
	 * 해당 사용자가 가지고 있는 맴핑 정보
	 * 
	 * @param userId
	 * @return
	 */
	public static Collection<AuthMapVo> getAuthMapCache(String userId) {
		Collection<AuthMapVo> col = new ArrayList<AuthMapVo>();
		for (AuthMapVo map : authMapCache) {
			if (map.getUserId().equals(userId)) {
				col.add(map);
			}
		}
		return col;
	}
}
