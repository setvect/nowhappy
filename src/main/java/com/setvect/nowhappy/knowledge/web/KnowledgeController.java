package com.setvect.nowhappy.knowledge.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.code.service.CodeConstant;
import com.setvect.nowhappy.code.service.CodeService;
import com.setvect.nowhappy.code.vo.CodeVo;
import com.setvect.nowhappy.knowledge.service.KnowledgeSearch;
import com.setvect.nowhappy.knowledge.service.KnowledgeService;
import com.setvect.nowhappy.knowledge.vo.KnowledgeVo;
import com.setvect.nowhappy.util.TimestampDateFormat;

@Controller
public class KnowledgeController {
	/** 한 페이지당 표시 항목 갯수 */
	private static final int PAGE_PER_ITEM = 10;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private CodeService codeService;

	private Map<String, CodeVo> classifyMap;

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/knowledge/page.do")
	public String page(HttpServletRequest request, HttpServletResponse response) {
		if (!ApplicationUtil.isAdmin(request)) {
			return null;
		}

		return "/app/knowledge/knowledge_page";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/knowledge/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return "/app/knowledge/views/knowledge_list";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/knowledge/write.do")
	public String write(HttpServletRequest request, HttpServletResponse response) {
		return "/app/knowledge/views/knowledge_write";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/knowledge/read.do")
	public String read(HttpServletRequest request, HttpServletResponse response) {
		return "/app/knowledge/views/knowledge_read";
	}

	/**
	 * 지식게시물 목록
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/knowledge/listKnowledge.json.do")
	@ResponseBody
	public GenericPage<KnowledgeVo> listKnowledge(HttpServletRequest request) {
		String pg = StringUtilAd.null2str(request.getParameter("pageNumber"), "1");
		int pageNumber = Integer.parseInt(pg);
		String t = request.getParameter("pagePerItem");
		int pagePerItem = StringUtils.isEmpty(t) ? PAGE_PER_ITEM : Integer.parseInt(t);
		int startCursor = (pageNumber - 1) * pagePerItem;

		KnowledgeSearch pageCondition = new KnowledgeSearch(startCursor, pagePerItem);
		setSearchCondition(request, pageCondition);

		GenericPage<KnowledgeVo> page = knowledgeService.getKnowledgePagingList(pageCondition);

		addCodeValue(page.getList());

		return page;
	}

	/**
	 * 코드값 추가
	 *
	 * @param list
	 */
	private void addCodeValue(List<KnowledgeVo> list) {
		for (KnowledgeVo v : list) {
			addCodeValue(v);
		}
	}

	private void addCodeValue(KnowledgeVo v) {
		if (classifyMap == null) {
			classifyMap = codeService.mapCode(CodeConstant.KNOW_TYPE);
		}

		CodeVo c = classifyMap.get(v.getClassifyC());
		v.setClassifyCode(c);
	}

	/**
	 * 지식 분류 정보
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/knowledge/listCategory.json.do")
	@ResponseBody
	public List<CodeVo> listCategory(HttpServletRequest request) {
		List<CodeVo> codeList = codeService.listCode(CodeConstant.KNOW_TYPE);
		return codeList;
	}

	/**
	 * 검색 조건 적용
	 *
	 * @param request
	 * @param pageCondition
	 */
	private void setSearchCondition(HttpServletRequest request, KnowledgeSearch pageCondition) {
		String classifyC = request.getParameter("searchClassifyC");
		String word = request.getParameter("searchWord");
		pageCondition.setSearchClassifyC(classifyC);
		pageCondition.setSearchWord(word);
	}

	/**
	 * 지식게시물
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/knowledge/readKnowledge.json.do")
	@ResponseBody
	public KnowledgeVo readKnowledge(HttpServletRequest request) {
		String knowledgeStr = request.getParameter("knowledgeSeq");
		int knowledgeSeq = Integer.parseInt(knowledgeStr);
		KnowledgeVo read = knowledgeService.getKnowledge(knowledgeSeq);
		return read;
	}

	/**
	 * 지식게시물 등록
	 *
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping("/app/knowledge/addKnowledge.do")
	@ResponseBody
	public boolean addKnowledge(@ModelAttribute KnowledgeVo knowledge, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		knowledge.setRegDate(new Date());

		knowledgeService.insertKnowledge(knowledge);
		return true;
	}

	/**
	 * 지식게시물 수정
	 *
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/app/knowledge/updateKnowledge.do")
	@ResponseBody
	public boolean updateKnowledge(@ModelAttribute KnowledgeVo param, HttpServletRequest request)
			throws FileNotFoundException, IOException {

		KnowledgeVo knowledge = knowledgeService.getKnowledge(param.getKnowledgeSeq());
		knowledge.setClassifyC(param.getClassifyC());
		knowledge.setProblem(param.getProblem());
		knowledge.setSolution(param.getSolution());
		knowledgeService.updateKnowledge(knowledge);

		return true;
	}

	/**
	 * 지식게시물 삭제<br>
	 * 논리적 삭제
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/knowledge/deleteKnowledge.do")
	@ResponseBody
	public boolean deleteKnowledge(@ModelAttribute KnowledgeVo knowledge, HttpServletRequest request) {
		KnowledgeVo knowledgeSave = knowledgeService.getKnowledge(knowledge.getKnowledgeSeq());
		knowledgeSave.setDeleteF(true);
		knowledgeService.updateKnowledge(knowledgeSave);
		return true;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new TimestampDateFormat(), true));
	}
}
