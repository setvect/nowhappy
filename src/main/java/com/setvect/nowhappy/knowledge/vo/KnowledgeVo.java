package com.setvect.nowhappy.knowledge.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.setvect.nowhappy.code.vo.CodeVo;

/**
 * 지식정보
 */
@Entity
@Table(name = "TBEA_KNOWLEDGE")
public class KnowledgeVo {
	/** key */
	@Id
	@Column(name = "KNOWLEDGE_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int knowledgeSeq;

	/** 분류 코드 */
	@Column(name = "CLASSIFY_C", nullable = false, length = 20)
	private String classifyC;

	/** 분류 코드 */
	@Transient
	private CodeVo classifyCode;

	/** 문제 */
	@Column(name = "PROBLEM", nullable = false)
	@Lob
	private String problem;

	/** 해법 */
	@Column(name = "SOLUTION", nullable = true)
	@Lob
	private String solution;

	/** 등록일 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/** 삭제여부 */
	@Column(name = "DELETE_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean deleteF;

	/**
	 * @return the knowledgeSeq
	 */
	public int getKnowledgeSeq() {
		return knowledgeSeq;
	}

	/**
	 * @param knowledgeSeq
	 *            the knowledgeSeq to set
	 */
	public void setKnowledgeSeq(final int knowledgeSeq) {
		this.knowledgeSeq = knowledgeSeq;
	}

	/**
	 * @return the classifyC
	 */
	public String getClassifyC() {
		return classifyC;
	}

	/**
	 * @param classifyC
	 *            the classifyC to set
	 */
	public void setClassifyC(final String classifyC) {
		this.classifyC = classifyC;
	}

	/**
	 * @return the problem
	 */
	public String getProblem() {
		return problem;
	}

	/**
	 * @param problem
	 *            the problem to set
	 */
	public void setProblem(final String problem) {
		this.problem = problem;
	}

	/**
	 * @return the solution
	 */
	public String getSolution() {
		return solution;
	}

	/**
	 * @param solution
	 *            the solution to set
	 */
	public void setSolution(final String solution) {
		this.solution = solution;
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
	public void setRegDate(final Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the classifyCode
	 */
	public CodeVo getClassifyCode() {
		return classifyCode;
	}

	/**
	 * @param classifyCode
	 *            the classifyCode to set
	 */
	public void setClassifyCode(final CodeVo classifyCode) {
		this.classifyCode = classifyCode;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
