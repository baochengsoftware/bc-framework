/**
 * 
 */
package cn.bc.identity.domain;

import cn.bc.identity.Actor;
import cn.bc.identity.User;


/**
 * 用户
 * @author  dragon
 */
public class UserImpl extends ActorImpl implements User{
	private String firstName;
	private String lastName;
	private int sex;
	
	public int getType() {
		return Actor.TYPE_USER;
	}
	public void setType(int type) {
		// do nothing;
	}
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		this.biuldName();
	}
	private void biuldName() {
		if(this.getFirstName() == null){
			if(this.getLastName() == null){
				this.setName(null);
			}else{
				this.setName(this.getLastName());
			}
		}else{
			if(this.getLastName() == null){
				this.setName(this.getFirstName());
			}else{
				this.setName(this.getFirstName() + this.getLastName());
			}
		}
		
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.biuldName();
	}
	public Object getAttribute(String key) {
		if("firstName".equals(key))
			return getFirstName();
		else if("lastName".equals(key))
			return getLastName();
		else
			return super.getAttribute(key);
	}
	
	public void setAttribute(String key, Object value) {
		if("firstName".equals(key))
			setFirstName((String)value);
		else if("lastName".equals(key))
			setLastName((String)value);
		else
			super.setAttribute(key, value);
	}
}
