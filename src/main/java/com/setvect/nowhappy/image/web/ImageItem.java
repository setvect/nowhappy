package com.setvect.nowhappy.image.web;

import org.springframework.web.multipart.MultipartFile;

/**
 * 이미지 정보 <br>
 * 향후 이미지에 대한 메타 정보까지 추가
 * 
 * @version $Id$
 */
public class ImageItem {
	/** 이미정보 */
	private MultipartFile[] imageFile;

	/**
	 * @return the attachFile
	 */
	public MultipartFile[] getImageFile() {
		return imageFile;
	}

	/**
	 * @param attachFile
	 *            the attachFile to set
	 */
	public void setImageFile(MultipartFile[] attachFile) {
		this.imageFile = attachFile;
	}

}
