/**
 * 
 */
package cn.bc.identity.impl.domain;

import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorDetail;


/**
 * 参与者的默认实现
 * 
 * @author dragon
 */
public class ActorImpl implements Actor {
	private Long id;
	private String uid;
	private String name;
	private String code;
	private int type;
	private int status;
	private String email;
	private String phone;
	private String order;
	private ActorDetail detail;
	private boolean inner;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public ActorDetail getDetail() {
		if(this.detail == null)
			this.detail = new ActorDetailImpl();
		return detail;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public void setDetail(ActorDetail detail) {
		this.detail = detail;
	}
	public boolean isNew() {
		return getId() == null || getId() <= 0;
	}
	public boolean isInner() {
		return inner;
	}
	public void setInner(boolean inner) {
		this.inner = inner;
	}
}
