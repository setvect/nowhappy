package com.setvect.nowhappy.attach.vo;

import java.io.File;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 첨부파일
 */
@Entity
@Table(name = "TBYA_ATTACH_FILE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttachFileVo {

	@Id
	@Column(name = "ATTACH_FIlE_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int attachFileSeq;

	@Column(name = "MODULE_NAME")
	@Enumerated(EnumType.STRING)
	private AttachFileModule moduleName;

	@Column(name = "MODULE_ID")
	private String moduleId;

	@Column(name = "ORIGINAL_NAME")
	private String originalName;

	@Column(name = "SAVE_NAME")
	private String saveName;

	@Column(name = "SIZE")
	private int size;

	/** 아이디 */
	@Column(name = "USER_ID")
	private String userId;
	/** 등록일 */
	@Column(name = "REG_DATE")
	private Date regDate;

	/** 등록자 */
	@Transient
	private UserVo user;

	public int getAttachFileSeq() {
		return attachFileSeq;
	}

	public void setAttachFileSeq(int attachFileSeq) {
		this.attachFileSeq = attachFileSeq;
	}

	public AttachFileModule getModuleName() {
		return moduleName;
	}

	public void setModuleName(AttachFileModule moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleID) {
		this.moduleId = moduleID;
	}

	public String getOriginalName() {
		return originalName;
	}

	/**
	 * @return Base64로 변환 된 첨부파일 실제 이름
	 */
	public String getOriginalNameEncode() {
		return StringUtilAd.encodeString(originalName);
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getSaveName() {
		return saveName;
	}

	/**
	 * @return 웹루트를 기준으로 첨부파일 경로.(파일명 포함)
	 */
	public String getSavePath() {
		File basePath = new File(ApplicationConstant.FileUpload.ATTACH_PATH);
		File f = new File(basePath, saveName);
		return f.getPath();
	}

	/**
	 * @return Base64로 변환 된 첨부파일 경로
	 */
	public String getSavePathEncode() {
		return StringUtilAd.encodeString(getSavePath());
	}

	/**
	 * @return 파일 URL 경로
	 */
	public String getUrl() {
		return "/" + ApplicationConstant.FileUpload.ATTACH_PATH + "/" + saveName;
	}

	/**
	 * @param saveName
	 *            the saveName to set
	 */
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the user
	 */
	public UserVo getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserVo user) {
		this.user = user;
	}

	/**
	 * @return 등록자 이름
	 */
	public String getUserName() {
		return user.getName();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
