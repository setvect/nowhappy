package com.setvect.nowhappy.board.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant.FileUpload;
import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.attach.service.AttachFileService;
import com.setvect.nowhappy.attach.vo.AttachFileVo;
import com.setvect.nowhappy.board.service.BoardArticleSearch;
import com.setvect.nowhappy.board.service.BoardService;
import com.setvect.nowhappy.board.vo.BoardArticleVo;

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
		return "/app/board/board_page";
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
		GenericPage<BoardArticleVo> page = boardService.getArticlePagingList(pageCondition);
		return page;
	}

	/**
	 * 게시물 등록
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws IOException
	 */
	@RequestMapping("/app/board/add.do")
	@ResponseBody
	public boolean add(@ModelAttribute BoardArticleVo article, HttpServletRequest request) {
		String title = request.getParameter("title");
		System.out.println("title: " + title);

		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		boardService.insertArticle(article);
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
	private void saveAttachFile(HttpServletRequest request, BoardArticleVo article) throws IOException,
			FileNotFoundException {
		String destDir = request.getSession().getServletContext().getRealPath(FileUpload.ATTACH_PATH);

		File saveDir = new File(destDir, article.getBoardCode());
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		MultipartFile[] attachFiles = article.getAttachFile();
		if (attachFiles == null) {
			return;
		}
		for (MultipartFile file : attachFiles) {
			if (StringUtilAd.isEmpty(file.getOriginalFilename())) {
				continue;
			}
			String fileName = "upload." + FilenameUtils.getExtension(file.getOriginalFilename());

			File destination = File.createTempFile("file", fileName, saveDir);
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destination));
			AttachFileVo attach = new AttachFileVo();
			attach.setModuleName(AttachFileModule.BOARD);
			attach.setModuleId(String.valueOf(article.getArticleSeq()));
			attach.setOriginalName(file.getOriginalFilename());
			attach.setSaveName(destination.getName());
			attach.setSize((int) file.getSize());
			attachFileService.createAttachFile(attach);
		}
	}

	/**
	 * 게시물 수정
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws IOException
	 */
	@RequestMapping("/app/board/update.do")
	@ResponseBody
	public boolean update(@ModelAttribute BoardArticleVo article, HttpServletRequest request) {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		boardService.updateArticle(article);
		return true;
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
}
