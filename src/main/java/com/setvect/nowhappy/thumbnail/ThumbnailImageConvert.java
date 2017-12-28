package com.setvect.nowhappy.thumbnail;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.coobird.thumbnailator.Thumbnails;


/**
 * 섬네일 이미지 제작
 */
public class ThumbnailImageConvert {
	private static Logger logger = LogManager.getLogger(ThumbnailImageConvert.class);

	/**
	 * 파일 확장자를 보고 gif->jpg, jpg->jpg 결정
	 *
	 * @param orgFile
	 *            원본 파일
	 * @param destFile
	 *            목적지 파일
	 * @param wishSizeW
	 *            최대 넓이
	 * @param wishSizeH
	 *            최대 높이
	 */
	public static void makeThumbnail(File orgFile, File destFile, int wishSizeW, int wishSizeH) {
		try {
			Thumbnails.of(orgFile).size(wishSizeW, wishSizeH).toFile(destFile);
			logger.info(String.format("make thumbnail %s -> %s", orgFile.getName(), destFile.getName()));
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
	}

}
