package com.setvect.nowhappy.test.note;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.setvect.nowhappy.note.vo.NoteCategoryVo;
import com.setvect.nowhappy.note.web.NoteController;
import com.setvect.nowhappy.test.MainTestBase;

public class NoteControllerTestCase extends MainTestBase {
	@Autowired
	private NoteController controller;

	@Test
	public void test() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		List<NoteCategoryVo> rtnValue = controller.listCategory(request);
		
		for(NoteCategoryVo r : rtnValue){
			System.out.println(r);
		}
	}
}
