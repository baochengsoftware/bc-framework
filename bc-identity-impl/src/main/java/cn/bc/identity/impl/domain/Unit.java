/**
 * 
 */
package cn.bc.identity.impl.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bc.identity.domain.Actor;

/**
 * 单位
 * 
 * @author dragon
 */
//@Entity
//@Table(name = "BC_IDENTITY_ACTOR")
public class Unit extends ActorImpl {
	private static final long serialVersionUID = 1L;

	public int getType() {
		return Actor.TYPE_UNIT;
	}
	public void setType(int type) {
		super.setType(Actor.TYPE_UNIT);
	}
}
