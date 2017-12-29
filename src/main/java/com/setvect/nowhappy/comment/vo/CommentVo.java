package com.setvect.nowhappy.comment.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.setvect.nowhappy.comment.service.CommentModule;
import com.setvect.nowhappy.util.DateDiff;

/**
 * 코멘트
 */
@Entity
@Table(name = "TBGA_COMMENT")
public class CommentVo {

	/** 일련번호 */
	@Id
	@Column(name = "COMMENT_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int commentSeq;

	/** 모듈이름 */
	@Column(name = "MODULE_NAME")
	@Enumerated(EnumType.STRING)
	private CommentModule moduleName;

	/** 모듈 아이디 */
	@Column(name = "MODULE_ID")
	private String moduleId;

	/** 아이디 */
	@Column(name = "USER_ID")
	private String userId;

	/** 내용 */
	@Column(name = "CONTENT")
	private String content;

	/** 등록일 */
	@Column(name = "REG_DATE")
	private Date regDate;

	/**
	 * @return 일련번호
	 */
	public int getCommentSeq() {
		return commentSeq;
	}

	/**
	 * @param commentSeq
	 *            일련번호
	 */
	public void setCommentSeq(final int commentSeq) {
		this.commentSeq = commentSeq;
	}

	/**
	 * @return 모듈이름
	 */
	public CommentModule getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName
	 *            모듈이름
	 */
	public void setModuleName(final CommentModule moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return 모듈 아이디
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId
	 *            모듈 아이디
	 */
	public void setModuleId(final String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return 아이디
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            아이디
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * @return 내용
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            내용
	 */
	public void setContent(final String content) {
		this.content = content;
	}

	/**
	 * @return 등록일
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate
	 *            등록일
	 */
	public void setRegDate(final Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return 등록시간을 상대적으로 표시
	 */
	public String getRegDateDiff() {
		return DateDiff.diff(new Date(), regDate);
	}
}
