package com.setvect.nowhappy.ctmemo.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.ctmemo.CtmemoSearchCondition;
import com.setvect.nowhappy.ctmemo.dao.CtmemoDao;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.ctmemo.vo.WorkspaceVo;

@Service
public class CtmemoService {
	@Inject
	private CtmemoDao ctmemoDao;

	public WorkspaceVo getWorkspace(int workspaceSeq) {
		return ctmemoDao.getWorkspace(workspaceSeq);
	}

	/**
	 * 워크스페이스 전체 목록
	 * 
	 * @return
	 */
	public List<WorkspaceVo> listWorkspace() {
		return ctmemoDao.listWorkspace();
	}

	/**
	 * 워크스페이스 등록
	 */
	public void insertWorkspace(WorkspaceVo workspace) {
		ctmemoDao.insertWorkspace(workspace);
	}

	/**
	 * 워크스페이스 수정
	 * 
	 * @param workspace
	 */
	public void updateWorkspace(WorkspaceVo workspace) {
		ctmemoDao.updateWorkspace(workspace);
	}

	/**
	 * 워크스페이스 삭제
	 * 
	 * @param workspaceSeq
	 */
	public void deleteWorkspace(int workspaceSeq) {
		ctmemoDao.deleteWorkspace(workspaceSeq);
	}

	public CtmemoVo getCtmemo(int ctmemoId) {
		return ctmemoDao.getCtmemo(ctmemoId);
	}

	/**
	 * z-index 최대 값
	 * 
	 * @return
	 */
	public int getMaxZindex() {
		return ctmemoDao.getMaxZindex();
	}

	public List<CtmemoVo> listCtmemo(CtmemoSearchCondition condition) {
		return ctmemoDao.listCtmemo(condition);
	}

	public void insertCtmemo(CtmemoVo ctmemo) {
		ctmemoDao.insertCtmemo(ctmemo);
	}

	/**
	 * 새로운 메모장 생성<br>
	 * 생성과 동시에 DB에 저장
	 * 
	 * @return
	 */
	public CtmemoVo newMemo() {
		CtmemoVo ctmemo = new CtmemoVo();
		ctmemo.setContent(" ");
		ctmemo.setBgCss(ApplicationConstant.Style.BGSTYLE_1);
		ctmemo.setFontCss(ApplicationConstant.Style.FONTSTYLE_1);
		ctmemo.setWidth(150);
		ctmemo.setHeight(150);
		ctmemo.setPositionX(1);
		ctmemo.setPositionY(1);
		ctmemo.setzIndex(getMaxZindex());
		Date date = new Date();
		ctmemo.setRegDate(date);
		ctmemo.setUptDate(date);
		insertCtmemo(ctmemo);
		return ctmemo;
	}

	/**
	 * 삭제 취소
	 * 
	 * @param ctmemoSeq
	 * @return
	 */
	public CtmemoVo undeleteCtmemo(int ctmemoSeq) {
		CtmemoVo memo = getCtmemo(ctmemoSeq);
		memo.setDeleteF(false);
		updateCtmemo(memo);
		return memo;
	}

	public void updateCtmemo(CtmemoVo ctmemo) {
		ctmemoDao.updateCtmemo(ctmemo);
	}

	public void removeCtmemo(int ctmemoId) {
		CtmemoVo ctmemo = getCtmemo(ctmemoId);
		ctmemo.setDeleteF(true);
		updateCtmemo(ctmemo);
	}

}
