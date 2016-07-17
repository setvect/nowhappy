package com.setvect.nowhappy.test.knowledge;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.nowhappy.knowledge.service.KnowledgeService;
import com.setvect.nowhappy.knowledge.vo.KnowledgeVo;
import com.setvect.nowhappy.test.MainTestBase;
import com.setvect.nowhappy.util.StringEtcUtil;

/**
 * 복슬지식 엑셀데이터 -> DB로 마이그레이션
 *
 * @Rollback(false)로 셋팅 해야됨.
 */
public class KnowledgeTestCase extends MainTestBase {
	@Autowired
	private KnowledgeService knowledgeService;

	private static Map<String, String> CODE = new HashMap<>();
	static {
		CODE.put("자바스크립트", "JAVASCRIPT");
		CODE.put("자바", "JAVA");
		CODE.put("HTML, CSS", "HTML_CSS");
		CODE.put("OS 및 각종 설정", "OS_SETTING");
		CODE.put("DBMS", "DBMS");
		CODE.put("기타", "ETC");
	}

	@Test
	public void test() throws IOException {
		List<KnowledgeVo> list = getKnowledgeData();
		System.out.println("시작");
		for (KnowledgeVo v : list) {
			knowledgeService.insertKnowledge(v);
		}
		System.out.println("끝.");
	}

	static List<KnowledgeVo> getKnowledgeData() throws IOException {

		List<KnowledgeVo> result = new ArrayList<>();

		FileInputStream fis = new FileInputStream("doc/knowledge.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int rowindex = 0;
		// 시트 수 (첫번째에만 존재하므로 0을 준다)
		// 만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
		XSSFSheet sheet = workbook.getSheetAt(0);
		// 행의 수
		int rows = sheet.getPhysicalNumberOfRows();
		for (rowindex = 1; rowindex < rows; rowindex++) {
			// 행을읽는다
			XSSFRow row = sheet.getRow(rowindex);
			if (row == null) {
				continue;
			}

			KnowledgeVo k = new KnowledgeVo();
			// 셀의 수
			XSSFCell problem = row.getCell(2);
			XSSFCell code = row.getCell(3);
			String problemString = problem.getStringCellValue();
			if (StringUtils.isEmpty(problemString.trim())) {
				continue;
			}

			problemString = StringEtcUtil.toText(problemString);

			k.setProblem(problemString);
			String codeValue = code.getStringCellValue();
			k.setClassifyC(CODE.get(codeValue));
			k.setRegDate(new Date());

			result.add(k);
		}
		workbook.close();
		return result;
	}

}
