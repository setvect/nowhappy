package com.setvect.nowhappy.note.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.note.service.NoteCategorySearch;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;

/**
 * 복슬노트 카테고리
 */
public class NoteCategoryDaoImpl implements NoteCategoryDaoCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<NoteCategoryVo> getNoteCategoryPagingList(NoteCategorySearch pageCondition) {
		String q = "select count(*) from NoteCategoryVo " + getCategoryWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = " from NoteCategoryVo " + getCategoryWhereClause(pageCondition) + " order by name ";
		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<NoteCategoryVo> resultList = query.getResultList();

		GenericPage<NoteCategoryVo> resultPage = new GenericPage<NoteCategoryVo>(resultList, pageCondition.getStartCursor(),
				totalCount, pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return select where 절
	 */
	private String getCategoryWhereClause(NoteCategorySearch pageCondition) {
		String where = " where deleteF = 'N' ";
		return where;
	}

}
