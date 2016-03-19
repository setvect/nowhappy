package com.setvect.nowhappy.ctmemo.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.ctmemo.CtmemoSearchCondition;
import com.setvect.nowhappy.ctmemo.service.CtmemoService;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.ctmemo.vo.WorkspaceVo;

@Controller
public class CtmemoController {

	@Inject
	private CtmemoService ctmemoService;

	@RequestMapping("/ctmemo/page.do")
	public String ctmemoPage() {
		return "/app/ctmemo/main";
	}

	@RequestMapping("/ctmemo/mobile.do")
	public String ctmemoMobile() {
		return "/app/ctmemo/mobile";
	}

	@RequestMapping("/ctmemo/listAllCtmemo.json.do")
	@ResponseBody
	public List<CtmemoVo> listAllCtmemo(HttpServletRequest request) {
		// init();
		CtmemoSearchCondition condition = new CtmemoSearchCondition();
		String seqStr = request.getParameter("workspaceSeq");
		int seq = Integer.parseInt(seqStr);
		condition.setSearchWorkspaceSeq(seq);

		List<CtmemoVo> result = ctmemoService.listCtmemo(condition);
		return result;
	}

	@RequestMapping("/ctmemo/workspace.json.do")
	@ResponseBody
	public List<WorkspaceVo> listWorkspace() {
		return ctmemoService.listWorkspace();
	}

	@RequestMapping("/ctmemo/newMemo.json.do")
	@ResponseBody
	public CtmemoVo newMemo(HttpServletRequest request) {
		String seqStr = request.getParameter("workspaceSeq");
		int seq = Integer.parseInt(seqStr);
		return ctmemoService.newMemo(seq);
	}

	/**
	 * @param ctmemo
	 * @return 변경한 메모의 z-index 값
	 */
	@RequestMapping("/ctmemo/saveMemo.do")
	@ResponseBody
	public int saveMemo(@ModelAttribute("ctmemo") CtmemoVo ctmemo, ServletRequest request) {
		// uptDate 필드 업데트 여부
		boolean dateupdate = Boolean.parseBoolean(request.getParameter("dateUpdateable"));
		if (dateupdate) {
			ctmemo.setUptDate(new Date());
		} else {
			CtmemoVo t = ctmemoService.getCtmemo(ctmemo.getCtmemoSeq());
			ctmemo.setUptDate(t.getUptDate());
		}

		ctmemo.setzIndex(ctmemoService.getMaxZindex());

		ctmemoService.updateCtmemo(ctmemo);
		return ctmemo.getzIndex();
	}

	@RequestMapping("/ctmemo/deleteMemo.do")
	@ResponseBody
	public String deleteMemo(ServletRequest request) {
		int ctmemoSeq = Integer.parseInt(request.getParameter("ctmemoSeq"));
		ctmemoService.removeCtmemo(ctmemoSeq);
		return "true";
	}

	/**
	 * 삭제 취소
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/ctmemo/undelete.json.do")
	@ResponseBody
	public CtmemoVo undelete(ServletRequest request) {
		int ctmemoSeq = Integer.parseInt(request.getParameter("ctmemoSeq"));
		CtmemoVo memo = ctmemoService.undeleteCtmemo(ctmemoSeq);
		return memo;
	}

	/**
	 * 사용하는 스타일
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/ctmemo/listUsagestyle.json.do")
	@ResponseBody
	public Map<String, List<String>> listUsagestyle() {
		Map<String, List<String>> m = new HashMap<String, List<String>>();
		m.put("font", ApplicationConstant.Style.FONTSTYLE_LIST);
		m.put("bg", ApplicationConstant.Style.BGSTYLE_LIST);
		return m;
	}

	@InitBinder
	protected void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				Date parsedDate = new Date(Long.parseLong(value));
				setValue(new Date(parsedDate.getTime()));
			}
		});
	}
}
