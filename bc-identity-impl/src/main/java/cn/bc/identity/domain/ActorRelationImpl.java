/**
 * 
 */
package cn.bc.identity.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Actor之间的关联关系的默认实现
 * 
 * @author dragon
 */
@Entity
@Table(name = "BC_IDENTITY_ACTOR_RELATION")
@IdClass(ActorRelationPK.class)
public class ActorRelationImpl implements ActorRelation {
	private static final long serialVersionUID = 1L;
	private Actor master;
	private Actor follower;
	private Integer type;
	private String order;
	
	@Id
	@ManyToOne(targetEntity = ActorImpl.class)
	@JoinColumn(name = "MASTER_ID", referencedColumnName = "ID")
	public Actor getMaster() {
		return master;
	}
	public void setMaster(Actor master) {
		this.master = master;
	}
	@Id
	@ManyToOne(targetEntity = ActorImpl.class)
	@JoinColumn(name = "FOLLOWER_ID", referencedColumnName = "ID")
	public Actor getFollower() {
		return follower;
	}
	public void setFollower(Actor follower) {
		this.follower = follower;
	}
	@Id
	@Column(name = "TYPE_")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "ORDER_")
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
}
