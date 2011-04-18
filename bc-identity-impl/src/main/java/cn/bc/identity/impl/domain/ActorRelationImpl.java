/**
 * 
 */
package cn.bc.identity.impl.domain;

import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorRelation;


/**
 * Actor之间的关联关系的默认实现
 * 
 * @author dragon
 */
public class ActorRelationImpl implements ActorRelation {
	private Actor master;
	private Actor follower;
	private int type;
	private String order;
	
	public Actor getMaster() {
		return master;
	}
	public void setMaster(Actor master) {
		this.master = master;
	}
	public Actor getFollower() {
		return follower;
	}
	public void setFollower(Actor follower) {
		this.follower = follower;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
}
