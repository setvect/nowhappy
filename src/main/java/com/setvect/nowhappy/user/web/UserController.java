package com.setvect.nowhappy.user.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.user.vo.UserVo;

/**
 * 사용자 정보
 */
@Controller
public class UserController {
	/**
	 * 로그인 정보
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/user/loginInfo.json.do")
	public UserVo loginInfo(HttpServletRequest request) {
		UserVo user = ApplicationUtil.getLoginSession(request);
		return user;
	}
}