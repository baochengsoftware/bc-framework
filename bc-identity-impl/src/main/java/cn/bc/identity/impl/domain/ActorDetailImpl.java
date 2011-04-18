/**
 * 
 */
package cn.bc.identity.impl.domain;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.bc.identity.domain.ActorDetail;

/**
 * 参与者的扩展属性封装的默认实现
 * 
 * @author dragon
 * 
 */
public class ActorDetailImpl implements ActorDetail {
	private static Log logger = LogFactory.getLog(ActorDetailImpl.class);
	private Map<String, Object> attrs;

	protected Object getAttr(String key) {
		return (attrs != null && attrs.containsKey(key)) ? attrs.get(key)
				: null;
	}

	protected void setAttr(String key, Object value) {
		if (logger.isDebugEnabled())
			logger.debug("key=" + key + ";value=" + value + ";valueType="
					+ (value != null ? value.getClass() : "?"));
		if (key == null)
			throw new RuntimeException("key can't to be null");
		if (attrs == null)
			attrs = new HashMap<String, Object>();
		attrs.put(key, value);
	}

	public Object get(String key) {
		return getAttr(key);
	}

	public void set(String key, Object value) {
		setAttr(key, value);
	}

	public String getString(String key) {
		return (String) getAttr(key);
	}

	public void set(String key, String value) {
		setAttr(key, value);
	}

	public boolean getBoolean(String key) {
		return (boolean) Boolean.parseBoolean(String.valueOf(getAttr(key)));
	}

	public void set(String key, boolean value) {
		setAttr(key, value);
	}

	public Integer getInteger(String key) {
		return (Integer) getAttr(key);
	}

	public void set(String key, Integer value) {
		setAttr(key, value);
	}

	public Long getLong(String key) {
		return (Long) getAttr(key);
	}

	public void set(String key, Long value) {
		setAttr(key, value);
	}

	public Float getFloat(String key) {
		return (Float) getAttr(key);
	}

	public void set(String key, Float value) {
		setAttr(key, value);
	}

	public Calendar getCalendar(String key) {
		return (Calendar) getAttr(key);
	}

	public void set(String key, Calendar value) {
		setAttr(key, value);
	}
}
