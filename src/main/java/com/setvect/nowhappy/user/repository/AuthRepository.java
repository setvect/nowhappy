package com.setvect.nowhappy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.auth.vo.AuthVo;

/**
 * 권한
 */
public interface AuthRepository extends JpaRepository<AuthVo, Integer> {

}
