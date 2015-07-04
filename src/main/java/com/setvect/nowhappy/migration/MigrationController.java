package com.setvect.nowhappy.migration;

import java.io.File;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

/**
 * 첨부파일 마이그레이션..
 */
@Controller
public class MigrationController {
	@Inject
	private MigrationService service;

//	@RequestMapping("/migration/run.do")
//	@ResponseBody
	public String newMemo(HttpServletRequest request) {
		String destDir = request.getSession().getServletContext().getRealPath("/");
		File destPath = new File(destDir);
		return service.runMigration(destPath);
	}
}
