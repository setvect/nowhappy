package com.setvect.nowhappy.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.auth.AuthChangeListener;
import com.setvect.nowhappy.auth.vo.AuthMapVo;
import com.setvect.nowhappy.auth.vo.AuthMapVoKey;
import com.setvect.nowhappy.auth.vo.AuthVo;
import com.setvect.nowhappy.user.repository.AuthRepository;
import com.setvect.nowhappy.user.repository.AuthMapRepository;
import com.setvect.nowhappy.user.repository.UserRepository;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 */
@Service(value = "UserService")
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthRepository authRepository;
	@Autowired
	private AuthMapRepository authMapRepository;

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
		return userRepository.findOne(id);
	}

	public GenericPage<UserVo> listUser(UserSearchCondition searchVo) {
		return userRepository.listUser(searchVo);
	}

	/**
	 * 회원 정보 등록
	 *
	 * @param user
	 *            회원
	 * @throws Exception
	 */
	public void insertUser(UserVo user) {
		userRepository.save(user);
	}

	/**
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(UserVo user) {
		userRepository.save(user);
	}

	/**
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(String id) {
		userRepository.delete(id);
	}

	// ---------------- 권한
	public AuthVo getAuth(int authSeq) {
		return authRepository.findOne(authSeq);
	}

	public List<AuthVo> getAuthPagingList() {
		return authRepository.findAll();
	}

	public void createAuth(AuthVo auth) {
		authRepository.save(auth);
		fireEventAuth();
	}

	public void updateAuth(AuthVo auth) {
		authRepository.save(auth);
		fireEventAuth();
	}

	public void removeAuth(int authSeq) {
		authRepository.delete(authSeq);
		fireEventAuth();
	}

	// ---------------- 권한 맵핑
	public AuthMapVo getAuthMap(AuthMapVoKey key) {
		return authMapRepository.findOne(key);
	}

	public List<AuthMapVo> getAuthMapPagingList() {
		return authMapRepository.findAll();
	}

	public void createAuthMap(AuthMapVo authMap) {
		authMapRepository.save(authMap);
		fireEventAuthMap();
	}

	public void removeAuthMap(AuthMapVoKey key) {
		authMapRepository.delete(key);
	}

	/**
	 * 회원이 가지고 있는 권한 맵핑 정보 삭제
	 *
	 * @param userId
	 */
	public void removeAuthMapForUserId(String userId) {
		authMapRepository.deleteByKeyUserId(userId);
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
		List<AuthVo> list = getAuthPagingList();

		for (AuthChangeListener listener : authChangeListener) {
			listener.updateAuth(list);
		}
	}

	/**
	 * 권한 맵핑 정보 수정 notify
	 */
	private void fireEventAuthMap() {
		List<AuthMapVo> list = getAuthMapPagingList();

		for (AuthChangeListener listener : authChangeListener) {
			listener.updateAuthMap(list);
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
