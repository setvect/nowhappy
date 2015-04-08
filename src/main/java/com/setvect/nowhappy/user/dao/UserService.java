package com.setvect.nowhappy.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.common.util.GenericPage;
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
}
