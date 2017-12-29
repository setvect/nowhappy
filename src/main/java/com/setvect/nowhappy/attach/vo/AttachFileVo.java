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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
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
public class AttachFileVo {

	/** 일련번호 */
	@Id
	@Column(name = "ATTACH_FIlE_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int attachFileSeq;

	/** 모듈이름 */
	@Column(name = "MODULE_NAME", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private AttachFileModule moduleName;

	/** 모듈내 항목 번호 */
	@Column(name = "MODULE_ID", nullable = false, length = 50)
	private String moduleId;

	/** 원본파일명 */
	@Column(name = "ORIGINAL_NAME", nullable = false, length = 250)
	private String originalName;

	/** 저장파일명 */
	@Column(name = "SAVE_NAME", nullable = false, length = 250)
	private String saveName;

	/** 파일 사이즈 */
	@Column(name = "SIZE", nullable = false)
	private int size;

	/** 아이디 */
	@Column(name = "USER_ID", nullable = false, length = 20)
	private String userId;

	/** 등록일 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/** 등록자 */
	@Transient
	private UserVo user;

	/**
	 * @return 일련번호
	 */
	public int getAttachFileSeq() {
		return attachFileSeq;
	}

	/**
	 * @param attachFileSeq
	 *            일련번호
	 */
	public void setAttachFileSeq(final int attachFileSeq) {
		this.attachFileSeq = attachFileSeq;
	}

	/**
	 * @return 모듈이름
	 */
	public AttachFileModule getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName
	 *            모듈이름
	 */
	public void setModuleName(final AttachFileModule moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return 모듈내 항목 번호
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleID
	 *            모듈내 항목 번호
	 */
	public void setModuleId(final String moduleID) {
		this.moduleId = moduleID;
	}

	/**
	 * @return 원본파일명
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * @return Base64로 변환 된 첨부파일 실제 이름
	 */
	public String getOriginalNameEncode() {
		return StringUtilAd.encodeString(originalName);
	}

	/**
	 * @param originalName
	 *            원본파일명
	 */
	public void setOriginalName(final String originalName) {
		this.originalName = originalName;
	}

	/**
	 * @return 저장파일명
	 */
	public String getSaveName() {
		return saveName;
	}

	/**
	 * @return 웹루트를 기준으로 첨부파일 경로.(파일명 포함)
	 */
	public String getSavePath() {
		final File basePath = new File(ApplicationConstant.FileUpload.ATTACH_PATH);
		final File f = new File(basePath, saveName);
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
	public void setSaveName(final String saveName) {
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
	public void setSize(final int size) {
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
	public void setUserId(final String userId) {
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
	public void setRegDate(final Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * 확장자를 기준으로 이미지 파일 여부 체크
	 *
	 * @return 이미지 파일이면 true
	 */
	public boolean isImage() {
		final String ext = FilenameUtils.getExtension(originalName);
		return ApplicationConstant.WebCommon.IMAGE_EXT.contains(ext.toLowerCase());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
