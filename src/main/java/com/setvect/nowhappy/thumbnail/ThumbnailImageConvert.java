package com.setvect.nowhappy.thumbnail;

import java.awt.image.IndexColorModel;
import java.awt.image.renderable.ParameterBlock;
import java.net.URL;

import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.RenderedOp;

import org.apache.commons.io.FilenameUtils;

/**
 * 섬네일 이미지 제작
 * 
 * @version $Id$
 */
public class ThumbnailImageConvert {

	/**
	 * 파일 확장자를 보고 gif->jpg, jpg->jpg 결정
	 * 
	 * @param orgFile
	 *            원본 파일 이름(경로 포함)
	 * @param destFile
	 *            목적지 파일 이름(경로 포함)
	 * @param wishSizeW
	 *            최대 넓이
	 * @param wishSizeH
	 *            최대 높이
	 */
	public static void toJPEGAny(String orgFile, String destFile, int wishSizeW, int wishSizeH) {

		String ext = FilenameUtils.getExtension(orgFile);
		// gif
		if (ext.toLowerCase().endsWith("gif")) {
			toJPEGfromGIF(orgFile, destFile, wishSizeW, wishSizeH);
		}
		// jpg
		else {
			toJPEG(orgFile, destFile, wishSizeW, wishSizeH);
		}

	}

	/**
	 * 해당 사이즈에 맞는 섬네일 JPEG 이미지로 생성
	 * 
	 * @param orgFile
	 *            원본 파일 이름(경로 포함)
	 * @param destFile
	 *            목적지 파일 이름(경로 포함)
	 * @param wishSizeW
	 *            최대 넓이
	 * @param wishSizeH
	 *            최대 높이
	 */
	public static void toJPEG(String orgFile, String destFile, int wishSizeW, int wishSizeH) {
		RenderedOp src = JAI.create("fileload", orgFile);
		RenderedOp res_img = changeWidthScale(src, wishSizeW, wishSizeH);
		WritingImage.writeImageToFile(res_img, destFile, "JPEG");
	}

	/**
	 * * 해당 사이즈에 맞는 섬네일 JPEG 이미지로 생성
	 * 
	 * @param orgURL
	 *            원본 이미지 URL 경로
	 * @param destFile
	 *            목적지 파일 이름(경로 포함)
	 * @param wishSizeW
	 *            최대 넓이
	 * @param wishSizeH
	 *            최대 높이
	 */
	public static void toJPEG(URL orgURL, String destFile, int wishSizeW, int wishSizeH) {
		RenderedOp src = JAI.create("url", orgURL);
		RenderedOp res_img = changeWidthScale(src, wishSizeW, wishSizeH);
		WritingImage.writeImageToFile(res_img, destFile, "JPEG");
	}

	/**
	 * GIF 이미지를 해당 사이즈에 맞는 섬네일 JPEG 이미지로 생성
	 * 
	 * @param orgFile
	 *            원본 파일 이름(경로 포함)
	 * @param destFile
	 *            목적지 파일 이름(경로 포함)
	 * @param wishSizeW
	 *            최대 넓이
	 * @param wishSizeH
	 *            최대 높이
	 */
	public static void toJPEGfromGIF(String orgFile, String destFile, int wishSizeW, int wishSizeH) {
		RenderedOp src = JAI.create("fileload", orgFile);
		RenderedOp res_img = bandSelectGIF(src);
		res_img = changeWidthScale(res_img, wishSizeW, wishSizeH);
		WritingImage.writeImageToFile(res_img, destFile, "JPEG");

	}

	/**
	 * GIF 이미지를 해당 사이즈에 맞는 섬네일 JPEG 이미지로 생성
	 * 
	 * @param srcURL
	 *            원본 이미지 URL 경로
	 * @param destFile
	 *            목적지 파일 이름(경로 포함)
	 * @param wishSizeW
	 *            최대 넓이
	 * @param wishSizeH
	 *            최대 높이
	 */
	public static void toJPEGfromGIF(URL srcURL, String destFile, int wishSizeW, int wishSizeH) {
		RenderedOp src = JAI.create("url", srcURL);
		RenderedOp res_img = bandSelectGIF(src);
		res_img = changeWidthScale(res_img, wishSizeW, wishSizeH);
		WritingImage.writeImageToFile(res_img, destFile, "JPEG");
	}

