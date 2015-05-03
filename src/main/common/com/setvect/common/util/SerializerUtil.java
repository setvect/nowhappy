package com.setvect.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 직렬화 객체를 만들고, 직렬화된 객체를 복원 시킨다.
 * 
 * @version $Id: SerializerUtil.java,v 1.1 2006/07/12 05:33:59 setvect Exp $
 */
public class SerializerUtil {

	/**
	 * not instance
	 */
	private SerializerUtil() {

	}

	/**
	 * 객체을 정보를 파일로 직렬화를 만든다.
	 * 
	 * @param obj
	 *            직렬화 할 객체
	 * @param path
	 *            직렬화된 정보를 저장할 파일의 풀 패스(파일명 포함)
	 * @throws IOException
	 */
	public static void makeSerializer(Object obj, String path) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		makeSerializer(obj, fos);
		fos.close();
	}

	/**
	 * 객체을 정보를 직렬화 한다.
	 * 
	 * @param obj
	 *            직렬화 할 객체
	 * @param os
	 *            직렬화 데이터가 나갈 스트림
	 * @throws IOException
	 */
	public static void makeSerializer(Object obj, OutputStream os) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	/**
	 * 객체를 직렬화 하여 그 Byte 데이터를 BASE64코드로 변경해 문자열로 제공한다.
	 * 
	 * @param obj
	 *            직렬화 할 객체
	 * @return BASE64로 인코딩된 문자열
	 * @throws IOException
	 */
	public static String makeBase64Encode(Object obj) throws IOException {
		ByteArrayOutputStream os = null;
		BufferedInputStream sourceIn = null;
		ByteArrayOutputStream sourceOut = null;
		String encode;
		try {
			os = new ByteArrayOutputStream();
			// 객체를 정보를 스트림 형태로 가져옴
			SerializerUtil.makeSerializer(obj, os);

			// BASE64 형태로 바이터를 데이터를 TEXT 형태로 가져옴
			BASE64Encoder encoder = new BASE64Encoder();
			sourceIn = new BufferedInputStream(new ByteArrayInputStream(os.toByteArray()));
			sourceOut = new ByteArrayOutputStream();
			encoder.encode(sourceIn, sourceOut);
			encode = new String(sourceOut.toByteArray());
		} finally {
			if (sourceOut != null) {
				sourceOut.close();
			}
			if (sourceIn != null) {
				sourceIn.close();
			}
			if (os != null) {
				os.close();
			}
		}

		return encode;
	}

	/**
	 * 파일로 가지고 있는 직렬화 정보를 이용하여 객체를 생성한다.
	 * 
	 * @param path
	 *            직렬화 정보를 담고 있는 파일 경로 (파일명 포함)
	 * @return 직렬화 변경된것
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object restoreSerializer(String path) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		Object rtn = restoreSerializer(fis);
		fis.close();
		return rtn;
	}

	/**
	 * 스트림으로 부터 직렬화 데이터를 가져와 객체를 만든다.
	 * 
	 * @param is
	 *            직렬화 데이터가 있는 스트림
	 * @return 직렬화 변경된것
	 * @throws Exception
	 */
	public static Object restoreSerializer(InputStream is) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(is);
		Object rtn = ois.readObject();
		ois.close();
		return rtn;
	}

	/**
	 * Base64 코드를 통해 바이트 정보를 얻어내어 객체 정보로 변환한다.
	 * 
	 * @param base64
	 *            객체 직렬화 정보를 담고 있는 base64 데이터
	 * @return 변환된 객체
	 * @throws Exception
	 */
	public static Object restoreBase64Decode(String base64) throws Exception {
		ByteArrayInputStream targetIn = null;
		ByteArrayOutputStream targetOut = null;
		BufferedInputStream sourceIn = null;
		Object obj;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			targetIn = new ByteArrayInputStream(base64.getBytes());
			targetOut = new ByteArrayOutputStream();
			decoder.decodeBuffer(targetIn, targetOut);

			sourceIn = new BufferedInputStream(new ByteArrayInputStream(targetOut.toByteArray()));
			obj = SerializerUtil.restoreSerializer(sourceIn);
		} finally {
			if (sourceIn != null) {
				sourceIn.close();
			}
			if (targetOut != null) {
				targetOut.close();
			}
			if (targetIn != null) {
				targetIn.close();
			}
		}

		return obj;
	}
}
