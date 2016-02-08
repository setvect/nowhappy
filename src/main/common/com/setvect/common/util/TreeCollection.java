package com.setvect.common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;

/**
 * 카테고리, 메뉴, 권한 정보를 캐싱한다.
 */
public class TreeCollection<OBJ extends TreeItem<?>> {
	/** 카테고리 정보 */
	private Hashtable<Object, OBJ> category = new Hashtable<Object, OBJ>();

	/** 최상단 카테고리 루트 아이디 */
	private final Object categoryRootID;

	/** toArray() 메소드를 사용하기 위해서 배열 형태 인스턴스 */
	private final OBJ[] _obj_empty_array;

	/**
	 * @param itmes
	 *            기초 데이터
	 * @param rootID
	 *            카테고리 root 아이디
	 */
	@SuppressWarnings("unchecked")
	public TreeCollection(List<OBJ> items, Object rootID) {
		if (items.size() == 0) {
			throw new RuntimeException("items size zero!!.");
		}
		for (OBJ i : items) {
			category.put(i.getId(), i);
		}
		_obj_empty_array = (OBJ[]) Array.newInstance(items.get(0).getClass(), 0);
		categoryRootID = rootID;
	}

	/**
	 * @return 루트 카테고리 아이디
	 */
	public Object getRootID() {
		return categoryRootID;
	}

	/**
	 * 기존에 저장되어 있는 객체를 지우고, 데이터 객체 정보를 변경
	 * 
	 * @param items
	 */
	public void setTreeItem(OBJ[] items) {
		category.clear();
		for (OBJ i : items) {
			category.put(i.getId(), i);
		}
	}

	/**
	 * @param categoryID
	 *            카테고리 아이디
	 * @return 카테고리 정보
	 */
	public OBJ get(Object categoryID) {
		return category.get(categoryID);
	}

	/**
	 * 
	 * @param baseRootCategoryID
	 *            트리 시작 지점
	 * @return 자식 객체 정보, 트리형태 아님
	 */
	public List<OBJ> getChild(Object baseRootCategoryID) {
		Enumeration<OBJ> e = category.elements();
		TreeSet<OBJ> n = new TreeSet<OBJ>();
		OBJ mc = null;
		while (e.hasMoreElements()) {
			mc = (OBJ) e.nextElement();
			if (mc.getParentId().equals(baseRootCategoryID) && !mc.getId().equals(categoryRootID)) {
				n.add(mc);
			}
		}
		return new ArrayList<>(n);
	}

	/**
	 * 트리 표시 순서대로 카테고리를 정렬 해서 가져옴
	 * 
	 * @return 카테고리 전체 구조(루트 카테고리 제외 )
	 */
	public List<OBJ> getTree() {
		return getTree(categoryRootID);
	}

	/**
	 * 트리 표시 순서대로 카테고리를 정렬 해서 가져옴
	 * 
	 * @param rootCategory
	 *            시작되는 카테고리
	 * @return 정렬된 카테고리 배열
	 */
	public List<OBJ> getTree(Object rootCategory) {
		return getTree(rootCategory, false);
	}

	/**
	 * 트리 표시 순서대로 카테고리를 정렬 해서 가져옴
	 * 
	 * @param rootCategory
	 *            시작되는 카테고리
	 * @param rootSave
	 *            루트 카테고리 포함 여부
	 * @return 정렬된 카테고리 배열
	 */
	public List<OBJ> getTree(Object rootCategory, boolean rootSave) {
		return getTree(rootCategory, 0, true, rootSave, null);
	}

	/**
	 * 트리 표시 순서대로 카테고리를 정렬 해서 가져옴
	 * 
	 * @param rootCategory
	 *            시작되는 카테고리
	 * @param rootSave
	 *            루트 카테고리 포함 여부
	 * @param excludeCategory
	 *            제외 할 메뉴(하위 그룹까지 다 제외)
	 * @return 정렬된 카테고리 배열
	 */
	public List<OBJ> getTree(Object rootCategory, boolean rootSave, Object excludeCategory) {
		return getTree(rootCategory, 0, true, rootSave, excludeCategory);
	}

