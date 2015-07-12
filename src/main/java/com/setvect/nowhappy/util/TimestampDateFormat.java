package com.setvect.nowhappy.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

/**
 * 타임스템프 값을 Date로 바인딩.
 */
public class TimestampDateFormat extends DateFormat {
	private static final long serialVersionUID = -262591871879026501L;

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		return null;
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		long v = Long.parseLong(source);
		Date date = new Date(v);
		pos.setIndex(1);
		return date;
	}
}
