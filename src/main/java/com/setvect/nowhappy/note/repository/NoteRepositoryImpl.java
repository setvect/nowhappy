package com.setvect.nowhappy.note.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.note.service.NoteSearch;
import com.setvect.nowhappy.note.service.NoteSearch.NoteSort;
import com.setvect.nowhappy.note.vo.NoteVo;

/**
 * 복슬 노트
 */
public class NoteRepositoryImpl implements NoteRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition) {
		String q = "select count(*) from NoteVo n " + getNoteWhereClause(pageCondition);
		javax.persistence.Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select n from NoteVo n " + getNoteWhereClause(pageCondition) + getOrder(pageCondition);
		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<NoteVo> resultList = query.getResultList();

		GenericPage<NoteVo> resultPage = new GenericPage<NoteVo>(resultList, pageCondition.getStartCursor(), totalCount,
				pageCondition.getReturnCount());
		return resultPage;
	}

	private String getOrder(NoteSearch pageCondition) {
		if (pageCondition.getSort() == NoteSort.UPD) {
			return " order by n.uptDate desc";
		} else {
			return " order by n.regDate asc";
		}
	}

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getNoteWhereClause(NoteSearch search) {
		String where = " where ";

		// 삭제 게시물 보여 주지 않음
		where += " n.deleteF = 'N' ";
		where += " and n.categorySeq in (select categorySeq from NoteCategoryVo c where c.deleteF = 'N') ";

		if (search.getSearchCategorySeq() != 0) {
			where += " and n.categorySeq = " + search.getSearchCategorySeq();
		}

		// 두개 이상 동시에 검색 조건에 포함 될 수 없음
		if (!StringUtilAd.isEmpty(search.getSearchTitle())) {
			where += " and upper(n.title) like " + StringUtilAd.getSqlStringLike(search.getSearchTitle()).toUpperCase();
		} else if (!StringUtilAd.isEmpty(search.getSearchContent())) {
			where += " and upper(n.content) like "
					+ StringUtilAd.getSqlStringLike(search.getSearchContent()).toUpperCase();
		}
		return where;
	}
}
