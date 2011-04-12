/**
 * 
 */
package cn.bc.identity.domain;

import cn.bc.core.exception.CoreException;
import cn.bc.identity.Actor;

/**
 * 用户
 * 
 * @author dragon
 */
public class ActorImpl implements Actor {
	private Long id;
	private String uid;
	private String name;
	private int type;
	private String email;
	private String phone;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Object getAttribute(String key) {
		if("id".equals(key))
			return getId();
		else if("name".equals(key))
			return getName();
		else if("type".equals(key))
			return getType();
		else if("email".equals(key))
			return getEmail();
		else if("phone".equals(key))
			return getPhone();
		else
			throw new CoreException("undefined key:" + key);
	}
	public void setAttribute(String key, Object value) {
		if("id".equals(key))
			setId((Long)value);
		else if("name".equals(key))
			setName((String)value);
		else if("type".equals(key))
			setType(Integer.parseInt(String.valueOf(value)));
		else if("email".equals(key))
			setEmail((String)value);
		else if("phone".equals(key))
			setPhone((String)value);
		else
			throw new CoreException("undefined key:" + key + "(value=" + value +")");
	}
	public boolean isNew() {
		return getId() == null || getId() <= 0;
	}
}
