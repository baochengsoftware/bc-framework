package cn.bc.identity.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name = "BC_IDENTITY_ACTOR_DETAIL")
public class ActorDetailImpl extends ActorDetail {
	private static Log logger = LogFactory.getLog(ActorDetailImpl.class);
	
	/**
	 * @return 创建时间
	 */
	//@Column
	@Transient
	public Calendar getCreateDate() {
		return getCalendar("createDate");
	}

	public void setCreateDate(Calendar createDate) {
		setAttr("createDate", createDate);
	}

	@Transient
	private Map<String, Object> attrs;

	@Transient
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

	@Transient
	public Object get(String key) {
		return getAttr(key);
	}

	public void set(String key, Object value) {
		setAttr(key, value);
	}

	@Transient
	public String getString(String key) {
		return (String) getAttr(key);
	}

	@Transient
	public Boolean getBoolean(String key) {
		return Boolean.valueOf(String.valueOf(getAttr(key)));
	}

	@Transient
	public Integer getInteger(String key) {
		return (Integer) getAttr(key);
	}

	@Transient
	public Long getLong(String key) {
		return (Long) getAttr(key);
	}

	@Transient
	public Float getFloat(String key) {
		return (Float) getAttr(key);
	}

	@Transient
	public Calendar getCalendar(String key) {
		return (Calendar) getAttr(key);
	}

	@Transient
	public Date getDate(String key) {
		return (Date) getAttr(key);
	}
}
