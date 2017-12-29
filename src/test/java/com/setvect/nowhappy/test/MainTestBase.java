package com.setvect.nowhappy.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.h2.tools.Server;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.setvect.nowhappy.ApplicationConstant;
import com.setvect.nowhappy.NowHappyApplication;

/**
 * 스프링 테스트
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = { NowHappyApplication.class })
@TestPropertySource(locations = "file:" + NowHappyApplication.CONFIG_PROPERTIES_TEST)
public class MainTestBase {
	/** 초기 데이터 입력 */
	private static final String INIT_INSERT_SQL = "/db/init_insert.sql";

	/** sql 실행 */
	@PersistenceContext
	private EntityManager em;

	static {
		System.setProperty(ApplicationConstant.TEST_CHECK_PROPERTY_NAME, "true");
		// openH2WebConsole();
	}

	/**
	 * 디버깅중 데이터 입력 값을 확인하기 위해 만듦.<br>
	 * TEST 중 JDBC 접속은 TCP로 되어야 됨<br>
	 * jdbc:h2:mem:literatureboy <-- 메모리로 DB를 오픈하면 당연히 접속을 못함.<br>
	 *
	 * 하지만 테스트 과정에서 트랜잭션이 적용되었기 때문에 테스트 과정에서 입력된 데이터를 확인할 수 없다.
	 */
	@SuppressWarnings("unused")
	private static void openH2WebConsole() {
		try {
			String[] optionWeb = "-webAllowOthers -webPort 8082".split(" ");
			Server h2Web;
			h2Web = Server.createWebServer(optionWeb);
			h2Web.setOut(new PrintStream(System.out));
			h2Web.start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 초기 값 등록.
	 *
	 */
	protected void insertInitValue() {
		InputStream in = MainTestBase.class.getResourceAsStream(INIT_INSERT_SQL);

		String text;
		try {
			text = IOUtils.toString(in, StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		String[] sqlInsertQueries = text.split("\n");

		Arrays.stream(sqlInsertQueries).filter(query -> StringUtils.isNotBlank(query)).forEach(query -> {
			Query insert = em.createNativeQuery(query);
			insert.executeUpdate();
		});
	}
}
