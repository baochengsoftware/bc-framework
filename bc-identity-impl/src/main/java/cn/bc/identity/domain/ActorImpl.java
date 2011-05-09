/**
 * 
 */
package cn.bc.identity.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import cn.bc.security.domain.Role;

/**
 * 参与者的默认实现
 * 
 * @author dragon
 */
@Entity
@Table(name = "BC_IDENTITY_ACTOR")
public class ActorImpl implements Actor {
	private static final long serialVersionUID = 1L;

	public ActorImpl(){
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "UID")
	private String uid;
	@Column(name = "STATUS_")
	private int status = cn.bc.core.Entity.STATUS_DISABLED;
	@Column(name = "INNER_")
	private boolean inner = false;

	@Column(name = "NAME")
	private String name;
	@Column(name = "CODE")
	private String code;
	@Column(name = "TYPE_")
	private int type = Actor.TYPE_UNDEFINED;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "PHONE")
	private String phone;
	@Column(name = "ORDER_")
	private String order;

	@OneToOne(targetEntity = ActorDetailImpl.class, cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "DETAIL_ID", referencedColumnName = "ID")
	private ActorDetail detail;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="BC_SECURITY_ROLE_ACTOR",
        joinColumns=
            @JoinColumn(name="AID", referencedColumnName="ID"),
        inverseJoinColumns=
            @JoinColumn(name="RID", referencedColumnName="ID")
        )
    @OrderBy("code asc")
	private Set<Role> roles;//拥有的角色列表

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

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
		// if (this.detail == null)
		// this.detail = new ActorDetail();
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
