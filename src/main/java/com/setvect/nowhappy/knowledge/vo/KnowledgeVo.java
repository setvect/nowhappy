package com.setvect.nowhappy.knowledge.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.setvect.nowhappy.code.vo.CodeVo;

@Entity
@Table(name = "TBEA_KNOWLEDGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KnowledgeVo {
	/** key */
	@Id
	@Column(name = "KNOWLEDGE_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int knowledgeSeq;

	/** 분류 코드 */
	@Column(name = "CLASSIFY_C")
	private String classifyC;

	/** 분류 코드 */
	@Transient
	private CodeVo classifyCode;

	/** 문제 */
	@Column(name = "PROBLEM")
	private String problem;

	/** 해법 */
	@Column(name = "SOLUTION")
	private String solution;

	/** 등록일 */
	@Column(name = "REG_DATE")
	private Date regDate;

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
	public void setKnowledgeSeq(int knowledgeSeq) {
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
	public void setClassifyC(String classifyC) {
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
	public void setProblem(String problem) {
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
	public void setSolution(String solution) {
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
	public void setRegDate(Date regDate) {
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
	public void setClassifyCode(CodeVo classifyCode) {
		this.classifyCode = classifyCode;
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
