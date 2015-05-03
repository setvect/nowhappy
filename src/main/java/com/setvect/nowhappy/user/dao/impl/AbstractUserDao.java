package com.setvect.nowhappy.user.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.common.util.GenericPage;
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
}
