package com.setvect.nowhappy.user.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 회원
 */
@Entity
@Table(name = "TBAA_USER")
public class UserVo implements Serializable {
	/** */
	private static final long serialVersionUID = 4058914352122647610L;

	/** 아이디 */
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 20)
	private String userId;

	/** 이름 */
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	/**
	 * MD5로 암호화
	 *
	 * @see com.setvect.nowhappy.ApplicationConstant.ApplicationConstant#PASSWD_ALGORITHM
	 */
	@Column(name = "PASSWD", nullable = false, length = 50)
	private String passwd;

	/** 이메일 */
	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;

	/** 관리자 여부 */
	@Column(name = "ADMIN_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean adminF;

	/** 삭제 여부 */
	@Column(name = "DELETE_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean deleteF;

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
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(final String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the adminF
	 */
	public boolean isAdminF() {
		return adminF;
	}

	/**
	 * @param adminF
	 *            the adminF to set
	 */
	public void setAdminF(final boolean adminF) {
		this.adminF = adminF;
	}

	/**
	 * @return the deleteF
	 */
	public boolean isDeleteF() {
		return deleteF;
	}

	/**
	 * @param deleteF
	 *            the deleteF to set
	 */
	public void setDeleteF(final boolean deleteF) {
		this.deleteF = deleteF;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserVo other = (UserVo) obj;
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

}
