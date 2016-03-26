package com.setvect.nowhappy.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.user.vo.UserSearchCondition;
import com.setvect.nowhappy.user.vo.UserVo;
import com.setvect.nowhappy.util.StringEtcUtil;

/**
 * 회원관리 DAO
 */
public class UserDaoImpl implements UserDaoCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<UserVo> listUser(UserSearchCondition search) {

		String q = "select count(*) from UserVo " + getUserWhere(search);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select u from UserVo u " + getUserWhere(search) + " order by u.userId ";
		query = em.createQuery(q);
		// query.setFirstResult(search.getStartNumber());
		// query.setMaxResults(search.getPagePerItemCount());

		@SuppressWarnings("unchecked")
		List<UserVo> resultList = query.getResultList();

		GenericPage<UserVo> resultPage = new GenericPage<UserVo>(resultList, search.getStartCursor(), totalCount);
		return resultPage;
	}

	private String getUserWhere(UserSearchCondition search) {
		String where = "where u.deleteF = 'N' ";

		if (StringUtils.isNotEmpty(search.getSearchId())) {
			where += " and u.userId like " + StringEtcUtil.getSqlStringLike(search.getSearchId());
		}

		if (StringUtils.isNotEmpty(search.getSearchName())) {
			where += " and u.name like " + StringEtcUtil.getSqlStringLike(search.getSearchName());
		}
		return where;
	}

}
