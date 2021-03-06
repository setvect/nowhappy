package com.setvect.nowhappy.ctmemo.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 메모장 워크스페이스 정보
 */
@Entity
@Table(name = "TBCB_WORKSPACE")
public class WorkspaceVo {
	/** 일련번호 */
	@Id
	@Column(name = "WORKSPACE_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int workspaceSeq;

	/** 내용 */
	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

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
	public void setTitle(final String title) {
		this.title = title;
	}
}
