package com.setvect.nowhappy.lotto.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.setvect.common.date.DateUtil;
import com.setvect.nowhappy.ApplicationConstant.WebAttributeKey;

@Controller
public class LottoController {
	private static final int MAX_LOTTERY_COUNT = 5;

	@RequestMapping("/lotto.do")
	public String lotto(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(WebAttributeKey.LOAD_PAGE, "/lottoPage.do");
		return "/home";
	}

	@RequestMapping("/lottoPage.do")
	public String luck(HttpServletRequest request, HttpServletResponse response) {

		String dayString = DateUtil.getSysDate();
		List<Set<Integer>> luckList = makeLottoNumber(dayString.hashCode(), MAX_LOTTERY_COUNT);
		request.setAttribute(WebAttributeKey.LOTTO, luckList);

		return "/app/lotto/lotto";
	}

	/**
	 * @param seed
	 *            랜덤 씨드
	 * @param lotteryCount
	 *            로또 추첨 수 1~5
	 * @return 운명의 번호
	 */
	public static List<Set<Integer>> makeLottoNumber(int seed, int lotteryCount) {
		List<Set<Integer>> result = new ArrayList<>();
		Random random = new Random(seed);

		for (int i = 0; i < lotteryCount; i++) {
			Set<Integer> r = new TreeSet<Integer>();
			while (true) {
				r.add(random.nextInt(45) + 1);
				if (r.size() == 6) {
					break;
				}
			}
			result.add(r);
		}
		return result;
	}
}
