package com.setvect.nowhappy.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.setvect.nowhappy.test.ctmemo.CtmemoAllTests;
import com.setvect.nowhappy.test.user.UserAllTests;

/**
 * 전체 테스트
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ CtmemoAllTests.class, UserAllTests.class })
public class NowHappyAllTests {
}
