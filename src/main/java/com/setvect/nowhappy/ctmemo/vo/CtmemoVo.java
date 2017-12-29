package com.setvect.nowhappy.ctmemo.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * 메모장
 */
@Entity
@Table(name = "TBCA_CTMEMO")
public class CtmemoVo implements Serializable {
	private static final long serialVersionUID = 4659097867395968759L;

	/** 일련번호 */
	@Id
	@Column(name = "CTMEMO_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int ctmemoSeq;

	/** 내용 */
	@Column(name = "CONTENT", nullable = false, length = 4000)
	private String content;

	/** 글자색 css */
	@Column(name = "FONT_CSS", nullable = false, length = 20)
	private String fontCss;

	/** 배경색 css */
	@Column(name = "BG_CSS", nullable = false, length = 20)
	private String bgCss;

	/** HTML 레이어에서 Z-INDEX */
	@Column(name = "Z_INDEX", nullable = false)
	private int zIndex;

	/** 넓이(픽셀) */
	@Column(name = "WIDTH", nullable = false)
	private int width;

	/** 높이(픽셀 */
	@Column(name = "HEIGHT", nullable = false)
	private int height;

	/** 좌표 X */
	@Column(name = "POSITION_X", nullable = false)
	private int positionX;

	/** 좌표 Y */
	@Column(name = "POSITION_Y", nullable = false)
	private int positionY;

	/** 마지막 수정일 */
	@Column(name = "UPT_DATE", nullable = true)
	private Date uptDate;

	/** 처음 등록일 */
	@Column(name = "REG_DATE", nullable = true)
	private Date regDate;

	/** 삭제여부 */
	@Column(name = "DELETE_F", nullable = false)
	@Type(type = "yes_no")
	private boolean deleteF;

	/** 워크스페이스 일련번호 */
	@Column(name = "WORKSPACE_SEQ", nullable = false)
	private int workspaceSeq;

	/**
	 * @return the memoSeq
	 */
	public int getCtmemoSeq() {
		return ctmemoSeq;
	}

	/**
	 * @param memoSeq
	 *            the memoSeq to set
	 */
	public void setCtmemoSeq(final int memoSeq) {
		this.ctmemoSeq = memoSeq;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(final String content) {
		this.content = content;
	}

	/**
	 * @return the fontColor
	 */
	public String getFontCss() {
		return fontCss;
	}

	/**
	 * @param fontColor
	 *            the fontColor to set
	 */
	public void setFontCss(final String fontColor) {
		this.fontCss = fontColor;
	}

	/**
	 * @return the bgColor
	 */
	public String getBgCss() {
		return bgCss;
	}

	/**
	 * @param bgColor
	 *            the bgColor to set
	 */
	public void setBgCss(final String bgColor) {
		this.bgCss = bgColor;
	}

	/**
	 * @return the zIndex
	 */
	public int getzIndex() {
		return zIndex;
	}

	/**
	 * @param zIndex
	 *            the zIndex to set
	 */
	public void setzIndex(final int zIndex) {
		this.zIndex = zIndex;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * @return the positionX
	 */
	public int getPositionX() {
		return positionX;
	}

	/**
	 * @param positionX
	 *            the positionX to set
	 */
	public void setPositionX(final int positionX) {
		this.positionX = positionX;
	}

	/**
	 * @return the positionY
	 */
	public int getPositionY() {
		return positionY;
	}

	/**
	 * @param positionY
	 *            the positionY to set
	 */
	public void setPositionY(final int positionY) {
		this.positionY = positionY;
	}

	/**
	 * @return the uptDate
	 */
	public Date getUptDate() {
		return uptDate;
	}

	/**
	 * @param uptDate
	 *            the uptDate to set
	 */
	public void setUptDate(final Date uptDate) {
		this.uptDate = uptDate;
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
	 * @return the deleteF
	 */
	public boolean getDeleteF() {
		return deleteF;
	}

	/**
	 * @param deleteF
	 *            the deleteF to set
	 */
	public void setDeleteF(final boolean deleteF) {
		this.deleteF = deleteF;
	}

	/**
	 * @return the workspaceSeq
	 */
	public int getWorkspaceSeq() {
		return workspaceSeq;
	}

	/**
	 * @param workspaceSeq
	 *            the workspaceSeq to set
	 */
	public void setWorkspaceSeq(final int workspaceSeq) {
		this.workspaceSeq = workspaceSeq;
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
		result = prime * result + ctmemoSeq;
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		final CtmemoVo other = (CtmemoVo) obj;
		if (ctmemoSeq != other.ctmemoSeq) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
