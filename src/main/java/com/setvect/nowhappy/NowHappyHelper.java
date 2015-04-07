package com.setvect.nowhappy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.setvect.common.date.DateUtil;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;

public class NowHappyHelper {

	/**
	 * 서비스 시작 직후 등록하기위한 샘플데이터 제공
	 * 테스트 용도로 사용할 메소드.
	 * @return
	 */
	public static List<CtmemoVo> getSampleData() {
		List<CtmemoVo> result = new ArrayList<CtmemoVo>();

		CtmemoVo ctmemo = new CtmemoVo();
		ctmemo.setCtmemoSeq(1);
		ctmemo.setContent("내용1\n복슬이");
		ctmemo.setBgCss(NowHappyConstant.Style.BGSTYLE_1);
		ctmemo.setFontCss(NowHappyConstant.Style.FONTSTYLE_1);
		ctmemo.setWidth(160);
		ctmemo.setHeight(130);
		ctmemo.setPositionX(220);
		ctmemo.setPositionY(220);
		ctmemo.setzIndex(1);
		Date date = DateUtil.getDateTime("2015-02-13 11:22:11");
		ctmemo.setRegDate(date);
		ctmemo.setUptDate(date);
		result.add(ctmemo);

		ctmemo = new CtmemoVo();
		ctmemo.setCtmemoSeq(2);
		ctmemo.setContent("내용2");
		ctmemo.setBgCss(NowHappyConstant.Style.BGSTYLE_2);
		ctmemo.setFontCss(NowHappyConstant.Style.FONTSTYLE_2);
		ctmemo.setWidth(160);
		ctmemo.setHeight(150);
		ctmemo.setPositionX(100);
		ctmemo.setPositionY(100);
		ctmemo.setzIndex(2);
		date = DateUtil.getDate("2015-02-14");
		ctmemo.setRegDate(date);
		ctmemo.setUptDate(date);
		result.add(ctmemo);

		ctmemo = new CtmemoVo();
		ctmemo.setCtmemoSeq(2);
		ctmemo.setContent("처음 느낀 그대 눈빛은 혼자만의 오해였던가요\n" + "해맑은 미소로 나를 바보로 만들었소\n" + "내 곁을 떠나가던 날\n" + "가슴에 품었던 분홍빛의\n"
				+ "수많은 추억들이 푸르게 바래졌소 ");
		ctmemo.setBgCss(NowHappyConstant.Style.BGSTYLE_2);
		ctmemo.setFontCss(NowHappyConstant.Style.FONTSTYLE_2);
		ctmemo.setWidth(160);
		ctmemo.setHeight(150);
		ctmemo.setPositionX(300);
		ctmemo.setPositionY(350);
		ctmemo.setzIndex(3);
		date = DateUtil.getDate("2015-02-15");
		ctmemo.setRegDate(date);
		ctmemo.setUptDate(date);
		result.add(ctmemo);
		return result;
	}
}
