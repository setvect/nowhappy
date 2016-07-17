package com.setvect.nowhappy.test;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/context-common.xml", "classpath:spring/context-hibernate.xml",
		"classpath:springmvc/context-*.xml" })
@Transactional
@Rollback(true)
public class MainTestBase {

}
