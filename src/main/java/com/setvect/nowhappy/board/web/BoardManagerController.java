package com.setvect.nowhappy.board.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.board.service.BoardManagerSearch;
import com.setvect.nowhappy.board.service.BoardService;
import com.setvect.nowhappy.board.vo.BoardVo;

/**
 * 게시판 관리
 */
@Controller
public class BoardManagerController {
	/** 한 페이지당 표시 항목 갯수 */
	private static final int PAGE_PER_ITEM = 5;

	@Autowired
	private BoardService boardService;

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board_manager/page.do")
	public String page(HttpServletRequest request, HttpServletResponse response) {
		return "/app/board/board_manager_page";
	}

	/**
	 * 게시판 목록
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board_manager/list.json")
	@ResponseBody
	public GenericPage<BoardVo> list(HttpServletRequest request) {
		String pg = StringUtilAd.null2str(request.getParameter("pageNumber"), "1");
		int pageNumber = Integer.parseInt(pg);
		int startCursor = (pageNumber - 1) * PAGE_PER_ITEM;
		BoardManagerSearch pageCondition = new BoardManagerSearch(startCursor, PAGE_PER_ITEM);
		GenericPage<BoardVo> page = boardService.getBoardPagingList(pageCondition);
		return page;
	}
}
