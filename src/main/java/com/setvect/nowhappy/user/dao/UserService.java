package com.setvect.nowhappy.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.auth.AuthChangeListener;
import com.setvect.nowhappy.auth.AuthMapSearch;
import com.setvect.nowhappy.auth.AuthSearch;
import com.setvect.nowhappy.auth.vo.AuthVo;
import com.setvect.nowhappy.auth.vo.AuthMapVo;
import com.setvect.nowhappy.auth.vo.AuthMapVoKey;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * @version $Id: MemoService.java 63 2010-08-16 12:24:44Z setvect@naver.com $
 */
@Service(value = "UserService")
public class UserService {
	/** DB 컨트롤 인스턴스 */
	@Autowired
	private UserDao userDao;

	/** 권한 변경 이벤트 */
	@Autowired
	private List<AuthChangeListener> authChangeListener = new ArrayList<AuthChangeListener>();

	// ---------------- 사용자
	/**
	 * @param id
	 *            회원 아이디
	 * @return 아이디에 맞는 회원정보 없으면 null
	 * @throws Exception
	 */
	public UserVo getUser(String id) {
		return userDao.getUser(id);
	}

	public GenericPage<UserVo> listUser(UserSearchCondition searchVo) {
		return userDao.listUser(searchVo);
	}

	/**
	 * 회원 정보 등록
	 * 
	 * @param user
	 *            회원
	 * @throws Exception
	 */
	public void insertUser(UserVo user) {
		userDao.insertUser(user);
	}

	/**
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(UserVo user) {
		userDao.updateUser(user);
	}

	/**
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(String id) {
		userDao.deleteUser(id);
	}

	// ---------------- 권한
	public AuthVo getAuth(int authSeq) {
		return userDao.getAuth(authSeq);
	}

	public GenericPage<AuthVo> getAuthPagingList(AuthSearch search) {
		return userDao.getAuthPagingList(search);
	}

	public void createAuth(AuthVo auth) {
		userDao.createAuth(auth);
		fireEventAuth();
	}

	public void updateAuth(AuthVo auth) {
		userDao.updateAuth(auth);
		fireEventAuth();
	}

	public void removeAuth(int authSeq) {
		userDao.removeAuth(authSeq);
		fireEventAuth();
	}

	// ---------------- 권한 맵핑
	public AuthMapVo getAuthMap(AuthMapVoKey key) {
		return userDao.getAuthMap(key);
	}

	public GenericPage<AuthMapVo> getAuthMapPagingList(AuthMapSearch search) {
		return userDao.getAuthMapPagingList(search);
	}

	public void createAuthMap(AuthMapVo authMap) {
		userDao.createAuthMap(authMap);
		fireEventAuthMap();
	}

	public void removeAuthMap(AuthMapVoKey key) {
		userDao.removeAuthMap(key);
	}

	/**
	 * 회원이 가지고 있는 권한 맵핑 정보 삭제
	 * 
	 * @param userId
	 */
	public void removeAuthMapForUserId(String userId) {
		userDao.removeAuthMapForUserId(userId);
	}

	// ---------------- 권한 변경 이벤트

	/**
	 * 권한 이벤트 등록
	 * 
	 * @param listener
	 *            권한 변경 이벤트
	 */
	public void addAuthChangeListener(AuthChangeListener listener) {
		authChangeListener.add(listener);
	}

	/**
	 * 권한 변경 이벤트 삭제
	 * 
	 * @param listener
	 *            삭제한 권한 변경 이벤트
	 */
	public void removeAuthChangeListener(AuthChangeListener listener) {
		authChangeListener.remove(listener);
	}

	/**
	 * 권한 이벤트 전체 삭제
	 */
	public void clearAuthChangeListener() {
		authChangeListener.clear();
	}

	/**
	 * 권한 정보를 로딩
	 */
	public void initAuth() {
		fireEventAuth();
		fireEventAuthMap();
	}

	/**
	 * 권한 정보 수정 notify
	 */
	private void fireEventAuth() {
		AuthSearch search = new AuthSearch(0, Integer.MAX_VALUE);
		GenericPage<AuthVo> list = getAuthPagingList(search);

		for (AuthChangeListener listener : authChangeListener) {
			listener.updateAuth(list.getList());
		}
	}

	/**
	 * 권한 맵핑 정보 수정 notify
	 */
	private void fireEventAuthMap() {
		AuthMapSearch search = new AuthMapSearch(0, Integer.MAX_VALUE);
		GenericPage<AuthMapVo> list = getAuthMapPagingList(search);

		for (AuthChangeListener listener : authChangeListener) {
			listener.updateAuthMap(list.getList());
		}
	}

	/**
	 * @param authChangeListener
	 *            the authChangeListener to set
	 */
	public void setAuthChangeListener(List<AuthChangeListener> authChangeListener) {
		this.authChangeListener = authChangeListener;
	}
}
