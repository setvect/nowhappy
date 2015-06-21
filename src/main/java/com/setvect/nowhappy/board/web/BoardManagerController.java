package com.setvect.nowhappy.board.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationUtil;
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
		String t = request.getParameter("pagePerItem");
		int pagePerItem = StringUtils.isEmpty(t) ? PAGE_PER_ITEM : Integer.parseInt(t);
		int startCursor = (pageNumber - 1) * pagePerItem;
		BoardManagerSearch pageCondition = new BoardManagerSearch(startCursor, pagePerItem);

		setSearchCondition(request, pageCondition);

		GenericPage<BoardVo> page = boardService.getBoardPagingList(pageCondition);
		return page;
	}

	/**
	 * 검색 조건 적용
	 * 
	 * @param request
	 * @param pageCondition
	 */
	private void setSearchCondition(HttpServletRequest request, BoardManagerSearch pageCondition) {
		String option = request.getParameter("searchOption");
		String word = request.getParameter("searchWord");

		if (StringUtils.isNotEmpty(option)) {
			if (option.equals("code")) {
				pageCondition.setSearchCode(word);
			}
			else if (option.equals("name")) {
				pageCondition.setSearchName(word);
			}
		}
	}

	/**
	 * 게시판 내용
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board_manager/read.json")
	@ResponseBody
	public BoardVo read(HttpServletRequest request) {
		String boardCode = request.getParameter("boardCode");
		BoardVo board = boardService.getBoard(boardCode);
		return board;
	}

	/**
	 * 게시판 생성
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws IOException
	 */
	@RequestMapping("/app/board_manager/add.do")
	@ResponseBody
	public boolean add(@ModelAttribute BoardVo param, HttpServletRequest request) {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		boardService.insertBoard(param);
		return true;
	}

	/**
	 * 게시판 수정
	 * 
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws IOException
	 */
	@RequestMapping("/app/board_manager/update.do")
	@ResponseBody
	public boolean update(@ModelAttribute BoardVo param, HttpServletRequest request) {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		boardService.updateBoard(param);
		return true;
	}

	/**
	 * 게시판 삭제
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/board_manager/delete.do")
	@ResponseBody
	public boolean delete(@ModelAttribute BoardVo param, HttpServletRequest request) {
		if (!ApplicationUtil.isAdmin(request)) {
			return false;
		}
		boardService.deleteBoard(param.getBoardCode());
		return true;
	}

}
