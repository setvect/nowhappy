package com.setvect.nowhappy.attach.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.attach.service.AttachFileService;
import com.setvect.nowhappy.attach.vo.AttachFileVo;
import com.setvect.nowhappy.util.FileUtil;

/**
 * 첨부파일 조회
 */
@Controller
public class AttachFileController {
	@Autowired
	private AttachFileService attachFileService;

	/**
	 * 첨부파일 목록
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/attachFile/list.json")
	@ResponseBody
	public List<AttachFileVo> list(@ModelAttribute AttachFileVo param, HttpServletRequest request) {
		List<AttachFileVo> result = attachFileService.listAttachFile(param.getModuleName(), param.getModuleId());
		// TODO javascript 변수를 보고 실제 저장된 경로를 파악 할 수 있음. 보안 문제를 해결해야됨.
		return result;
	}

	/**
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping("/download.do")
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 서버에 저장된 파일이름 풀 경로
		String saveName = StringUtilAd.decodeString(request.getParameter("s"));
		String downName;

		// 별도의 디코드 파일이 없으면 저장파일 이름을 다운로드
		if (StringUtilAd.isEmpty(request.getParameter("d"))) {
			// 다운로도될 파일이름
			int n = saveName.lastIndexOf("/");
			if (n == -1) {
				n = saveName.lastIndexOf("\\");
			}
			downName = saveName.substring(n + 1);
		}
		else {
			// 다운로도될 파일이름
			downName = StringUtilAd.decodeString(request.getParameter("d"));
		}
		String webBase = request.getSession().getServletContext().getRealPath("");
		File attachFile = new File(webBase, saveName);

		try {
			FileUtil.fileDown(attachFile, downName, request, response);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}
}
