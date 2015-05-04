package com.setvect.nowhappy.board.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * 게시판 설정
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBBA_BOARD_MANAGER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BoardVo {
	@Id
	@Column(name = "BOARD_CODE")
	private String boardCode;

	@Column(name = "NAME")
	private String name;

	/** 총 게시물 파일 업로드 제한 */
	@Column(name = "UPLOAD_LIMIT")
	private int uploadLimit;

	@Column(name = "REPLY_F")
	@Type(type = "yes_no")
	private boolean replyF;

	@Column(name = "COMMENT_F")
	@Type(type = "yes_no")
	private boolean commentF;

	@Column(name = "ATTACH_F")
	@Type(type = "yes_no")
	private boolean attachF;

	/** 암호화 게시물 허용 여부 */
	@Column(name = "ENCODE_F")
	@Type(type = "yes_no")
	private boolean encodeF;

	/** 게시판 삭제 여부 */
	@Column(name = "DELETE_F")
	@Type(type = "yes_no")
	private boolean deleteF;

	/**
	 * @return the boardCode
	 */
	public String getBoardCode() {
		return boardCode;
	}

	/**
	 * @param boardCode
	 *            the boardCode to set
	 */
	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
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
	 * @return the uploadLimit
	 */
	public int getUploadLimit() {
		return uploadLimit;
	}

	/**
	 * @param uploadLimit
	 *            the uploadLimit to set
	 */
	public void setUploadLimit(int uploadLimit) {
		this.uploadLimit = uploadLimit;
	}

	/**
	 * @return the replyF
	 */
	public boolean isReplyF() {
		return replyF;
	}

	/**
	 * @param replyF
	 *            the replyF to set
	 */
	public void setReplyF(boolean replyF) {
		this.replyF = replyF;
	}

	/**
	 * @return the commentF
	 */
	public boolean isCommentF() {
		return commentF;
	}

	/**
	 * @param commentF
	 *            the commentF to set
	 */
	public void setCommentF(boolean commentF) {
		this.commentF = commentF;
	}

	/**
	 * @return the pdsF
	 */
	public boolean isAttachF() {
		return attachF;
	}

	/**
	 * @param pdsF
	 *            the pdsF to set
	 */
	public void setAttachF(boolean pdsF) {
		this.attachF = pdsF;
	}

	/**
	 * @return the encodeF
	 */
	public boolean isEncodeF() {
		return encodeF;
	}

	/**
	 * @param encodeF
	 *            the encodeF to set
	 */
	public void setEncodeF(boolean encodeF) {
		this.encodeF = encodeF;
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

}
