/**
 * 
 */
package cn.bc.identity;


/**
 * Actor之间的关联关系 如：<p>1) 用户所隶属的单位或部门 </p><p>2) 用户所在岗位  </p><p>3) 组织的负责人、正职、副职、归档人等 </p> <p>4) 不同组织间的业务关系 </p>
 * @author  dragon
 */
public interface ActorRelation {
	/**
	 * @return   返回关联关系中的主控方，如岗位与用户关系中的岗位
	 */
	Actor getMaster();
	
	/**
	 * 设置关联关系中的主控方
	 * @param  master
	 */
	void setMaster(Actor master);
	
	/**
	 * @return   返回关联关系中的从属方，如岗位与用户关系中的用户
	 */
	Actor getFollower();
	
	/**
	 * 设置关联关系中的从属方
	 * @param  follower
	 */
	void setFollower(Actor follower	);
	
	/**
	 * @return  返回关联类型
	 */
	int getType();
	
	/**
	 * 设置关联类型
	 * @param  string
	 */
	void setType(int string);
	
	/**
	 * @return  返回多个从属方之间的排序号
	 */
	String getOrder();
	
	/**
	 * 设置多个从属方之间的排序号
	 * @param order
	 */
	void setOrder(String order);
}