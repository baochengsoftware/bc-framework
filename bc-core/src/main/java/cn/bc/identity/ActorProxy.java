/**
 * 
 */
package cn.bc.identity;



/**
 * 参与者的代理者，当一个参与者拥有代理者后，所有与参与者相关的信息将由代理者代为处理
 * @author  dragon
 */
public interface ActorProxy extends Actor{
	/**
	 * @return 返回所代理的参与者
	 */
	Actor getActor();
	
	/**
	 * 设置所要代理的参与者
	 * @param actor
	 */
	void setActor(Actor actor);
}
