package com.setvect.nowhappy.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestDataBinder;

/**
 * Request 값을 VO 클래스의 바인드
 * 
 * @version $Id$
 */
public class Binder {
	/**
	 * 바인드
	 * 
	 * @param request
	 * @param object
	 *            바인드될 Object
	 * @throws ServletRequestBindingException
	 */
	public static void bind(HttpServletRequest request, Object object) throws ServletRequestBindingException {
		bind(request, object, null);
	}

	/**
	 * @param request
	 * @param object
	 * @param customEditor
	 *            bind될 속성 클래스에 따른 request 방법
	 * @throws ServletRequestBindingException
	 */
	public static void bind(HttpServletRequest request, Object object, Map<Class<?>, PropertyEditorSupport> customEditor)
			throws ServletRequestBindingException {
		ServletRequestDataBinder binder = new ServletRequestDataBinder(object);

		if (customEditor != null) {
			Set<Class<?>> keys = customEditor.keySet();
			for (Class<?> k : keys) {
				binder.registerCustomEditor(k, customEditor.get(k));
			}
		}

		binder.bind(request);
		binder.closeNoCatch();
	}

	/**
	 * @param dateFormat
	 * @return 날짜 패턴 bind
	 */
	public static PropertyEditorSupport getDateFromatProperty(String dateFormat) {

		return new DatePropertyEditorSupport(dateFormat) {
			public void setAsText(String value) {
				try {
					setValue(new SimpleDateFormat(pattern).parse(value));
				} catch (ParseException e) {
					setValue(null);
				}
			}

			public String getAsText() {
				return new SimpleDateFormat(pattern).format((Date) getValue());
			}
		};

	}

	static class DatePropertyEditorSupport extends PropertyEditorSupport {
		protected final String pattern;

		public DatePropertyEditorSupport(String pattern) {
			this.pattern = pattern;
		}
	}

}
