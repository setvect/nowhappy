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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import com.setvect.nowhappy.attach.vo.AttachFileVo;

@Entity
@Table(name = "TBDB_NOTE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NoteVo implements Serializable {

	/** */
	private static final long serialVersionUID = -2975824645025088507L;

	/** 일련번호 */
	@Id
	@Column(name = "NOTE_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int noteSeq;

	/** 카테고리 */
	@Column(name = "CATEGORY_SEQ")
	private int categorySeq;

	/** 제목 */
	@Column(name = "TITLE")
	private String title;

	/** 내용 */
	@Column(name = "CONTENT")
	private String content;

	/** 마지막 수정일 */
	@Column(name = "UPT_DATE")
	private Date uptDate;

	/** 처음 등록일 */
	@Column(name = "REG_DATE")
	private Date regDate;

	/** 삭제여부 */
	@Column(name = "DELETE_F")
	@Type(type = "yes_no")
	private boolean deleteF;

	@Transient
	private MultipartFile[] attachFile;

	/** 첨부파일 */
	@Transient
	private List<AttachFileVo> attach;

	/**
	 * @return the noteSeq
	 */
	public int getNoteSeq() {
		return noteSeq;
	}

	/**
	 * @param noteSeq
	 *            the noteSeq to set
	 */
	public void setNoteSeq(int noteSeq) {
		this.noteSeq = noteSeq;
	}

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
	public String getTitle() {
		return title;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setTitle(String name) {
		this.title = name;
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
	public void setContent(String content) {
		this.content = content;
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
	public void setUptDate(Date uptDate) {
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
	 * @return the attachFile
	 */
	public MultipartFile[] getAttachFile() {
		return attachFile;
	}

	/**
	 * @param attachFile
	 *            the attachFile to set
	 */
	public void setAttachFile(MultipartFile[] attachFile) {
		this.attachFile = attachFile;
	}

	/**
	 * @return the attach
	 */
	public List<AttachFileVo> getAttach() {
		return attach;
	}

	/**
	 * @param attach
	 *            the attach to set
	 */
	public void setAttach(List<AttachFileVo> attach) {
		this.attach = attach;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
