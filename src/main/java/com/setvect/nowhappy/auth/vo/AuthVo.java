package com.setvect.nowhappy.auth.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 권한 정보
 * 
 * @version $Id: Board.java 54 2010-08-09 14:25:54Z setvect@naver.com $
 */
@Entity
@Table(name = "TBAB_AUTH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthVo {
	@Id
	@Column(name = "AUTH_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int authSeq;

	/** 권한 구분 이름 */
	@Column(name = "NAME")
	private String name;

	@Column(name = "URL")
	private String url;

	/** 총 게시물 파일 업로드 제한 */
	@Column(name = "PARAMETER")
	private String parameter;
	/** 권한 부여 여부 */
	@Transient
	private boolean authHave;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the have
	 */
	public boolean isAuthHave() {
		return authHave;
	}

	/**
	 * @param have
	 *            the have to set
	 */
	public void setAuthHave(boolean have) {
		this.authHave = have;
	}

}
