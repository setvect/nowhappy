package com.setvect.nowhappy.user.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.auth.AuthMapSearch;
import com.setvect.nowhappy.auth.AuthSearch;
import com.setvect.nowhappy.auth.vo.AuthVo;
import com.setvect.nowhappy.auth.vo.AuthMapVo;
import com.setvect.nowhappy.auth.vo.AuthMapVoKey;
import com.setvect.nowhappy.user.dao.UserDao;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;
import com.setvect.nowhappy.util.StringEtcUtil;

/**
 * 회원관리 DAO
 */
public abstract class AbstractUserDao implements UserDao {
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public UserVo getUser(String userId) {
		Session session = sessionFactory.getCurrentSession();
		return (UserVo) session.get(UserVo.class, userId);
	}

	@Override
	public GenericPage<UserVo> listUser(UserSearchCondition search) {
		Session session = sessionFactory.getCurrentSession();

		String q = "select count(*) from UserVo " + getUserWhere(search);
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from UserVo " + getUserWhere(search) + " order by userId ";
		query = session.createQuery(q);
		// query.setFirstResult(search.getStartNumber());
		// query.setMaxResults(search.getPagePerItemCount());

		@SuppressWarnings("unchecked")
		List<UserVo> resultList = query.list();

		GenericPage<UserVo> resultPage = new GenericPage<UserVo>(resultList, search.getStartCursor(), totalCount);
		return resultPage;
	}

	private String getUserWhere(UserSearchCondition search) {
		String where = "where deleteF = 'N' ";

		if (StringUtils.isNotEmpty(search.getSearchId())) {
			where += " and userId like " + StringEtcUtil.getSqlStringLike(search.getSearchId());
		}

		if (StringUtils.isNotEmpty(search.getSearchName())) {
			where += " and name like " + StringEtcUtil.getSqlStringLike(search.getSearchName());
		}
		return where;
	}

	@Override
	public void insertUser(UserVo user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
		session.flush();
	}

	@Override
	public void updateUser(UserVo user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
		session.flush();
	}

	@Override
	public void deleteUser(String userId) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getUser(userId));
		session.flush();
	}

	// ---------------- 권한
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
		}
		else if (!StringUtilAd.isEmpty(search.getSearchName())) {
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

	// ---------------- 권한 맵핑
	public AuthMapVo getAuthMap(AuthMapVoKey key) {
		Session session = sessionFactory.getCurrentSession();
		return (AuthMapVo) session.get(AuthMapVo.class, key);
	}

	public GenericPage<AuthMapVo> getAuthMapPagingList(AuthMapSearch search) {
		Session session = sessionFactory.getCurrentSession();

		String q = "select count(*) from AuthMapVo " + getAuthMapWhere(search);
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from AuthMapVo " + getAuthMapWhere(search) + " order by key.userId ";
		query = session.createQuery(q);
		query.setFirstResult(search.getStartCursor());
		query.setMaxResults(search.getReturnCount());

		@SuppressWarnings("unchecked")
		List<AuthMapVo> resultList = query.list();

		GenericPage<AuthMapVo> resultPage = new GenericPage<AuthMapVo>(resultList, search.getStartCursor(), totalCount,
				search.getReturnCount());
		return resultPage;
	}

	private String getAuthMapWhere(AuthMapSearch search) {
		String where = "where 1 = 1 ";

		// 두개가 동새에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(search.getSearchUserId())) {
			where += " and key.userId = " + StringUtilAd.getSqlString(search.getSearchUserId());
		}
		else if (search.getSearchAuthSeq() != 0) {
			where += " and key.authSeq = " + search.getSearchAuthSeq();
		}
		return where;
	}

	public void createAuthMap(AuthMapVo authMap) {
		Session session = sessionFactory.getCurrentSession();
		session.save(authMap);
		session.flush();
	}

	public void removeAuthMap(AuthMapVoKey key) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getAuthMap(key));
		session.flush();
	}

	public void removeAuthMapForUserId(String userId) {
		Session session = sessionFactory.getCurrentSession();

		// Map 정보 삭제
		String q = "delete from AuthMapVo where key.userId = ?";
		Query query = session.createQuery(q);
		query.setParameter(0, userId);
		query.executeUpdate();
		session.flush();
	}
}
