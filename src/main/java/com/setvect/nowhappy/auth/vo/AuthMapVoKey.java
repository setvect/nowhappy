package com.setvect.nowhappy.auth.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 사용자와 권한 맵핑
 * 
 * @version $Id: ConnKey.java 590 2009-06-02 10:29:25Z setvect $
 */
@Embeddable
public class AuthMapVoKey implements Serializable {

	/** */
	private static final long serialVersionUID = -8519884056908412301L;

	/** 권한 일련정보 */
	@Column(name = "AUTH_SEQ")
	private int authSeq;

	/** 사용자 */
	@Column(name = "USER_ID")
	private String userId;

	/**
	 * @return the authSeq
	 */
	public int getAuthSeq() {
		return authSeq;
	}

	/**
	 * @param authSeq
	 *            the authSeq to set
	 */
	public void setAuthSeq(int authSeq) {
		this.authSeq = authSeq;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authSeq;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthMapVoKey other = (AuthMapVoKey) obj;
		if (authSeq != other.authSeq)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}