package com.setvect.nowhappy.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.auth.vo.AuthVo;

/**
 * 권한
 */
public interface AuthDao extends JpaRepository<AuthVo, Integer> {

}
