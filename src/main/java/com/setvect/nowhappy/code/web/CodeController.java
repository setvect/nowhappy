package com.setvect.nowhappy.code.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.nowhappy.code.service.CodeService;
import com.setvect.nowhappy.code.vo.CodeVo;

/**
 * 코드 관리
 */
@Controller
public class CodeController {
	@Autowired
	private CodeService codeService;

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/code/page.do")
	public String page(HttpServletRequest request, HttpServletResponse response) {
		return "/app/code/code_page";
	}

	/**
	 * 항목 하나를 가져옴
	 *
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/code/get.json.do")
	@ResponseBody
	public CodeVo get(@ModelAttribute CodeVo param) {
		CodeVo code = codeService.getCode(param.getCodeSeq());
		return code;
	}

	/**
	 * 코드 조회 목록
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/code/list.json.do")
	@ResponseBody
	public List<CodeVo> list(@ModelAttribute CodeVo param) {
		List<CodeVo> page = codeService.listCode(param.getMajorCode());
		return page;
	}

	/**
	 * 코드 추가
	 *
	 * @param request
	 * @param response
	 * @return 추가한 코드 아이디
	 * @throws IOException
	 */
	@RequestMapping("/app/code/add.do")
	@ResponseBody
	public int add(@ModelAttribute CodeVo code, HttpServletRequest request) {
		codeService.createCode(code);
		return code.getCodeSeq();
	}

	/**
	 * 코드 삭제
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/code/delete.do")
	@ResponseBody
	public boolean delete(@ModelAttribute CodeVo param, HttpServletRequest request) {
		codeService.removeCode(param.getCodeSeq());
		return true;
	}

}