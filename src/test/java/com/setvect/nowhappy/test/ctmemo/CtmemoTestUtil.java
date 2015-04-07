package com.setvect.nowhappy.test.ctmemo;

import java.util.Date;

import com.setvect.common.date.DateUtil;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;

public class CtmemoTestUtil {
	public static CtmemoVo getCtmemoTestData() {
		CtmemoVo ctmemo = new CtmemoVo();
		ctmemo.setContent("내용");
		ctmemo.setBgCss("bg_1");
		ctmemo.setFontCss("font_1");
		ctmemo.setWidth(100);
		ctmemo.setHeight(100);
		ctmemo.setPositionX(100);
		ctmemo.setPositionY(100);
		ctmemo.setzIndex(1);
		Date date = DateUtil.getDate("2015-02-14");
		ctmemo.setRegDate(date);
		ctmemo.setUptDate(date);

		return ctmemo;
	}
}
