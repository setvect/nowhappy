package com.setvect.nowhappy.network.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.common.util.GenericPage;
import com.setvect.nowhappy.ApplicationUtil;
import com.setvect.nowhappy.network.repository.NetworkRepository;
import com.setvect.nowhappy.network.vo.NetworkVo;

@Controller
public class NetworkController {
	/** 한 페이지당 표시 항목 갯수 */
	private static final int PAGE_PER_ITEM = 10;

	@Autowired
	private NetworkRepository networkRepository;

	/**
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/network/list.do")
	public String listPage(HttpServletRequest request) {
		if (!ApplicationUtil.isAdmin(request)) {
			return null;
		}
		return "/app/network/network_list";
	}

	/**
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/network/page.do")
	public String page(HttpServletRequest request) {
		if (!ApplicationUtil.isAdmin(request)) {
			return null;
		}
		return "/app/network/network_page";
	}

	/**
	 * 네트워크 목록
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/network/list.json")
	@ResponseBody
	public GenericPage<NetworkVo> list(HttpServletRequest request) {
		NetworkSearch pageCondition = new NetworkSearch(0, Integer.MAX_VALUE);
		setSearchCondition(request, pageCondition);

		GenericPage<NetworkVo> page = networkRepository.getNetworkPagingList(pageCondition);

		return page;
	}

	/**
	 * 네트워크 목록
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/network/get.json")
	@ResponseBody
	public NetworkVo get(@RequestParam(name = "networkSeq") int networkSeq) {
		NetworkVo network = networkRepository.findOne(networkSeq);
		return network;
	}

	/**
	 * 검색 조건 적용
	 *
	 * @param request
	 * @param pageCondition
	 */
	private void setSearchCondition(HttpServletRequest request, NetworkSearch pageCondition) {
		String title = request.getParameter("searchTitle");
		pageCondition.setSearchTitle(title);
	}

	/**
	 * 네트워크 등록
	 *
	 * @param network
	 *            .
	 * @return 추가한 코멘트 아이디
	 */
	@RequestMapping("/network/save.do")
	@ResponseBody
	public int add(@ModelAttribute final NetworkVo network) {
		Date date = new Date();
		network.setEditDate(new Date());
		if (network.getNetworkSeq() == 0) {
			network.setRegDate(date);
		} else {
			NetworkVo b = networkRepository.findOne(network.getNetworkSeq());
			network.setRegDate(b.getRegDate());
		}
		network.setTitle(StringUtils.defaultString(network.getTitle(), "untitle"));

		networkRepository.save(network);
		return network.getNetworkSeq();
	}

	/**
	 * 네트워크 삭제<br>
	 * 논리적 삭제
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/network/delete.do")
	@ResponseBody
	public boolean delete(@ModelAttribute NetworkVo param, HttpServletRequest request) {
		NetworkVo network = networkRepository.findOne(param.getNetworkSeq());
		network.setDeleteF(true);
		networkRepository.save(network);
		return true;
	}
}
