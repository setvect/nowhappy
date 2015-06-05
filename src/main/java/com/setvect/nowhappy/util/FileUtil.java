package com.setvect.nowhappy.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.setvect.common.date.DateUtil;

public class FileUtil extends FileUtils {

	/**
	 * 오늘 날짜(년/월/일)통해 디렉토리를 만듬
	 * 
	 * @param path
	 *            기준 디렉토리
	 * @return 만들어진 디렉토리
	 */
	public static File makeDayDir(File path) {
		String year = NumberFormat.getNumberString("0000", DateUtil.getYear());
		String month = NumberFormat.getNumberString("00", DateUtil.getMonth());
		String day = NumberFormat.getNumberString("00", DateUtil.getDay());
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
}
