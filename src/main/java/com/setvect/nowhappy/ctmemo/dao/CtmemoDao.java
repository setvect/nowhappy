package com.setvect.nowhappy.ctmemo.dao;

import java.util.List;

import com.setvect.nowhappy.ctmemo.CtmemoSearchCondition;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.ctmemo.vo.WorkspaceVo;

/**
 * 메모장 DAO
 * 
 * @version $Id$
 */
public interface CtmemoDao {
	// 워크스페이스
	/**
	 * 워크스페이스 정보
	 * 
	 * @param workspaceSeq
	 * @return
	 */
	public WorkspaceVo getWorkspace(int workspaceSeq);

	/**
	 * 워크스페이스 전체 목록
	 * 
	 * @return
	 */
	public List<WorkspaceVo> listWorkspace();

	/**
	 * 워크스페이스 등록
	 */
	public void insertWorkspace(WorkspaceVo workspace);

	/**
	 * 워크스페이스 수정
	 * 
	 * @param workspace
	 */
	public void updateWorkspace(WorkspaceVo workspace);

	/**
	 * 워크스페이스 삭제
	 * 
	 * @param workspaceSeq
	 */
	public void deleteWorkspace(int workspaceSeq);

	// 메모장
	public CtmemoVo getCtmemo(int ctmemoId);

	/**
	 * z-index 최대 값
	 * 
	 * @return
	 */
	public int getMaxZindex();

	/**
	 * @param condition
	 * @return
	 */
	public List<CtmemoVo> listCtmemo(CtmemoSearchCondition condition);

	/**
	 * @param board
	 */
	public void insertCtmemo(CtmemoVo ctmemo);

	/**
	 * @param article
	 */
	public void updateCtmemo(CtmemoVo ctmemo);

	/**
	 * @param articleSeq
	 */
	public void deleteCtmemo(int ctmemoId);

}
