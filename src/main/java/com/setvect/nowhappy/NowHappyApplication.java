package com.setvect.nowhappy;

import java.io.File;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.setvect.nowhappy.thumbnail.ThumbnailImageServlet;

/**
 * Spring boot application 시작점.
 */
@SpringBootApplication
@ImportResource({ "classpath:/spring/context-transaction.xml" })
public class NowHappyApplication extends SpringBootServletInitializer {
	/** 설정 파일 경로. */
	private static final String CONFIG_PROPERTIES = "/application.properties";
	/** 테스트 설정 파일 경로 */
	public static final String CONFIG_PROPERTIES_TEST = "./src/test/resources/test.properties";

	/**
	 * Application 시작점.
	 *
	 * @param args
	 *            사용 안함
	 */
	public static void main(final String[] args) {
		// spring boot에서 클래스가 및 properties 변경되었을 때 restart 안되게 함.
		// 즉 reload 효과
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(new Class[] { NowHappyApplication.class, ServletAppInitializer.class }, args);
	}

	@Override
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(NowHappyApplication.class, ServletAppInitializer.class);
	}

	/**
	 * 서비스 시작점 초기화.
	 *
	 * @return Spring boot 시작 bean
	 */
	@Bean
	InitializingBean init() {
		return () -> {
			String testEnv = System.getProperty(ApplicationConstant.TEST_CHECK_PROPERTY_NAME);
			if (Boolean.parseBoolean(testEnv)) {
				File configFile = new File(CONFIG_PROPERTIES_TEST);
				EnvirmentProperty.init(configFile);
			} else {
				URL configUrl = NowHappyApplication.class.getResource(CONFIG_PROPERTIES);
				EnvirmentProperty.init(configUrl);
			}

		};
	}

	/**
	 * @return JSP 변경 체크 여부 설정
	 */
	@Bean
	public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(final ConfigurableEmbeddedServletContainer container) {
				if (container instanceof TomcatEmbeddedServletContainerFactory) {
					customizeTomcat((TomcatEmbeddedServletContainerFactory) container);
				}
			}

			private void customizeTomcat(final TomcatEmbeddedServletContainerFactory tomcatFactory) {
				tomcatFactory.addContextCustomizers(new TomcatContextCustomizer() {

					@Override
					public void customize(final Context context) {
						Container jsp = context.findChild("jsp");
						if (jsp instanceof Wrapper) {
							// true면 변경 체크함, false 안함.
							((Wrapper) jsp).addInitParameter("development", "true");
						}
					}
				});
			}
		};
	}

	/**
	 * @return 리소스 번들
	 */
	@Bean
	public MessageSourceAccessor resourceBundle() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasenames("message_bundel/message");
		resourceBundleMessageSource.setDefaultEncoding("utf-8");

		MessageSourceAccessor msa = new MessageSourceAccessor(resourceBundleMessageSource);
		return msa;
	}

	/**
	 * 서블릿 설정
	 */
	public static class ServletAppInitializer implements ServletContextInitializer {
		@Override
		public void onStartup(final ServletContext container) {
			ServletRegistration.Dynamic dispatcher = container.addServlet("ThumbnailServlet",
					new ThumbnailImageServlet());
			dispatcher.addMapping("/servlet/Thumbnail");
		}
	}
}
