package com.setvect.nowhappy.boot;

import java.net.URL;

import com.setvect.nowhappy.EnvirmentProperty;

/**
 */
public class EnvirmentInit {
	private static final String CONFIG_CONFIG_PROPERTIES = "/config/config.properties";

	public EnvirmentInit() {
		bootUp();
	}

	/**
	 * config propertity, log4j, spring, hibernate 설정 초기화
	 */
	public static void bootUp() {
		URL configUrl = EnvirmentInit.class.getResource(CONFIG_CONFIG_PROPERTIES);
		EnvirmentProperty.init(configUrl);
	}
}