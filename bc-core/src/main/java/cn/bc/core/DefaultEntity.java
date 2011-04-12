/**
 * 
 */
package cn.bc.core;


/**
 * 用户
 * 
 * @author dragon
 */
public class DefaultEntity implements Entity<Long> {
	private Long id;
	private String uid;
	
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
}
