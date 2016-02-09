package com.setvect.nowhappy.note.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.common.util.TreeCollection;
import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.attach.service.AttachFileService;
import com.setvect.nowhappy.note.service.NoteCategorySearch;
import com.setvect.nowhappy.note.service.NoteSearch;
import com.setvect.nowhappy.note.service.NoteSearch.NoteSort;
import com.setvect.nowhappy.note.service.NoteService;
import com.setvect.nowhappy.note.vo.NoteCategoryVo;
import com.setvect.nowhappy.note.vo.NoteVo;
import com.setvect.nowhappy.user.vo.UserVo;
import com.setvect.nowhappy.util.TimestampDateFormat;

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
	@RequestMapping("/note/page.do")
	public String page(HttpServletRequest request, HttpServletResponse response) {
		if (!ApplicationUtil.isAdmin(request)) {
			return null;
		}

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
		return "/app/note/views/note_list";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/write.do")
	public String write(HttpServletRequest request, HttpServletResponse response) {
		return "/app/note/views/note_write";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/read.do")
	public String read(HttpServletRequest request, HttpServletResponse response) {
		return "/app/note/views/note_read";
	}

	// ----------------- 카테고리
	/**
	 * 카테고리
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/readCategory.json.do")
	@ResponseBody
	public NoteCategoryVo readCategory(HttpServletRequest request) {
		String categorySeqStr = request.getParameter("categorySeq");
		int categorySeq = Integer.parseInt(categorySeqStr);
		NoteCategoryVo category = noteService.getNoteCategory(categorySeq);
		return category;
	}

	/**
	 * 카테고리 목록
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/note/listCategory.json.do")
	@ResponseBody
	public List<NoteCategoryVo> listCategory(HttpServletRequest request) {
		// 검색 조건
		NoteCategorySearch pageCondition = new NoteCategorySearch(0, Integer.MAX_VALUE);
		GenericPage<NoteCategoryVo> page = noteService.getNoteCategoryPagingList(pageCondition);
		TreeCollection<NoteCategoryVo> categoryTree = new TreeCollection<NoteCategoryVo>(page.getList(),
				NoteCategoryVo.ROOT_CATEGORY_ID);

		List<NoteCategoryVo> categoryList = categoryTree.getChild(NoteCategoryVo.ROOT_CATEGORY_ID);
		for (NoteCategoryVo c : categoryList) {
			c.setDepth(1);
			List<NoteCategoryVo> child = categoryTree.getChild(c.getCategorySeq());
			for (NoteCategoryVo cc : child) {
				cc.setDepth(2);
			}
			c.setChildren(child);
		}
		return categoryList;
	}

	/**
	 * 카테고리 등록
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 */
	@RequestMapping("/app/note/addCategory.do")
	@ResponseBody
	public boolean addCategory(@ModelAttribute NoteCategoryVo category, HttpServletRequest request) {
		category.setRegDate(new Date());

		noteService.insertNoteCategory(category);
		return true;
	}

	/**
	 * 카테고리 수정
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 */
	@RequestMapping("/app/note/updateCategory.do")
	@ResponseBody
	public boolean updateCategory(@ModelAttribute NoteCategoryVo category, HttpServletRequest request) {
		NoteCategoryVo categorySave = noteService.getNoteCategory(category.getCategorySeq());
		categorySave.setName(category.getName());

		noteService.updateNoteCategory(categorySave);
		return true;
	}

	/**
	 * 카테고리 삭제<br>
	 * 논리적 삭제
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/note/deleteCategory.do")
	@ResponseBody
	public boolean deleteCategory(@ModelAttribute NoteCategoryVo NoteCategoryVo, HttpServletRequest request) {
		NoteCategoryVo categorySave = noteService.getNoteCategory(NoteCategoryVo.getCategorySeq());
		categorySave.setDeleteF(true);
		noteService.updateNoteCategory(categorySave);
		return true;
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
	@RequestMapping("/app/note/listNote.json.do")
	@ResponseBody
	public GenericPage<NoteVo> listNote(HttpServletRequest request) {
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

		setSearchCondition(request, pageCondition);

		// 정렬
		NoteSort sort = null;
		try {
			NoteSort.valueOf(request.getParameter("sort"));
		} catch (Exception e) {
			sort = NoteSort.UPD;
		}
		pageCondition.setSort(sort);

		GenericPage<NoteVo> page = noteService.getNotePagingList(pageCondition);
		return page;
	}

	/**
	 * 검색 조건 적용
	 * 
	 * @param request
	 * @param pageCondition
	 */
	private void setSearchCondition(HttpServletRequest request, NoteSearch pageCondition) {
		String option = request.getParameter("searchOption");
		String word = request.getParameter("searchWord");

		if (StringUtils.isNotEmpty(option)) {
			if (option.equals("title")) {
				pageCondition.setSearchTitle(word);
			}
			else if (option.equals("content")) {
				pageCondition.setSearchContent(word);
			}
		}
	}

	/**
	 * 노트
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/readNote.json.do")
	@ResponseBody
	public NoteVo readNote(HttpServletRequest request) {
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
	@RequestMapping("/app/note/addNote.do")
	@ResponseBody
	public boolean addNote(@ModelAttribute NoteVo note, HttpServletRequest request)
			throws FileNotFoundException, IOException {
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
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/app/note/updateNote.do")
	@ResponseBody
	public boolean updateNote(@ModelAttribute NoteVo param, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		NoteVo note = noteService.getNote(param.getNoteSeq());
		note.setTitle(param.getTitle());
		note.setContent(param.getContent());
		note.setAttachFile(param.getAttachFile());
		note.setUptDate(new Date());

		noteService.updateNote(note);
		saveAttachFile(request, note);
		deleteFile(request);

		return true;
	}

	/**
	 * 노트 카테고리 수정.
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/note/updateNoteCategory.do")
	@ResponseBody
	public boolean updateNoteCategory(@ModelAttribute NoteVo param) {
		NoteVo note = noteService.getNote(param.getNoteSeq());
		note.setCategorySeq(param.getCategorySeq());
		noteService.updateNote(note);
		return true;
	}

	/**
	 * 노트 삭제<br>
	 * 논리적 삭제
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/note/deleteNote.do")
	@ResponseBody
	public boolean deleteNote(@ModelAttribute NoteVo note, HttpServletRequest request) {
		NoteVo noteSave = noteService.getNote(note.getNoteSeq());
		noteSave.setDeleteF(true);
		noteService.updateNote(noteSave);
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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new TimestampDateFormat(), true));
	}
}
