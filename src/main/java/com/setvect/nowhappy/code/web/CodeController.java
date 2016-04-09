package com.setvect.nowhappy.code.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * @return
	 */
	@RequestMapping("/app/code/page.do")
	public String page() {
		return "/app/code/code_page";
	}

	/**
	 * 항목 하나를 가져옴
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping("/app/code/get.json.do")
	@ResponseBody
	public CodeVo get(@RequestParam("codeSeq") int codeSeq) {
		CodeVo code = codeService.getCode(codeSeq);
		return code;
	}

	/**
	 * 코드 조회 목록
	 *
	 * @return
	 */
	@RequestMapping("/app/code/list.json.do")
	@ResponseBody
	public List<CodeVo> list(@RequestParam("majorCode") String majorCode) {
		List<CodeVo> page = codeService.listCode(majorCode);
		return page;
	}

	/**
	 * 코드 추가
	 *
	 * @return 추가한 코드 아이디
	 */
	@RequestMapping("/app/code/add.do")
	@ResponseBody
	public void add(@ModelAttribute CodeVo code) {
		codeService.createCode(code);
	}

	/**
	 * 코드 수정
	 *
	 * @return 추가한 코드 아이디
	 */
	@RequestMapping("/app/code/update.do")
	@ResponseBody
	public void update(@ModelAttribute CodeVo code) {
		add(code);
	}

	/**
	 * 코드 삭제
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/code/delete.do")
	@ResponseBody
	public boolean delete(@RequestParam("codeSeq") int codeSeq) {
		codeService.removeCode(codeSeq);
		return true;
	}
}