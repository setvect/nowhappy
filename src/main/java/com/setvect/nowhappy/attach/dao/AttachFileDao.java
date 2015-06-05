package com.setvect.nowhappy.attach.dao;

import java.util.List;

import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.attach.vo.AttachFileVo;

/**
 * 첨부파일
 * 
 * @version $Id$
 */
public interface AttachFileDao {
	/**
	 * @param attachFileSeq
	 *            일련번호
	 * @return 첨부파일
	 */
	public AttachFileVo getAttachFile(int attachFileSeq);

	/**
	 * @param moduleName
	 *            모듈 이름
	 * @param moduleItemId
	 *            모듈 아이디
	 * @return 첨부파일 목록
	 */
	public List<AttachFileVo> listAttachFile(AttachFileModule moduleName, String moduleItemId);

	/**
	 * @param attachFile
	 *            첨부파일 저장
	 */
	public void createAttachFile(AttachFileVo attachFile);

	/**
	 * @param seq
	 *            첨부파일 번호
	 */
	public void removeAttachFile(int seq);

}
