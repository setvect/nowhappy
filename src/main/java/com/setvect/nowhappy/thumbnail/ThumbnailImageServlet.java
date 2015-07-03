package com.setvect.nowhappy.thumbnail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant.FileUpload;
import com.setvect.nowhappy.SessionCheckInterceptor;

/**
 * 웹 루트 바깥에 있는 이미지 파일을 열어서 데이터를 뿌여 주는 클래스 <br>
 * ex) <img src="/servlet/Thumbnail?i=이름지명&w=넓이&h=높이"> 이런 식으로 사용 하면 된다.
 * 
 * @version $Id$
 */
public class ThumbnailImageServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 3865935617999439844L;

	/** 섬네일 대상 이미지가 들어 있는 기본 경로 */
	public final static String BASE_DIR_SESSION_NAME = "_thum_base_dir";

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionCheckInterceptor.class);

	/**
	 * 썸네일 이미지
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// 원본 이미지
		String orgImagePath = req.getParameter("i");
		int width = Integer.parseInt(StringUtilAd.null2str(req.getParameter("w"), "0"));
		int height = Integer.parseInt(StringUtilAd.null2str(req.getParameter("h"), "0"));

		// 입력값이 재대로 입력되지 않으면 그냥 리턴
		if (orgImagePath == null || width == 0 || height == 0) {
			return;
		}
		File sourceImageFile;
		String baseDir = (String) req.getSession().getAttribute(BASE_DIR_SESSION_NAME);
		if (baseDir == null) {
			// 기본 경로가 없을 경우 웹 루트 경로를 기본으로 한다.
			sourceImageFile = new File(getServletConfig().getServletContext().getRealPath(orgImagePath));
		}
		else {
			sourceImageFile = new File(baseDir, orgImagePath);
		}

		// 파일이 존재 하지 않으면 그냥 종료
		if (!sourceImageFile.exists()) {
			LOGGER.warn(sourceImageFile + " not exist.");
			return;
		}

		// 섬네일 이미지 파일이름 만들기
		// e.g) imagename_w33_h44.jpg
		String tempImg = FilenameUtils.getBaseName(orgImagePath) + "_w" + width + "_h" + height + "."
				+ FilenameUtils.getExtension(orgImagePath);

		File saveDir = new File(getServletConfig().getServletContext().getRealPath(FileUpload.THUMBNAIL_PATH), orgImagePath).getParentFile();
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		// 섬네일 버전된 경로
		File toFile = new File(saveDir, tempImg);
		boolean fileExist = toFile.exists();
		boolean fileOld = toFile.lastModified() < sourceImageFile.lastModified();

		// 기존에 섬네일로 변환된 파일이 있는냐?
		// 섬네일로 변환된 파일이 없거나, 파일이 수정되었을 경우 섬네일 다시 만들기
		if (!fileExist || fileOld) {
			ThumbnailImageConvert.toJPEGAny(sourceImageFile.getPath(), toFile.getPath(), width, height);
		}

		// 썸네일로 변환된 파일 읽어 들리기
		FileInputStream ifp = null;

		try {
			ifp = new FileInputStream(toFile);
			res.setContentType("image/gif");
			ServletOutputStream out = res.getOutputStream();
			int ch;

			byte b[] = new byte[1024];
			while ((ch = ifp.read(b)) != -1) {
				out.write(b, 0, ch);
				out.flush();
			}
		} catch (Exception e) {
			LOGGER.error("read fail: " + toFile, e);
			return;
		} finally {
			if (ifp != null) {
				ifp.close();
			}
		}
	}
}