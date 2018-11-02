package com.setvect.nowhappy.network.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;
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
	public GenericPage<NetworkVo> listKnowledge(HttpServletRequest request) {
		String pg = StringUtilAd.null2str(request.getParameter("pageNumber"), "1");
		int pageNumber = Integer.parseInt(pg);
		String t = request.getParameter("pagePerItem");
		int pagePerItem = StringUtils.isEmpty(t) ? PAGE_PER_ITEM : Integer.parseInt(t);
		int startCursor = (pageNumber - 1) * pagePerItem;

		NetworkSearch pageCondition = new NetworkSearch(startCursor, pagePerItem);
		setSearchCondition(request, pageCondition);

		GenericPage<NetworkVo> page = networkRepository.getNetworkPagingList(pageCondition);

		return page;
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
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 */
	@RequestMapping("/network/add.do")
	@ResponseBody
	public boolean add(@ModelAttribute NetworkVo network, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		network.setRegDate(new Date());

		networkRepository.save(network);
		return true;
	}

	/**
	 * 네트워크 수정
	 *
	 * @param request
	 * @param response
	 * @return 추가한 코멘트 아이디
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/network/update.do")
	@ResponseBody
	public boolean update(@ModelAttribute NetworkVo param, HttpServletRequest request)
			throws FileNotFoundException, IOException {

		NetworkVo network = networkRepository.findOne(param.getNetworkSeq());
		network.setTitle(param.getTitle());
		network.setJsonData(param.getJsonData());
		networkRepository.save(network);
		return true;
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
