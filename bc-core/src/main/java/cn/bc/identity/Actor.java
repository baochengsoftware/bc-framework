/**
 * 
 */
package cn.bc.identity;

import cn.bc.core.Entity;


/**
 * 参与者的抽象标识，既可以代表一个人也可以代表一个组织。 使用该接口可以模糊人与组织间的关系，如一项任务可能派给具体的人也可以派给具体的某个部门或岗位
 * @author  dragon
 */
public interface Actor extends Entity<Long>{
	/**参与者类型为用户*/
	public static final int TYPE_USER = 1;
	/**参与者类型为岗位*/
	public static final int TYPE_GROUP = 2;
	/**参与者类型为部门*/
	public static final int TYPE_DEPARTMENT = 3;
	/**参与者类型为单位*/
	public static final int TYPE_UNIT = 4;
	
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
	int getType();
	
	/**
	 * 设置参与者的类型
	 * @param int
	 */
	void setType(int string);
	
	/**
	 * @return  参与者的联系邮箱
	 */
	String getEmail();
	/**
	 * @param email
	 */
	void setEmail(String email);
	
	/**
	 * @return  参与者的联系电话
	 */
	String getPhone();
	/**
	 * @param phone
	 */
	void setPhone(String phone);
	
	/**
	 * 获取指定扩展属性的值
	 * @param key 属性名称
	 * @return
	 */
	Object getAttribute(String key);
	
	/**
	 * 设置或添加指定的扩展属性
	 * @param key 属性名称	
	 * @param value 属性值
	 */
	void setAttribute(String key, Object value);
}
