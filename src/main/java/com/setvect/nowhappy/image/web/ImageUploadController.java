package com.setvect.nowhappy.image.web;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.setvect.common.util.StringUtilAd;
import com.setvect.nowhappy.ApplicationConstant.FileUpload;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;
import com.setvect.nowhappy.util.Binder;
import com.setvect.nowhappy.util.FileUtil;

/**
 * 이미지 업로드
 */
@Controller
public class ImageUploadController {

	/**
	 * 서브 명령어 정의
	 */
	public static enum Mode {
		UPLOAD_FORM, UPLOAD_ACTION
	}

	@RequestMapping("/image/upload.do")
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String mode = request.getParameter("mode");

		Mode m;
		if (StringUtilAd.isEmpty(mode)) {
			m = Mode.UPLOAD_FORM;
		}
		else {
			m = Mode.valueOf(mode);
		}

		if (m == Mode.UPLOAD_FORM) {
			mav.addObject(WebAttributeKey.MODE, Mode.UPLOAD_ACTION);
			mav.setViewName("/app/image/image_upload_form");
		}
		else {
			String realPath = request.getSession().getServletContext().getRealPath(FileUpload.IMAGE_PATH);
			File baseDir = new File(realPath);
			File targetDir = FileUtil.makeDayDir(baseDir);
			ImageItem item = new ImageItem();
			Binder.bind(request, item);

			MultipartFile imageFile = item.getImageFile()[0];
			String saveName = "upload." + FilenameUtils.getExtension(imageFile.getOriginalFilename());
			File destination = File.createTempFile("image", saveName, targetDir);
			FileCopyUtils.copy(imageFile.getInputStream(), new FileOutputStream(destination));

			// 웹루트 디렉토리를 기준으로 이미지 접근 URL를 추출
			String webbaseDirString = request.getSession().getServletContext().getRealPath("/");
			String savePathString = destination.getPath();
			int pos = savePathString.indexOf(webbaseDirString);
			String imageUrl = savePathString.substring(pos + webbaseDirString.length() - 1);
			imageUrl = imageUrl.replace('\\', '/');
			String contextPath = request.getContextPath();
			imageUrl = contextPath + imageUrl;

			mav.addObject(WebAttributeKey.IMAGE_URL, imageUrl);

			mav.setViewName("/app/image/image_upload_complete");
		}
		return mav;
	}
}
