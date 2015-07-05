package com.setvect.nowhappy.note.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "TBDA_NOTE_CATEGORY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NoteCategoryVo implements Serializable {

	/** */
	private static final long serialVersionUID = -8586768191354097869L;

	/** 일련번호 */
	@Id
	@Column(name = "CATEGORY_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int categorySeq;

	/** 이름 */
	@Column(name = "NAME")
	private String name;

	/** 처음 등록일 */
	@Column(name = "REG_DATE")
	private Date regDate;

	/** 삭제여부 */
	@Column(name = "DELETE_F")
	@Type(type = "yes_no")
	private boolean deleteF;

	/**
	 * @return the categorySeq
	 */
	public int getCategorySeq() {
		return categorySeq;
	}

	/**
	 * @param categorySeq
	 *            the categorySeq to set
	 */
	public void setCategorySeq(int categorySeq) {
		this.categorySeq = categorySeq;
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
	 * @return the regDate
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate
	 *            the regDate to set
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