	/**
	 * 루트 카테고리리 부터 현재 카테고리까지 경로를 구한다.
	 * 
	 * @param formCategoryID
	 *            루트로 부터 formCategoryID카테고리 까지 경로를 구함
	 * @return 카테고리 경로
	 */
	public List<OBJ> getPath(Object formCategoryID) {
		ArrayList<OBJ> saveCategory = new ArrayList<OBJ>();
		path(formCategoryID, saveCategory);

		// 구하고자 하는 카테고리 -> Root 카테고리 순서로 되어 있다.
		OBJ[] descCategory = (OBJ[]) saveCategory.toArray(_obj_empty_array);

		// 정렬 순서를 변경
		// Root 카테고리 ->구하고자 하는 카테고리 순서로 변경
		// 이렇게 해야지 프로그램에서 가져다 쓰기가 조금 이라도 편한다.
		@SuppressWarnings("unchecked")
		OBJ[] ascCategory = (OBJ[]) Array.newInstance(saveCategory.get(0).getClass(), descCategory.length);

		int c = 0;
		for (int i = descCategory.length - 1; i >= 0; i--) {
			ascCategory[c++] = descCategory[i];
		}
		return new ArrayList<>(Arrays.asList(ascCategory));
	}

	/**
	 * @param currentCategoryID
	 *            현재 카테고리 아이디
	 * @param saveCategory
	 *            재귀 호출을 통해 카테고리 정보를 담는 변수
	 */
	private void path(Object currentCategoryID, ArrayList<OBJ> saveCategory) {
		OBJ mc = get(currentCategoryID);
		saveCategory.add(mc);

		// 루트 카테고리가 아니면 하위 카테고리를 더 찾음
		if (!currentCategoryID.equals(categoryRootID)) {
			path(mc.getParentId(), saveCategory);
		}
	}

	/**
	 * @param rootCategory
	 *            시작 카테고리
	 * @param depth
	 *            현재 진행 카테고리 깊이
	 * @param modifyLevel
	 *            ture 깊이 정보 변경, false 변경하지 않음
	 * @param rootSave
	 *            루트 카테고리 포함 여부
	 * @param excludeCategory
	 *            제외 할 메뉴(하위 그룹까지 다 제외)
	 * @return 정렬된 카테고리 배열
	 */
	private List<OBJ> getTree(Object rootCategory, int depth, boolean modifyLevel, boolean rootSave,
			Object excludeCategory) {
		List<OBJ> saveCategory = new ArrayList<OBJ>();

		Enumeration<OBJ> e = category.elements();
		TreeSet<OBJ> map = new TreeSet<OBJ>();

		while (e.hasMoreElements()) {
			map.add(e.nextElement());
		}

		// 루트 카테고리 지정
		OBJ root = get(rootCategory);
		if (modifyLevel) {
			root.setDepth(depth);
		}
		OBJ[] cat = (OBJ[]) map.toArray(_obj_empty_array);
		if (rootSave) {
			saveCategory.add(root);
		}

		recurrence(cat, rootCategory, saveCategory, depth + 1, modifyLevel, excludeCategory);
		return saveCategory;
	}

	/**
	 * 재귀 호출을 통해 카테고리 아이디 저장
	 * 
	 * @param cat
	 *            카테고리 정보값
	 * 
	 * @param currentCategoryID
	 *            현재 카테고리 아이디
	 * @param saveCategory
	 *            재귀 호출을 통해 카테고리 정보를 담는 변수
	 * @param depth
	 *            현재 진행 카테고리 깊이
	 * @param modifyLevel
	 *            ture 깊이 정보 변경, false 변경하지 않음
	 * @param excludeCategory
	 *            제외 할 메뉴(하위 그룹까지 다 제외)
	 */
	private void recurrence(OBJ[] cat, Object currentCategoryID, List<OBJ> saveCategory, int depth,
			boolean modifyLevel, Object excludeCategory) {
		for (int i = 0; i < cat.length; i++) {
			OBJ mc = cat[i];
			if (mc.getParentId().equals(currentCategoryID) && !mc.getId().equals(categoryRootID)) {
				if (modifyLevel) {
					mc.setDepth(depth);
				}
				// 제외 카테고리 검사
				if (!mc.getId().equals(excludeCategory)) {
					saveCategory.add(mc);
					recurrence(cat, mc.getId(), saveCategory, depth + 1, modifyLevel, excludeCategory);
				}
			}
		}
	}

}
