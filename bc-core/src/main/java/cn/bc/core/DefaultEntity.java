/**
 * 
 */
package cn.bc.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 用户
 * 
 * @author dragon
 */
@Entity
public class DefaultEntity implements cn.bc.core.Entity<Long> {
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isNew() {
		return getId() == null || getId() <= 0;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isInner() {
		return inner;
	}
	public void setInner(boolean inner) {
		this.inner = inner;
	}
}
