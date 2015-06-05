
package com.setvect.nowhappy.util;

/**
 *  숫자 포맵 변환 관련 클래스
 * 
 * @version $Id: NumberFormat.java,v 1.5 2006/06/28 06:28:23 setvect Exp $
 */
public class NumberFormat {

	/**
	 * @param pattern
	 *            표현 패턴
	 * @param number
	 *            포맷을 원한는 숫자
	 * @return 포맷을 적용한 숫자
	 */
	public static String getNumberString(String pattern, int number) {

		java.text.DecimalFormat format = new java.text.DecimalFormat(pattern);
		String result = format.format(number);

		return result;
	}

	/**
	 * @param pattern
	 *            표현 패턴
	 * @param number
	 *            포맷을 원한는 숫자
	 * @return 포맷을 원한는 숫자
	 */
	public static String getNumberString(String pattern, long number) {

		java.text.DecimalFormat format = new java.text.DecimalFormat(pattern);
		String result = format.format(number);

		return result;
	}

	/**
	 * @param pattern
	 *            표현 패턴
	 * @param number
	 *            포맷을 원한는 숫자
	 * @return 포맷을 원한는 숫자
	 */
	public static String getNumberString(String pattern, double number) {

		java.text.DecimalFormat format = new java.text.DecimalFormat(pattern);
		String result = format.format(number);

		return result;
	}

	/**
	 * @param pattern
	 *            표현 패턴
	 * @param number
	 *            포맷을 원한는 숫자
	 * @return 포맷을 원한는 숫자
	 */
	public static String getNumberString(String pattern, float number) {

		java.text.DecimalFormat format = new java.text.DecimalFormat(pattern);
		String result = format.format(number);

		return result;
	}

}