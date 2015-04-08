package com.setvect.nowhappy.test.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 전체 테스트
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ UserDaoTestCase.class, UserServiceTestCase.class })
public class UserAllTests {
}
