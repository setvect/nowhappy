package com.setvect.nowhappy;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.setvect.common.http.CookieProcess;
import com.setvect.common.util.SerializerUtil;
import com.setvect.nowhappy.user.vo.UserVo;

public class ApplicationUtil {
	/**
	 * 파일 업로드 및 다운로드 가능 여부를 체크함.
	 * 
	 * @param fileName
	 *            체크할 파일 이름
	 * @throws ApplicationException
	 *             불가능 할 경우 예외 발생
	 */
	public static void checkAllowUploadFile(String fileName) throws ApplicationException {
		String ext = FilenameUtils.getExtension(fileName).toLowerCase();

		int a = StringUtils.indexOfAny(ext, ApplicationConstant.WebCommon.ALLOW_UPLOAD_FILE);
		if (a == -1) {
			throw new ApplicationException("[" + fileName + "] 은 허가된 파일이 아닙니다.");
		}
	}

	/**
	 * 쿠기 정보에 있는 로그인 코드를 객채화 시켜 가져옴
	 * 
	 * @param request
	 * @return 로그인 안되어 있으면 null
	 * @throws Exception
	 */
	public static UserVo getLoginSession(HttpServletRequest request) {
		UserVo user = null;
		CookieProcess cookie = new CookieProcess(request);
		String encode = cookie.get(ApplicationConstant.WebCommon.USER_COOKIE_KEY);
		if (!StringUtils.isEmpty(encode)) {
			encode = encode.replaceAll("  ", "\n");
			try {
				user = (UserVo) SerializerUtil.restoreBase64Decode(encode);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return user;
	}

	/**
	 * 로그인 한 사람이 admin이면 true 리턴
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAdmin(HttpServletRequest request) {
		UserVo user = ApplicationUtil.getLoginSession(request);
		if(user == null){
			return false;
		}
		return user.isAdminF();
	}
}
