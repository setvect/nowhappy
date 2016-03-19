package com.setvect.nowhappy.user.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.auth.AuthSearch;
import com.setvect.nowhappy.auth.vo.AuthVo;
import com.setvect.nowhappy.user.dao.AuthDao;

/**
 * 회원관리 DAO
 */
public abstract class AbstractAuthDao implements AuthDao {
	@Autowired
	SessionFactory sessionFactory;

	public AuthVo getAuth(int authSeq) {
		Session session = sessionFactory.getCurrentSession();
		return (AuthVo) session.get(AuthVo.class, authSeq);
	}

	public GenericPage<AuthVo> getAuthPagingList(AuthSearch search) {
		Session session = sessionFactory.getCurrentSession();

		String q = "select count(*) from AuthVo " + getAuthWhere(search);
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from AuthVo " + getAuthWhere(search) + " order by name";
		query = session.createQuery(q);
		query.setFirstResult(search.getStartCursor());
		query.setMaxResults(search.getReturnCount());

		@SuppressWarnings("unchecked")
		List<AuthVo> resultList = query.list();

		GenericPage<AuthVo> resultPage = new GenericPage<AuthVo>(resultList, search.getStartCursor(), totalCount,
				search.getReturnCount());
		return resultPage;

	}

	private String getAuthWhere(AuthSearch search) {
		String where = "where 1 = 1 ";

		// 두개가 동새에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(search.getSearchUrl())) {
			where += " and url like " + StringUtilAd.getSqlStringLike(search.getSearchUrl());
		} else if (!StringUtilAd.isEmpty(search.getSearchName())) {
			where += " and name like " + StringUtilAd.getSqlStringLike(search.getSearchName());
		}
		return where;
	}

	public void createAuth(AuthVo auth) {
		Session session = sessionFactory.getCurrentSession();
		session.save(auth);
		session.flush();
	}

	public void updateAuth(AuthVo auth) {
		Session session = sessionFactory.getCurrentSession();
		session.update(auth);
		session.flush();
	}

	public void removeAuth(int authSeq) {
		Session session = sessionFactory.getCurrentSession();

		// Map 정보 삭제
		String q = "delete from AuthMapVo where key.authSeq = ?";
		Query query = session.createQuery(q);
		query.setParameter(0, authSeq);
		query.executeUpdate();

		session.delete(getAuth(authSeq));
		session.flush();
	}
}
