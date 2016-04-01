package com.setvect.nowhappy.code.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 코드
 */
@Entity
@Table(name = "TBYC_CODE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CodeVo {
	@Id
	@Column(name = "CODE_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int codeSeq;

	@Column(name = "MAJOR_CODE")
	private String majorCode;
	@Column(name = "MINOR_CODE")
	private String minorCode;
	@Column(name = "CODE_VALUE")
	private String codeValue;
	@Column(name = "ORDER_NO")
	private int orderNo;

	/**
	 * @return the codeSeq
	 */
	public int getCodeSeq() {
		return codeSeq;
	}

	/**
	 * @param codeSeq
	 *            the codeSeq to set
	 */
	public void setCodeSeq(int codeSeq) {
		this.codeSeq = codeSeq;
	}

	/**
	 * @return the majorCode
	 */
	public String getMajorCode() {
		return majorCode;
	}

	/**
	 * @param majorCode
	 *            the majorCode to set
	 */
	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	/**
	 * @return the minorCode
	 */
	public String getMinorCode() {
		return minorCode;
	}

	/**
	 * @param minorCode
	 *            the minorCode to set
	 */
	public void setMinorCode(String minorCode) {
		this.minorCode = minorCode;
	}

	/**
	 * @return the codeValue
	 */
	public String getCodeValue() {
		return codeValue;
	}

	/**
	 * @param codeValue
	 *            the codeValue to set
	 */
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	/**
	 * @return the orderNo
	 */
	public int getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNo to set
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
