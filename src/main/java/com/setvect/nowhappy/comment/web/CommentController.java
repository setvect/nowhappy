package com.setvect.nowhappy.comment.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.comment.service.CommentModule;
import com.setvect.nowhappy.comment.service.CommentSearch;
import com.setvect.nowhappy.comment.service.CommentService;
import com.setvect.nowhappy.comment.vo.Comment;

/**
 * 이메일 주소 알아 내기
 * 
 * @version $Id$
 */
@Controller
public class CommentController {

	public static final String ATTR_LIST = "LIST";
	public static final String ATTR_EXIST = "EXIST";

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
	@RequestMapping("/comment.do")
	public String process(HttpServletRequest request, HttpServletResponse response) throws IOException {

		CommentModule moduleName = CommentModule.valueOf(request.getParameter("moduleName"));
		String moduleItemId = request.getParameter("moduleId");
		int currentPage = Integer.parseInt(StringUtilAd.null2str(request.getParameter("currentPage"), "1"));
		CommentSearch pageCondition = new CommentSearch(currentPage, moduleName, moduleItemId);
		GenericPage<Comment> page = commentService.getCommentPagingList(pageCondition);
		List<Comment> list = page.getList();
		request.setAttribute(ATTR_LIST, list);

		// 맨 끝까지 로드가 되었는지 확인
		int loadCount = currentPage * pageCondition.getPagePerItemCount();
		boolean exist = loadCount < page.getTotalCount();
		request.setAttribute(ATTR_EXIST, exist);

		// TODO 페이지 변경 로직 추가(메인, 게시판)
		return "/app/comment/list";
	}
}