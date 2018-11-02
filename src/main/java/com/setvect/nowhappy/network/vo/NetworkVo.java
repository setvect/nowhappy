package com.setvect.nowhappy.network.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "TBFA_NETWORK")
public class NetworkVo {
	@Id
	@Column(name = "NETWORK_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int networkSeq;
	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

	@Column(name = "CONTENT", nullable = false)
	@Lob
	private String jsonData;

	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	@Column(name = "DELETE_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean deleteF;

	/**
	 * @return the networkSeq
	 */
	public int getNetworkSeq() {
		return networkSeq;
	}

	/**
	 * @param networkSeq
	 *            the networkSeq to set
	 */
	public void setNetworkSeq(int networkSeq) {
		this.networkSeq = networkSeq;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the jsonData
	 */
	public String getJsonData() {
		return jsonData;
	}

	/**
	 * @param jsonData
	 *            the jsonData to set
	 */
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	/**
	 * @return the regRate
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate
	 *            the regRate to set
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
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
	public void setDeleteF(boolean deleteF) {
		this.deleteF = deleteF;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
