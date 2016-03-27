package com.setvect.nowhappy.board.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import com.setvect.nowhappy.attach.vo.AttachFileVo;
import com.setvect.nowhappy.util.DateDiff;

/**
 * 게시물 VO
 *
 * @version $Id$
 */
@Entity
@Table(name = "TBBB_BOARD_ARTICLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BoardArticleVo {
	/** */
	@Id
	@Column(name = "ARTICLE_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int articleSeq;

	/** */
	@Column(name = "BOARD_CODE")
	private String boardCode;

	/** */
	@Column(name = "USER_ID")
	private String userId;

	/** */
	@Column(name = "IDX1")
	private int idx1;

	/** */
	@Column(name = "IDX2")
	private int idx2;

	/** */
	@Column(name = "IDX3")
	private int idx3;

	/** 깊이 1부터 시작 */
	@Column(name = "DEPTH_LEVEL")
	private int depthLevel;

	/** */
	@Column(name = "TITLE")
	private String title;

	/** */
	@Column(name = "NAME")
	private String name;

	/** */
	@Column(name = "EMAIL")
	private String email;

	/** */
	@Column(name = "PASSWD")
	private String passwd;

	/** */
	@Column(name = "CONTENT")
	private String content;

	/** */
	@Column(name = "IP")
	private String ip;

	/** 조회수 */
	@Column(name = "HIT_COUNT")
	private int hitCount;

	/** 암호화된 글 여부 */
	@Column(name = "ENCODE_F")
	@Type(type = "yes_no")
	private boolean encodeF;

	/** */
	@Column(name = "REG_DATE")
	private Date regDate;

	/** 게시물 삭제 여부 */
	@Column(name = "DELETE_F")
	@Type(type = "yes_no")
	private boolean deleteF;

	@Transient
	private MultipartFile[] attachFile;

	/** 첨부파일 */
	@Transient
	private List<AttachFileVo> attach;

	/**
	 * @return the articleSeq
	 */
	public int getArticleSeq() {
		return articleSeq;
	}

	/**
	 * @param articleSeq
	 *            the articleSeq to set
	 */
	public void setArticleSeq(int articleSeq) {
		this.articleSeq = articleSeq;
	}

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
	 * @return the memberId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setUserId(String memberId) {
		this.userId = memberId;
	}

	/**
	 * @return the idx1
	 */
	public int getIdx1() {
		return idx1;
	}

	/**
	 * @param idx1
	 *            the idx1 to set
	 */
	public void setIdx1(int idx1) {
		this.idx1 = idx1;
	}

	/**
	 * @return the idx2
	 */
	public int getIdx2() {
		return idx2;
	}

	/**
	 * @param idx2
	 *            the idx2 to set
	 */
	public void setIdx2(int idx2) {
		this.idx2 = idx2;
	}

	/**
	 * @return the idx3
	 */
	public int getIdx3() {
		return idx3;
	}

	/**
	 * @param idx3
	 *            the idx3 to set
	 */
	public void setIdx3(int idx3) {
		this.idx3 = idx3;
	}

	/**
	 * @return the depthLevel
	 */
	public int getDepthLevel() {
		return depthLevel;
	}

	/**
	 * @param depthLevel
	 *            the depthLevel to set
	 */
	public void setDepthLevel(int depthLevel) {
		this.depthLevel = depthLevel;
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
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the hitCount
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * @param hitCount
	 *            the hitCount to set
	 */
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
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

	public String getRegDateDiff() {
		return DateDiff.diff(new Date(), regDate);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
