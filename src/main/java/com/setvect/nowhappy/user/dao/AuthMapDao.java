package com.setvect.nowhappy.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.nowhappy.auth.vo.AuthMapVo;
import com.setvect.nowhappy.auth.vo.AuthMapVoKey;

/**
 * 권한 맴핑
 */
public interface AuthMapDao extends JpaRepository<AuthMapVo, AuthMapVoKey> {
	public void deleteByKeyUserId(String userId);
}
