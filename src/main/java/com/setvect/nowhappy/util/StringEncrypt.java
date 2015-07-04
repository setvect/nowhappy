package com.setvect.nowhappy.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

/**
 * 아래 구조로 스트링에 특정 키값을 합치면 HEX로 변환된 스트링 코드가 나온다. encode, decode가 가능하다.
 * 
 * 스트링 + 키값을 = HEX로 변환된 스트링
 * 
 * @version $Id: StringEncrypt.java,v 1.7 2006/10/24 13:47:42 setvect Exp $
 */
public class StringEncrypt {

	private static final int CUT_STRING_LEN = 80;

	static BitSet dontNeedEncoding;
	static {
		dontNeedEncoding = new BitSet(256);
		for (int i = 97; i <= 122; i++)
			dontNeedEncoding.set(i);

		for (int j = 65; j <= 90; j++)
			dontNeedEncoding.set(j);

		for (int k = 48; k <= 57; k++)
			dontNeedEncoding.set(k);

		dontNeedEncoding.set(32);
		dontNeedEncoding.set(45);
		dontNeedEncoding.set(95);
		dontNeedEncoding.set(46);
		dontNeedEncoding.set(42);
	}

	/**
	 * 
	 * @param pm_sStr
	 *            원본 문자열
	 * @param pm_sKey
	 *            키 문자열
	 * @return 암호화된 문자열
	 */
	public static String encodeJ(String pm_sStr, String pm_sKey) {
		int i, s;
		String lm_sRtnValue = "";
		String lm_sEncode;
		String lm_sKeyEncode;
		String lm_sValue;

		lm_sEncode = encode(pm_sStr);
		lm_sKeyEncode = encode(pm_sKey);

		lm_sValue = maxing(lm_sEncode, lm_sKeyEncode);
		s = lm_sValue.length();

		StringBuffer lm_sbT = new StringBuffer();
		// 80컬럼씩 줄끊기
		for (i = 0; i < s; i += CUT_STRING_LEN) {
			if (i + CUT_STRING_LEN < s) {
				lm_sbT.append(lm_sValue.substring(i, i + CUT_STRING_LEN));
			}
			else {
				lm_sbT.append(lm_sValue.substring(i, s));
			}

			lm_sbT.append('\n');
		}
		lm_sRtnValue = lm_sbT.toString();
		return lm_sRtnValue;
	}

	/**
	 * 
	 * @param pm_sStr
	 *            암호환 된 문자열
	 * @param pm_sKey
	 *            키 문자열
	 * @return 원본 문자열
	 */
	public static String decodeJ(String pm_sStr, String pm_sKey) {
		char lm_cT;
		int i, s;
		String lm_sRtnValue = "";
		String lm_sKeyEncode;
		StringBuffer lm_sbT = new StringBuffer();
		s = pm_sStr.length();

		for (i = 0; i < s; i++) {
			lm_cT = pm_sStr.charAt(i);
			// 숫자 또는 영문자이면 스트링 버퍼에 등록
			if (Character.isLetterOrDigit(lm_cT)) {
				lm_sbT.append(lm_cT);
			}
		}

		lm_sKeyEncode = encode(pm_sKey);
		lm_sRtnValue = decode(maxing(lm_sbT.toString(), lm_sKeyEncode));

		return lm_sRtnValue;
	}

