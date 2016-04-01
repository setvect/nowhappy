package com.setvect.nowhappy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 사용자 Repository
 */
public interface UserRepository extends JpaRepository<UserVo, String>, UserRepositoryCustom {

}
