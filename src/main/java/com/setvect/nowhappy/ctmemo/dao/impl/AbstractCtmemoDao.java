package com.setvect.nowhappy.ctmemo.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.setvect.nowhappy.ctmemo.CtmemoSearchCondition;
import com.setvect.nowhappy.ctmemo.dao.CtmemoDao;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.ctmemo.vo.WorkspaceVo;

/**
 * 하이버네이트을 이용한 메모장 DAO<br>
 * DBMS에 맞추어 상속받아 사용.
 * 
 * @version $Id$
 */
public abstract class AbstractCtmemoDao implements CtmemoDao {

	@Inject
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.nowhappy.ctmemo.dao.CtmemoDao#getWorkspace(int)
	 */
	public WorkspaceVo getWorkspace(int workspaceSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (WorkspaceVo) session.get(WorkspaceVo.class, workspaceSeq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.nowhappy.ctmemo.dao.CtmemoDao#listWorkspace()
	 */
	public List<WorkspaceVo> listWorkspace() {
		Session session = sessionFactory.getCurrentSession();
		String q = " from WorkspaceVo order by workspaceSeq";
		Query query = session.createQuery(q);

		@SuppressWarnings("unchecked")
		List<WorkspaceVo> resultList = query.list();
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.nowhappy.ctmemo.dao.CtmemoDao#insertWorkspace(com.setvect.nowhappy.ctmemo.vo.WorkspaceVo)
	 */
	public void insertWorkspace(WorkspaceVo workspace) {
		Session session = sessionFactory.getCurrentSession();
		session.save(workspace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.nowhappy.ctmemo.dao.CtmemoDao#updateWorkspace(com.setvect.nowhappy.ctmemo.vo.WorkspaceVo)
	 */
	public void updateWorkspace(WorkspaceVo workspace) {
		Session session = sessionFactory.getCurrentSession();
		session.update(workspace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.setvect.nowhappy.ctmemo.dao.CtmemoDao#deleteWorkspace(int)
	 */
	public void deleteWorkspace(int workspaceSeq) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getWorkspace(workspaceSeq));
	}

	// 메모장
	@Override
	public CtmemoVo getCtmemo(int ctmemoId) {
		Session session = sessionFactory.getCurrentSession();
		return (CtmemoVo) session.get(CtmemoVo.class, ctmemoId);
	}

	@Override
	public int getMaxZindex() {
		Session session = sessionFactory.getCurrentSession();
		String q = " select COALESCE(max(zIndex) + 1, 1) from CtmemoVo where deleteF = 'N'";
		Query query = session.createQuery(q);
		return (Integer) query.uniqueResult();
	}

	@Override
	public List<CtmemoVo> listCtmemo(CtmemoSearchCondition condition) {
		Session session = sessionFactory.getCurrentSession();
		String q = " from CtmemoVo where workspaceSeq = :workspaceSeq and deleteF = 'N' order by uptDate desc";
		Query query = session.createQuery(q);
		query.setParameter("workspaceSeq", condition.getSearchWorkspaceSeq());

		@SuppressWarnings("unchecked")
		List<CtmemoVo> resultList = query.list();
		return resultList;
	}

	@Override
	public void insertCtmemo(CtmemoVo ctmemo) {
		Session session = sessionFactory.getCurrentSession();
		session.save(ctmemo);
	}

	@Override
	public void updateCtmemo(CtmemoVo ctmemo) {
		Session session = sessionFactory.getCurrentSession();
		session.update(ctmemo);
	}

	@Override
	public void deleteCtmemo(int ctmemoId) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getCtmemo(ctmemoId));
	}
}

