package com.setvect.nowhappy.network.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.setvect.nowhappy.ApplicationUtil;

@Controller
public class NetworkController {

	/**
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/network/page.do")
	public String page(final HttpServletRequest request, final HttpServletResponse response) {
		if (!ApplicationUtil.isAdmin(request)) {
			return null;
		}

		return "/app/network/network_page";
	}

}
