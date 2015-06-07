package com.setvect.nowhappy.attach.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.setvect.nowhappy.attach.dao.AttachFileDao;
import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.attach.vo.AttachFileVo;

/**
 * 첨부파일
 * 
 * @version $Id$
 */
public abstract class AbstractAttachFileDao implements AttachFileDao {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public AttachFileVo getAttachFile(int attachFileSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (AttachFileVo) session.get(AttachFileVo.class, attachFileSeq);
	}

	public List<AttachFileVo> listAttachFile(AttachFileModule moduleName, String moduleItemId) {
		Session session = sessionFactory.getCurrentSession();

		String q = " from AttachFileVo where moduleName = ? and moduleId = ? order by attachFileSeq ";
		Query query = session.createQuery(q);

		query.setString(0, moduleName.name());
		query.setString(1, moduleItemId);

		@SuppressWarnings("unchecked")
		List<AttachFileVo> resultList = query.list();

		return resultList;
	}

	public void createAttachFile(AttachFileVo attachFile) {
		Session session = sessionFactory.getCurrentSession();
		session.save(attachFile);
		session.flush();
	}

	public void removeAttachFile(int seq) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getAttachFile(seq));
		session.flush();
	}

}