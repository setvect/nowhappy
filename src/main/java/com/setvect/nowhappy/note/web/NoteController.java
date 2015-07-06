package com.setvect.nowhappy.note.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.attach.service.AttachFileService;
import com.setvect.nowhappy.attach.vo.AttachFileVo;
import com.setvect.nowhappy.board.service.BoardArticleSearch;
import com.setvect.nowhappy.board.service.BoardService;
import com.setvect.nowhappy.board.vo.BoardArticleVo;
import com.setvect.nowhappy.note.service.NoteCategorySearch;
import com.setvect.nowhappy.note.service.NoteSearch;
import com.setvect.nowhappy.note.service.NoteService;
import com.setvect.nowhappy.note.service.NoteSearch.NoteSort;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;
import com.setvect.nowhappy.note.vo.NoteVo;
import com.setvect.nowhappy.user.vo.UserVo;
import com.setvect.nowhappy.util.StringEncrypt;

/**
 * 복슬 노트
 */
@Controller
public class NoteController {
	/** 한 페이지당 표시 항목 갯수 */
	private static final int PAGE_PER_ITEM = 10;

	@Autowired
	private NoteService noteService;

	@Autowired
	private AttachFileService attachFileService;

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/page.do")
	public String page(HttpServletRequest request, HttpServletResponse response) {
		return "/app/note/note_page";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return "/app/note/views/list";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/write.do")
	public String write(HttpServletRequest request, HttpServletResponse response) {
		return "/app/note/views/write";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/read.do")
	public String read(HttpServletRequest request, HttpServletResponse response) {
		return "/app/note/views/read";
	}

	// ----------------- 카테고리
	/**
	 * 카테고리 목록
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/list_category.json.do")
	@ResponseBody
	public GenericPage<NoteCategoryVo> listCategory(HttpServletRequest request) {
		// 검색 조건
		NoteCategorySearch pageCondition = new NoteCategorySearch(0, Integer.MAX_VALUE);

		GenericPage<NoteCategoryVo> page = noteService.getNoteCategoryPagingList(pageCondition);
		return page;
	}

	// ----------------- 노트

	/**
	 * 노트 목록
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/list.json.do")
	@ResponseBody
	public GenericPage<NoteVo> list(HttpServletRequest request) {
		String pg = StringUtilAd.null2str(request.getParameter("pageNumber"), "1");
		int pageNumber = Integer.parseInt(pg);
		String t = request.getParameter("pagePerItem");
		int pagePerItem = StringUtils.isEmpty(t) ? PAGE_PER_ITEM : Integer.parseInt(t);
		int startCursor = (pageNumber - 1) * pagePerItem;

		// 검색 조건
		NoteSearch pageCondition = new NoteSearch(startCursor, pagePerItem);
		String seqStr = StringUtilAd.null2str(request.getParameter("categorySeq"), "0");
		int categorySeq = Integer.parseInt(seqStr);
		pageCondition.setSearchCategorySeq(categorySeq);

		pageCondition.setSearchTitle(request.getParameter("searchTitle"));
		pageCondition.setSearchContent(request.getParameter("searchContent"));

		// 정렬
		NoteSort sort = null;
		try {
			NoteSort.valueOf(request.getParameter("sort"));
		} catch (Exception e) {
			sort = NoteSort.REG;
		}
		pageCondition.setSort(sort);

		GenericPage<NoteVo> page = noteService.getNotePagingList(pageCondition);
		return page;
	}

	/**
	 * 노트
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/read.json.do")
	@ResponseBody
	public NoteVo read(HttpServletRequest request) {
		String noteStr = request.getParameter("noteSeq");
		int noteSeq = Integer.parseInt(noteStr);
		NoteVo read = noteService.getNote(noteSeq);
		return read;
	}

	/**
	 * 노트 등록
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping("/app/note/add.do")
	@ResponseBody
	public boolean add(@ModelAttribute NoteVo note, HttpServletRequest request) throws FileNotFoundException,
			IOException {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		note.setRegDate(new Date());
		note.setUptDate(new Date());

		noteService.insertNote(note);
		saveAttachFile(request, note);
		return true;
	}

	/**
	 * 노트 수정
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping("/app/board/update.do")
	@ResponseBody
	public boolean update(@ModelAttribute NoteVo param, HttpServletRequest request) throws FileNotFoundException,
			IOException {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		NoteVo note = noteService.getNote(param.getNoteSeq());
		note.setTitle(param.getTitle());
		note.setContent(param.getContent());
		note.setAttachFile(param.getAttachFile());

		noteService.updateNote(note);
		saveAttachFile(request, note);
		deleteFile(request);

		return true;
	}

	/**
	 * 삭제 선택 파일 삭제 처리
	 * 
	 * @param request
	 */
	private void deleteFile(HttpServletRequest request) {
		String[] deleteSeq = request.getParameterValues("deleteattachFileSeq");
		if (deleteSeq == null) {
			return;
		}
		String destDir = request.getSession().getServletContext().getRealPath("/");
		attachFileService.deleteFile(deleteSeq, destDir);
	}

	/**
	 * 노트 삭제
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/delete.do")
	@ResponseBody
	public boolean delete(@ModelAttribute NoteVo note, HttpServletRequest request) {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		noteService.deleteNote(note.getNoteSeq());
		return true;
	}

	/**
	 * 첨부파일 저장
	 * 
	 * @param request
	 * @param article
	 *            관계 글
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void saveAttachFile(HttpServletRequest request, NoteVo article) throws IOException, FileNotFoundException {
		String destDir = request.getSession().getServletContext().getRealPath("/");
		File destPath = new File(destDir);
		MultipartFile[] attachFiles = article.getAttachFile();
		UserVo user = ApplicationUtil.getLoginSession(request);
		attachFileService.process(destPath, attachFiles, AttachFileModule.NOTE, article.getNoteSeq(), user.getUserId());
	}
}
