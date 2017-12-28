package com.setvect.nowhappy.test.ctmemo;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.setvect.nowhappy.ctmemo.CtmemoSearchCondition;
import com.setvect.nowhappy.ctmemo.repository.CtmemoRepository;
import com.setvect.nowhappy.ctmemo.vo.CtmemoVo;
import com.setvect.nowhappy.test.MainTestBase;
import com.setvect.nowhappy.test.UnitTestUtil;

public class CtmemoRepositoryTestCase extends MainTestBase {

	@Autowired
	private CtmemoRepository ctmemoRepository;

	@Test
	@Transactional
	public void test() throws InterruptedException {
		System.out.println(ctmemoRepository);

		CtmemoVo ctmemo = UnitTestUtil.getCtmemoTestData();
		ctmemoRepository.save(ctmemo);

		CtmemoVo getmemo = ctmemoRepository.findOne(ctmemo.getCtmemoSeq());
		System.out.println(getmemo);
		Assert.assertThat(ctmemo, CoreMatchers.is(getmemo));

		CtmemoSearchCondition condition = new CtmemoSearchCondition();
		condition.setSearchWorkspaceSeq(1);
		List<CtmemoVo> list = ctmemoRepository.findByWorkspaceSeq(condition.getSearchWorkspaceSeq());
		Assert.assertThat(list.size(), CoreMatchers.is(19));

		String content = "내사랑 복슬이";
		ctmemo.setContent(content);
		ctmemoRepository.save(ctmemo);

		CtmemoVo result = ctmemoRepository.findOne(ctmemo.getCtmemoSeq());
		Assert.assertThat(content, CoreMatchers.is(result.getContent()));

		ctmemoRepository.delete(ctmemo.getCtmemoSeq());
		list = ctmemoRepository.findByWorkspaceSeq(condition.getSearchWorkspaceSeq());
		Assert.assertThat(list.size(), CoreMatchers.is(18));

	}

}