	/**
	 * 
	 * @param pm_sEncode
	 *            Hex로 인코딩 된 문자
	 * @param pm_sKeyEncode
	 *            Hex로 인코딩 된 키
	 * @return 변환된 문자열 (Hex값)
	 */
	private static String maxing(String pm_sEncode, String pm_sKeyEncode) {
		byte lm_bSum = 0x00;
		char lm_cTemp;
		int lm_iTemp;
		int i;
		int lm_iLen;
		int lm_iLenKey;
		int lm_iLenKeyIndex;
		String lm_sRtnValue = "";
		String pm_sKeyEncode2;
		StringBuffer lm_sbTemp = new StringBuffer();

		lm_iLen = pm_sKeyEncode.length();
		// 키값을 합하여 하나의 캐릭터 문자를 만든다.
		for (i = 0; i < lm_iLen; i += 2) {
			lm_bSum += Integer.parseInt(pm_sKeyEncode.substring(i, i + 2), 16);
		}

		lm_iLen = pm_sKeyEncode.length();
		int lm_iTemp2;
		for (i = 0; i < lm_iLen; i += 2) {
			lm_iTemp2 = Integer.parseInt(pm_sKeyEncode.substring(i, i + 2), 16);
			lm_iTemp = (byte) (lm_iTemp2) ^ lm_bSum;
			lm_cTemp = Character.forDigit(lm_iTemp >> 4 & 0xF, 16);
			lm_sbTemp.append(lm_cTemp);
			lm_cTemp = Character.forDigit(lm_iTemp & 0xF, 16);
			lm_sbTemp.append(lm_cTemp);
		}

		// 완성된 키값
		pm_sKeyEncode2 = lm_sbTemp.toString();

		lm_sbTemp = new StringBuffer();

		lm_iLen = pm_sEncode.length();
		lm_iLenKey = pm_sKeyEncode2.length();
		lm_iLenKeyIndex = 0;

		for (i = 0; i < lm_iLen; i += 2) {
			// 키 인텍스 초기화
			if (lm_iLenKey <= lm_iLenKeyIndex) {
				lm_iLenKeyIndex = 0;
			}
			// XOR 특징: 어느한 데이터에 또같은 값으로 XOR연산을 두번하면 원본 데이터가 나온다.
			lm_iTemp = Integer.parseInt(pm_sEncode.substring(i, i + 2), 16)
					^ Integer.parseInt(pm_sKeyEncode2.substring(lm_iLenKeyIndex, lm_iLenKeyIndex + 2), 16);

			lm_cTemp = Character.forDigit(lm_iTemp >> 4 & 0xF, 16);
			lm_sbTemp.append(lm_cTemp);

			lm_cTemp = Character.forDigit(lm_iTemp & 0xF, 16);
			lm_sbTemp.append(lm_cTemp);

			lm_iLenKeyIndex += 2;
		}
		lm_sRtnValue = lm_sbTemp.toString().toUpperCase();
		return lm_sRtnValue;
	}

	/**
	 * URL Decode 하기위한 메소드
	 * 
	 * @param s
	 *            String type의 데이터
	 * @return 변환된 String
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String s) {
		byte byte0 = 10;
		StringBuffer stringbuffer = new StringBuffer(s.length());
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(byte0);
		OutputStreamWriter outputstreamwriter = null;
		try {
			// OS에 따라 default encoding이 달라지는 문제가 있어 명시적으로 정함.
			outputstreamwriter = new OutputStreamWriter(bytearrayoutputstream, "MS949");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (dontNeedEncoding.get(c)) {
				stringbuffer.append(Integer.toHexString(c));
				continue;
			}
			try {
				outputstreamwriter.write(c);
				outputstreamwriter.flush();
			} catch (IOException ex) {
				bytearrayoutputstream.reset();
				continue;
			}
			byte abyte0[] = bytearrayoutputstream.toByteArray();
			for (int j = 0; j < abyte0.length; j++) {
				char c1 = Character.forDigit(abyte0[j] >> 4 & 0xf, 16);
				// 문자이면 ' '를 빼서 대문자 만든다.
				if (Character.isLetter(c1))
					c1 -= ' ';
				stringbuffer.append(c1);
				c1 = Character.forDigit(abyte0[j] & 0xf, 16);
				if (Character.isLetter(c1))
					c1 -= ' ';
				stringbuffer.append(c1);
			}

			bytearrayoutputstream.reset();
		}

		return stringbuffer.toString();
	}

	/**
	 * 디코드하기위한 메소드
	 * 
	 * @param s
	 *            String type의 데이터
	 * @return 변환된 String
	 */
	public static String decode(String s) {
		byte abyte0[] = null;

		try {
			StringBuffer stringbuffer = new StringBuffer();

			for (int i = 0; i < s.length(); i += 2)
				stringbuffer.append((char) Integer.parseInt(s.substring(i, i + 2), 16));

			String s1 = stringbuffer.toString();
			abyte0 = s1.getBytes("8859_1");
			return new String(abyte0, "ms949");
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

	}
}