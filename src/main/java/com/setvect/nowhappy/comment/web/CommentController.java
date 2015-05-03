package com.setvect.nowhappy.comment.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.comment.service.CommentModule;
import com.setvect.nowhappy.comment.service.CommentSearch;
import com.setvect.nowhappy.comment.service.CommentService;
import com.setvect.nowhappy.comment.vo.Comment;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 이메일 주소 알아 내기
 * 
 * @version $Id$
 */
@Controller
public class CommentController {
	/** 한 페이지당 표시 항목 갯수 */
	private static final int PAGE_PER_ITEM = 5;
	public static final String ATTR_MODULE_NAME = "MODULE_NAME";

	/**
	 * 서브 명령어 정의
	 */
	public static enum Mode {
		LIST_FORM,
	}

	@Autowired
	private CommentService commentService;

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/comment/page.do")
	public String page(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mn = request.getParameter("moduleName");
		CommentModule moduleName = CommentModule.valueOf(mn);
		request.setAttribute(ATTR_MODULE_NAME, moduleName);
		// TODO 페이지 변경 로직 추가(메인, 게시판)
		return "/app/comment/comment_page";
	}

	/**
	 * 코멘트 조회 목록
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/comment/list.json")
	@ResponseBody
	public GenericPage<Comment> list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mn = request.getParameter("moduleName");
		CommentModule moduleName = CommentModule.valueOf(mn);
		String cursor = StringUtilAd.null2str(request.getParameter("startCursor"), "0");
		int startCursor = Integer.parseInt(cursor);

		CommentSearch pageCondition = new CommentSearch(moduleName, startCursor, PAGE_PER_ITEM);
		GenericPage<Comment> page = commentService.getCommentPagingList(pageCondition);

		return page;
	}

	/**
	 * 코멘트 삭제
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/comment/delete.do")
	@ResponseBody
	public boolean delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int commentSeq = Integer.parseInt(request.getParameter("commentSeq"));
		Comment comment = commentService.getComment(commentSeq);
		if (comment == null) {
			return false;
		}
		UserVo user = ApplicationUtil.getLoginSession(request);
		if (user == null) {
			return false;
		}
		if (!comment.getUserId().equals(user.getUserId())) {
			return false;
		}

		// 자기가 쓴 comment
		commentService.deleteComment(commentSeq);
		return true;
	}

}