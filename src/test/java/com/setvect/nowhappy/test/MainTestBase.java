package com.setvect.nowhappy.test;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.setvect.nowhappy.NowHappyApplication;

/**
 * 스프링 테스트
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = { NowHappyApplication.class })
@TestPropertySource(locations = "file:" + NowHappyApplication.CONFIG_CONFIG_PROPERTIES_TEST)
public class MainTestBase {

}
