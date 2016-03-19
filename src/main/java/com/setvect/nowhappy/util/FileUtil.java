package com.setvect.nowhappy.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FileUtil extends FileUtils {

	/**
	 * 오늘 날짜(년/월/일)통해 디렉토리를 만듬
	 * 
	 * @param path
	 *            기준 디렉토리
	 * @return 만들어진 디렉토리
	 */
	public static File makeDayDir(File path) {
		return makeDayDir(path, new Date());
	}

	/**
	 * 기준 날짜(년/월/일)통해 디렉토리를 만듬
	 * 
	 * @param path
	 *            기준 디렉토리
	 * @param baseDate
	 *            기준 날짜
	 * @return 만들어진 디렉토리
	 */
	public static File makeDayDir(File path, Date baseDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate);
		
		String year = NumberFormat.getNumberString("0000", c.get(Calendar.YEAR));
		String month = NumberFormat.getNumberString("00", c.get(Calendar.MONTH) + 1);
		String day = NumberFormat.getNumberString("00", c.get(Calendar.DATE));
		// 디렉토리가 있는지
		if (!path.isDirectory()) {
			throw new RuntimeException(path + " directory is not exist!");
		}

		File makedir = new File(path, year);
		makedir = new File(makedir, month);
		makedir = new File(makedir, day);
		makedir.mkdirs();
		return makedir;
	}

	/**
	 * 디렉토리를 탐색하여 원하는 파일을 찾음
	 * 
	 * @param baseDir
	 *            파일 탐색 시작 지점
	 * 
	 * @param subDirectory
	 *            하위 디렉토리 탐색 여부
	 * @param exts
	 *            확장자 <br>
	 * @return 결과 파일 목록
	 */
	public static List<File> getSubFiles(File baseDir, boolean subDirectory, Set<String> exts) {
		if (subDirectory) {
			FileFinder files = new FileFinder(baseDir, exts);
			return files.getFiles();
		}

		return getSubFiles(baseDir, exts);
	}

	/**
	 * @param baseDir
	 *            기준 디렉토리
	 * @param exts
	 *            맴칭할 확장자<br>
	 *            예) hwp,pdf,html
	 * @return 디렉토리 내에 있는 파일 중에 확장자와 매칭되는 파일 목록
	 */
	public static List<File> getSubFiles(File baseDir, Set<String> exts) {
		List<File> files = new ArrayList<File>();

		File[] fileList = baseDir.listFiles();
		Set<String> includeExt = new HashSet<String>();
		for (String e : exts) {
			includeExt.add(e);
		}

		for (File f : fileList) {
			String ext = FilenameUtils.getExtension(f.getName());
			if (!f.isFile()) {
				continue;
			}
			if (includeExt.contains(ext.toLowerCase())) {
				files.add(f);
			}
		}
		return files;
	}

	/**
	 * 하위 폴더를 탐색하여 특정 확장자를 갖는 파일 목록을 검색 <br>
	 * TODO 공통 라이브러리로 이동
	 * 
	 * @version $Id$
	 */
	private static class FileFinder {
		private List<File> files = new ArrayList<File>();
		private File baseDir;
		private Set<String> includeExt;

		/**
		 * @param baseDir
		 *            검색 시작 디렉토리
		 * @param ext
		 *            검색 대상 확장자. 확장자는 포함 <br>
		 *            ex) hwp, mp3, ...
		 */
		FileFinder(File baseDir, Set<String> ext) {
			this.baseDir = baseDir;
			this.includeExt = ext;
			setFile(this.baseDir);
		}

		/**
		 * @return 파일 목록
		 */
		public List<File> getFiles() {
			return Collections.unmodifiableList(files);
		}

		private void setFile(File dir) {
			File[] fileList = dir.listFiles();
			if (fileList == null) {
				return;
			}
			for (File f : fileList) {
				if (f.isDirectory() == true) {
					this.setFile(f);
				}
			}
			List<File> c = getSubFiles(dir, includeExt);
			files.addAll(c);
		}
	}

	/**
	 * file download method
	 * 
	 * @param file
	 *            다운로드할 파일 경로
	 * 
	 * @param downloadFileName
	 *            Download file name from client computer
	 * @param request
	 *            Request object
	 * @param response
	 *            Response object
	 */
	public static void fileDown(File file, String downloadFileName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String encodeCharSet = request.getCharacterEncoding();
		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;
		int read = 0;
		byte[] b = new byte[1024];
		// 버퍼 클리어
		response.reset();
		try {
			if (file.exists()) {
				fin = new BufferedInputStream(new FileInputStream(file));
				outs = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/x-force-download");

				// 한글 깨짐 현상은 java.net.URLEncoder.encode()를 쓰면 해결 됨
				// response.setHeader("Content-Disposition", option + ";
				// filename=" + java.net.URLEncoder.encode(orgFileName) + ";");

				response.setHeader("Content-Type", "application/octet-stream; charset=" + encodeCharSet + "\"");

				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(downloadFileName, encodeCharSet) + ";");

				response.setHeader("Content-Length", "" + file.length());
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				while ((read = fin.read(b)) != -1) {
					outs.write(b, 0, read);
				}
			}
			else {
				throw new IOException("not exist : " + file);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (outs != null) {
				try {
					outs.close();
				} catch (IOException e) {
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
