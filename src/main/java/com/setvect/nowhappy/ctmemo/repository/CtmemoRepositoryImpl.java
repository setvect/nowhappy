package com.setvect.nowhappy.ctmemo.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * DBMS에 맞추어 상속받아 사용.
 */
public class CtmemoRepositoryImpl implements CtmemoRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public int getMaxZindex() {
		String q = " select COALESCE(max(zIndex) + 1, 1) from CtmemoVo where deleteF = 'N'";
		Query query = em.createQuery(q);
		return (Integer) query.getSingleResult();
	}

}
