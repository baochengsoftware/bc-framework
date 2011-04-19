/**
 * 
 */
package cn.bc.identity.impl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorDetail;
import cn.bc.identity.domain.ActorDetailImpl;

/**
 * 参与者的默认实现
 * 
 * @author dragon
 */
@Entity
@Table(name = "BC_IDENTITY_ACTOR")
public class ActorImpl implements Actor {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Id
	@Column(name = "UID")
	private String uid;
	@Id
	@Column(name = "STATUS")
	private int status;
	@Id
	@Column(name = "INNER")
	private boolean inner;

	@Id
	@Column(name = "NAME")
	private String name;
	@Id
	@Column(name = "CODE")
	private String code;
	@Id
	@Column(name = "TYPE_")
	private int type;
	@Id
	@Column(name = "EMAIL")
	private String email;
	@Id
	@Column(name = "PHONE")
	private String phone;
	@Id
	@Column(name = "ORDER_")
	private String order;

	@OneToOne(targetEntity = ActorDetailImpl.class)
	@MapsId("ID")
	private ActorDetail detail;

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
		if (this.detail == null)
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
