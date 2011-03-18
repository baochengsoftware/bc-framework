/**
 * 
 */
package cn.bc.identity;


/**
 * 参与者的抽象标识，既可以代表一个人也可以代表一个组织。 使用该接口可以模糊人与组织间的关系，如一项任务可能派给具体的人也可以派给具体的某个部门或岗位
 * @author  dragon
 */
public interface Actor {
	/**
	 * @return  返回参与者的标识
	 */
	String getId();
	
	/**
	 * 设置参与者的标识
	 * @param id
	 */
	void setId(String id);
	
	/**
	 * @return  返回参与者的名称
	 */
	String getName();
	
	/**
	 * 设置参与者的名称
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * @return  返回参与者的类型
	 */
	String getType();
	
	/**
	 * 设置参与者的类型
	 * @param string
	 */
	void setType(String string);
}
