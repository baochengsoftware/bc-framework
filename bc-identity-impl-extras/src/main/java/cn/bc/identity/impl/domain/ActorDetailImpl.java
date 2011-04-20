package cn.bc.identity.impl.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.bc.identity.domain.ActorDetail;

@Entity
@Table(name = "BC_IDENTITY_ACTOR_DETAIL")
public class ActorDetailImpl implements ActorDetail {
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ActorDetailImpl.class);

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return 创建时间
	 */
	@Column
	public Calendar getCreateDate() {
		return getCalendar("createDate");
	}

	public void setCreateDate(Calendar createDate) {
		set("createDate", createDate);
	}

	/**
	 * @return 姓氏
	 */
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return getString("firstName");
	}
	public void setFirstName(String firstName) {
		set("firstName", firstName);
	}

	/**
	 * @return 名字
	 */
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return getString("lastName");
	}
	public void setLastName(String lastName) {
		set("lastName", lastName);
	}

	@Transient
	private Map<String, Object> attrs;

	@Transient
	public Object get(String key) {
		return (attrs != null && attrs.containsKey(key)) ? attrs.get(key)
				: null;
	}

	public void set(String key, Object value) {
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
	public String getString(String key) {
		return (String) get(key);
	}

	@Transient
	public Boolean getBoolean(String key) {
		return Boolean.valueOf(String.valueOf(get(key)));
	}

	@Transient
	public Integer getInteger(String key) {
		return (Integer) get(key);
	}

	@Transient
	public Long getLong(String key) {
		return (Long) get(key);
	}

	@Transient
	public Float getFloat(String key) {
		return (Float) get(key);
	}

	@Transient
	public Calendar getCalendar(String key) {
		return (Calendar) get(key);
	}

	@Transient
	public Date getDate(String key) {
		return (Date) get(key);
	}
}
