package com.setvect.nowhappy.board.web;

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
import com.setvect.nowhappy.user.vo.UserVo;
import com.setvect.nowhappy.util.StringEncrypt;

/**
 * 게시물
 */
@Controller
public class BoardController {
	/** 한 페이지당 표시 항목 갯수 */
	private static final int PAGE_PER_ITEM = 5;

	@Autowired
	private BoardService boardService;

	@Autowired
	private AttachFileService attachFileService;

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board/page.do")
	public String page(HttpServletRequest request, HttpServletResponse response) {
		settingListPage(request);

		return "/app/board/board_page";
	}

	/**
	 * 표시할 게시물 목록 페이지 설정
	 * 
	 * @param request
	 */
	private void settingListPage(HttpServletRequest request) {
		String typeValue = request.getParameter("type");
		String code = request.getParameter("boardCode");
		BoardListPage listPage = getListType(typeValue, code);
		request.setAttribute(WebAttributeKey.BOARD_LIST_TYPE, listPage);
	}

	private BoardListPage getListType(String typeValue, String code) {
		BoardListPage listPage = null;
		try {
			listPage = BoardListPage.valueOf(typeValue);
		} catch (Exception e) {
			boolean listConfigView = ApplicationConstant.BoardConfig.LIST_CONTENT_VIEW.contains(code);
			listPage = listConfigView ? BoardListPage.CONTENT : BoardListPage.NORMAL;
		}
		return listPage;
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		String typeValue = request.getParameter("type");
		BoardListPage listPage = getListType(typeValue, null);
		return listPage.getListUrl();
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board/write.do")
	public String write(HttpServletRequest request, HttpServletResponse response) {
		return "/app/board/views/board_write";
	}

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board/read.do")
	public String read(HttpServletRequest request, HttpServletResponse response) {
		return "/app/board/views/board_read";
	}

	/**
	 * 게시물 목록
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board/list.json")
	@ResponseBody
	public GenericPage<BoardArticleVo> list(HttpServletRequest request) {
		String pg = StringUtilAd.null2str(request.getParameter("pageNumber"), "1");
		int pageNumber = Integer.parseInt(pg);
		String t = request.getParameter("pagePerItem");
		int pagePerItem = StringUtils.isEmpty(t) ? PAGE_PER_ITEM : Integer.parseInt(t);
		int startCursor = (pageNumber - 1) * pagePerItem;

		BoardArticleSearch pageCondition = new BoardArticleSearch(startCursor, pagePerItem);
		String code = request.getParameter("boardCode");
		pageCondition.setSearchCode(code);

		setSearchCondition(request, pageCondition);

		GenericPage<BoardArticleVo> page = boardService.getArticlePagingList(pageCondition);
		return page;
	}

	/**
	 * 검색 조건 적용
	 * 
	 * @param request
	 * @param pageCondition
	 */
	private void setSearchCondition(HttpServletRequest request, BoardArticleSearch pageCondition) {
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
	 * 게시물 본문
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board/read.json")
	@ResponseBody
	public BoardArticleVo read(HttpServletRequest request) {
		String articleSeqStr = request.getParameter("articleSeq");
		int articleSeq = Integer.parseInt(articleSeqStr);
		BoardArticleVo read = boardService.getArticle(articleSeq);
		boardService.updateArticleIncrementHit(articleSeq);
		return read;
	}

	/**
	 * 게시물 등록
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping("/app/board/add.do")
	@ResponseBody
	public boolean add(@ModelAttribute BoardArticleVo article, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		processEncrypt(request, article);

		UserVo user = ApplicationUtil.getLoginSession(request);
		article.setUserId(user.getUserId());
		article.setEmail(user.getEmail());
		article.setName(user.getName());

		article.setRegDate(new Date());
		article.setIp(request.getRemoteAddr());

		boardService.insertArticle(article);
		saveAttachFile(request, article);
		return true;
	}

	/**
	 * 게시물 수정
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping("/app/board/update.do")
	@ResponseBody
	public boolean update(@ModelAttribute BoardArticleVo param, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		processEncrypt(request, param);
		BoardArticleVo article = boardService.getArticle(param.getArticleSeq());
		article.setTitle(param.getTitle());
		article.setContent(param.getContent());
		article.setAttachFile(param.getAttachFile());

		boardService.updateArticle(article);
		saveAttachFile(request, article);
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
		for (String s : deleteSeq) {
			int seq = Integer.parseInt(s);

			AttachFileVo file = attachFileService.getAttachFile(seq);
			if (file != null) {
				String destDir = request.getSession().getServletContext().getRealPath("/");
				File osFile = new File(destDir, file.getSavePath());
				osFile.delete();
			}
			attachFileService.deleteFile(seq);
		}
	}

	/**
	 * 게시물 삭제
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board/delete.do")
	@ResponseBody
	public boolean delete(@ModelAttribute BoardArticleVo article, HttpServletRequest request) {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		boardService.deleteArticle(article.getArticleSeq());
		return true;
	}

	/**
	 * 게시물 접근 권한 설정<br>
	 * 쓰기, 수정/삭제를 정함
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board/loadAuth.json")
	@ResponseBody
	public Map<String, Object> loadAuth(@ModelAttribute BoardArticleVo param, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// TODO 향후 수정
		result.put("write", true);
		result.put("edit", true);
		return result;
	}

	/**
	 * 암호화 글 처리
	 * 
	 * @param request
	 * @param article
	 */
	private void processEncrypt(HttpServletRequest request, BoardArticleVo article) {
		String encode = request.getParameter("encode");

		// 암호화 글
		if (!StringUtilAd.isEmpty(encode)) {
			article.setContent(StringEncrypt.encodeJ(article.getContent(), encode));
			article.setEncodeF(true);
		}
		else {
			article.setEncodeF(false);
		}
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
	private void saveAttachFile(HttpServletRequest request, BoardArticleVo article) throws IOException,
			FileNotFoundException {
		String destDir = request.getSession().getServletContext().getRealPath("/");
		File destPath = new File(destDir);
		MultipartFile[] attachFiles = article.getAttachFile();
		UserVo user = ApplicationUtil.getLoginSession(request);
		attachFileService.process(destPath, attachFiles, AttachFileModule.BOARD, article.getArticleSeq(),
				user.getUserId());
	}
}
