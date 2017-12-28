package com.setvect.nowhappy.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * 상대적인 데이터 값의 차이를 알아보기 편하게 제공함
 */
@Component
public class DateDiff {
	/** 리소스 */
	private static MessageSourceAccessor messageBundle;

	/**
	 * @param msa
	 *            리소스 번들
	 */
	@Autowired(required = true)
	public void setCommonCodeManager(final MessageSourceAccessor msa) {
		messageBundle = msa;
	}

	/**
	 * 상대적 시간 차이를 문자열로 표현
	 *
	 * @param nowDate
	 *            현재 시간
	 * @param sourceDate
	 *            기준 시간
	 * @return 시간 차이
	 */
	public static String diff(final Date nowDate, final Date sourceDate) {
		long diff = nowDate.getTime() - sourceDate.getTime();
		int min = (int) (diff / (1000 * 60));
		if (min == 0) {
			return messageBundle.getMessage("datediff.now");
		}

		if (min < 60) {
			return messageBundle.getMessage("datediff.minute", new Integer[] { min });
		}

		int hour = min / 60;
		if (hour < 24) {
			return messageBundle.getMessage("datediff.hour", new Integer[] { hour });
		}

		int day = hour / 24;
		return messageBundle.getMessage("datediff.day", new Integer[] { day });
	}
}
