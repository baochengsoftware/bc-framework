/**
 * 
 */
package cn.bc.identity.impl.domain;

import cn.bc.identity.domain.Actor;

/**
 * 用户
 * 
 * @author dragon
 */
public class User extends ActorImpl {
	private static final long serialVersionUID = 1L;

	public int getType() {
		return Actor.TYPE_USER;
	}
	public void setType(int type) {
		// do nothing;
	}

	public Integer getSex() {
		return getDetail().getInteger("sex");
	}

	public void setSex(Integer sex) {
		getDetail().set("sex", sex);
	}

	public String getFirstName() {
		return getDetail().getString("firstName");
	}

	public void setFirstName(String firstName) {
		getDetail().set("firstName", firstName);
		this.biuldName();
	}

	private void biuldName() {
		if (this.getFirstName() == null) {
			if (this.getLastName() == null) {
				this.setName(null);
			} else {
				this.setName(this.getLastName());
			}
		} else {
			if (this.getLastName() == null) {
				this.setName(this.getFirstName());
			} else {
				this.setName(this.getFirstName() + this.getLastName());
			}
		}

	}

	public String getLastName() {
		return getDetail().getString("lastName");
	}

	public void setLastName(String lastName) {
		getDetail().set("lastName", lastName);
		this.biuldName();
	}
}
