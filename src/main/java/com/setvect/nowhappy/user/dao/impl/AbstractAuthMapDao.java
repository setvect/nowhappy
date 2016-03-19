package com.setvect.nowhappy.user.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.auth.AuthMapSearch;
import com.setvect.nowhappy.auth.vo.AuthMapVo;
import com.setvect.nowhappy.auth.vo.AuthMapVoKey;
import com.setvect.nowhappy.user.dao.AuthMapDao;

/**
 * 회원관리 DAO
 */
public abstract class AbstractAuthMapDao implements AuthMapDao {
	@Autowired
	SessionFactory sessionFactory;

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
		} else if (search.getSearchAuthSeq() != 0) {
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
