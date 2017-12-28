package com.setvect.nowhappy.migration;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 첨부파일 마이그레이션..
 */
@Controller
public class MigrationController {
	@Autowired
	private MigrationService service;

//	@RequestMapping("/migration/run.do")
//	@ResponseBody
	public String newMemo(HttpServletRequest request) {
		String destDir = request.getSession().getServletContext().getRealPath("/");
		File destPath = new File(destDir);
		return service.runMigration(destPath);
	}
}
