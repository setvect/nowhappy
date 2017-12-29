package com.setvect.nowhappy.board.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 게시판 설정
 */
@Entity
@Table(name = "TBBA_BOARD_MANAGER")
public class BoardVo {
	/** 게시판 코드 */
	@Id
	@Column(name = "BOARD_CODE", nullable = false, length = 20)
	private String boardCode;

	/** 게시판 이름 */
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	/** 총 게시물 파일 업로드 제한 */
	@Column(name = "UPLOAD_LIMIT", nullable = false)
	private int uploadLimit;

	/** 답변 여부 */
	@Column(name = "REPLY_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean replyF;

	/** 코멘트 여부 */
	@Column(name = "COMMENT_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean commentF;

	/** 자료실 여부 */
	@Column(name = "ATTACH_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean attachF;

	/** 암호화 게시물 허용 여부 */
	@Column(name = "ENCODE_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean encodeF;

	/** 게시판 삭제 여부 */
	@Column(name = "DELETE_F", nullable = false, length = 1)
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
	public void setBoardCode(final String boardCode) {
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
	public void setName(final String name) {
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
	public void setUploadLimit(final int uploadLimit) {
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
	public void setReplyF(final boolean replyF) {
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
	public void setCommentF(final boolean commentF) {
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
	public void setAttachF(final boolean pdsF) {
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
	public void setEncodeF(final boolean encodeF) {
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
	public void setDeleteF(final boolean deleteF) {
		this.deleteF = deleteF;
	}

}
