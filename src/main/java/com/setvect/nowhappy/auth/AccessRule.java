package com.setvect.nowhappy.auth;

/**
 * 모듈에 따른 Access 여부를 체크
 * 
 * @version $Id$
 */
public interface AccessRule {
	/**
	 * @return 접근 가능 여부
	 */
	public boolean isAccess();

	/**
	 * @return 부가적인 상태
	 */
	public int getStatus();
}
