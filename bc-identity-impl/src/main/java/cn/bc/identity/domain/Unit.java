/**
 * 
 */
package cn.bc.identity.domain;

/**
 * 单位
 * 
 * @author dragon
 */
public class Unit extends ActorImpl {
	private static final long serialVersionUID = 1L;

	public int getType() {
		return Actor.TYPE_UNIT;
	}
	public void setType(int type) {
		super.setType(Actor.TYPE_UNIT);
	}
}
