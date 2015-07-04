package com.setvect.nowhappy.auth;

import java.util.Collection;

import com.setvect.nowhappy.auth.vo.AuthVo;
import com.setvect.nowhappy.auth.vo.AuthMapVo;


/**
 * 권한 수정 여부
 * 
 * @version $Id$
 */
public interface AuthChangeListener {
	/**
	 * 권한 정보 수정
	 * 
	 * @param auth
	 *            권한 목록
	 */
	public void updateAuth(Collection<AuthVo> auth);

	/**
	 * 권한 매핑 정보 수정
	 * 
	 * @param authMap
	 *            권한 맴핑 정보
	 */
	public void updateAuthMap(Collection<AuthMapVo> authMap);

}