	/**
	 * 해당 사이즈에 맞는 섬네일 PNG 이미지로 생성
	 * 
	 * @param orgFile
	 *            원본 파일 이름(경로 포함)
	 * @param destFile
	 *            목적지 파일 이름(경로 포함)
	 * @param wishSizeW
	 *            최대 넓이
	 * @param wishSizeH
	 *            최대 높이
	 */
	public static void toPNG(String orgFile, String destFile, int wishSizeW, int wishSizeH) {
		RenderedOp src = JAI.create("fileload", orgFile);
		RenderedOp res_img;
		res_img = changeWidthScale(src, wishSizeW, wishSizeH);
		WritingImage.writeImageToFile(res_img, destFile, "PNG");
	}

	/**
	 * 해당 사이즈에 맞는 섬네일 PNG 이미지로 생성
	 * 
	 * @param srcURL
	 *            원본 이미지 URL 경로
	 * @param destFile
	 *            목적지 파일 이름(경로 포함)
	 * @param wishSizeW
	 *            최대 넓이
	 * @param wishSizeH
	 *            최대 높이
	 */
	public static void toPNG(URL orgURL, String destFile, int wishSizeW, int wishSizeH) {
		RenderedOp src = JAI.create("url", orgURL);
		RenderedOp res_img = changeWidthScale(src, wishSizeW, wishSizeH);
		WritingImage.writeImageToFile(res_img, destFile, "PNG");
	}

	/**
	 * 뭔지 잘 모르겠다. GIF 에니메이션 프레임에 관련된 메소드 인것 같다.
	 * 
	 * @param src
	 *            이미지 정보
	 * @return 이미지 정보
	 */
	private static RenderedOp bandSelectGIF(RenderedOp src) {
		ParameterBlock pb = null;
		RenderedOp res_img = null;
		if (src.getColorModel() instanceof IndexColorModel) {
			IndexColorModel icm = (IndexColorModel) src.getColorModel();
			int mapSize = icm.getMapSize();
			byte lutData[][] = new byte[3][mapSize];
			icm.getReds(lutData[0]);
			icm.getGreens(lutData[1]);
			icm.getBlues(lutData[2]);
			LookupTableJAI lut = new LookupTableJAI(lutData);
			res_img = JAI.create("lookup", src, lut);
		}
		else if (src.getColorModel().hasAlpha()) {
			pb = new ParameterBlock();
			pb.addSource(src);
			pb.add(new int[] { 0, 1, 2 });
			res_img = JAI.create("BandSelect", pb);
		}
		else {
			res_img = src;
		}
		return res_img;
	}

	/**
	 * 원본 이미지에 축소할 가로, 세로 픽셀을 입력 받아, 최대 비율로 축소되는 비율을 구해 가로 세로 비율을 맞춘다.
	 * 
	 * @param src
	 *            이미지 소스
	 * @param wishSizeW
	 *            원하는 가로 크기(픽셀)
	 * @param wishSizeH
	 *            원하는 세로 크기(픽셀)
	 * @return 사이즈가 조정된 이미지 정보
	 */
	private static RenderedOp changeWidthScale(RenderedOp src, int wishSizeW, int wishSizeH) {
		int realW = src.getWidth();
		float ratioW = (float) wishSizeW / (float) realW;
		int realH = src.getHeight();
		float ratioH = (float) wishSizeH / (float) realH;

		// 가로, 세로 축소 비율중 최소 비율로 전체 비율 결정
		float ratio = Math.min(ratioW, ratioH);

		// 원본 이미지가 썸네일 만들 이미지 보다 작을 경우 원본 이미지 비율로 만들어 주기
		if (ratio > 1.0f) {
			ratio = 1.0f;
		}

		ParameterBlock scalePb = new ParameterBlock();
		scalePb.addSource(src);
		scalePb.add(ratio).add(ratio).add(0.0F).add(0.0F).add(new InterpolationNearest());
		RenderedOp res_img = CreateRenderedOp.getRenderOpFromParameterBlock("scale", scalePb);
		return res_img;
	}
}
