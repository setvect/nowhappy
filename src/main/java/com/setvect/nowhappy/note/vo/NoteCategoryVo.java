package com.setvect.nowhappy.note.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.setvect.common.util.TreeItem;

@Entity
@Table(name = "TBDA_NOTE_CATEGORY")
public class NoteCategoryVo implements Serializable, TreeItem<Integer> {
	/** ROOT 카테고리 아이디 */
	public static final int ROOT_CATEGORY_ID = 0;

	private static final long serialVersionUID = -8586768191354097869L;

	/** 일련번호 */
	@Id
	@Column(name = "CATEGORY_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int categorySeq;

	/**
	 * 부모 카테고리 아이디
	 * 
	 * @see #categorySeq
	 */
	@Column(name = "PARENT_ID")
	private int parentId;

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

	/** 카테고리 Depth 단계. 0부터 시작 */
	@Transient
	private int depth;

	/** 하위 카테고리 */
	@Transient
	private List<NoteCategoryVo> children;

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

	/**
	 * @return the children
	 */
	public List<NoteCategoryVo> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<NoteCategoryVo> children) {
		this.children = children;
	}

	/**
	 * 이름 순으로 오름차순 정렬
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(TreeItem<Integer> o) {
		NoteCategoryVo oCategory = (NoteCategoryVo) o;
		int compare = name.compareTo(oCategory.name);
		if (compare != 0) {
			return compare;
		}
		else {
			return getId().compareTo(oCategory.getId());
		}
	}

	@Override
	public Integer getId() {
		return categorySeq;
	}

	@Override
	public Integer getParentId() {
		return parentId;
	}
	

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Override
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * 카테고리 Depth 단계. 0부터 시작
	 * 
	 * @return the level
	 */
	public int getDepth() {
		return depth;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
