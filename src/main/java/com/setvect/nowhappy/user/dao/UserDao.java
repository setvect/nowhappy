package com.setvect.nowhappy.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 사용자 Repository
 */
public interface UserDao extends JpaRepository<UserVo, String>, UserDaoCustom {

}
