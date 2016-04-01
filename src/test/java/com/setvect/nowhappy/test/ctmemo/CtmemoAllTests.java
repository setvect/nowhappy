package com.setvect.nowhappy.test.ctmemo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.setvect.nowhappy.test.ctmemo.CtmemoControllerTestCase;
import com.setvect.nowhappy.test.ctmemo.CtmemoRepositoryTestCase;
import com.setvect.nowhappy.test.ctmemo.CtmemoServiceTestCase;

/**
 * 전체 테스트
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ CtmemoRepositoryTestCase.class, CtmemoServiceTestCase.class, CtmemoControllerTestCase.class, })
public class CtmemoAllTests {
}
