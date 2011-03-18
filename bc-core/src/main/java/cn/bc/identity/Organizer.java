/**
 * 
 */
package cn.bc.identity;


/**
 * 组织接口，代表单位、部门、岗位(团队)的抽象标识 标准接口中只定义标识、名称、邮箱、电话属性，其余的可以通过 setAttribute(String key, String value)和getAttribute(String key)方法扩展
 * @author  dragon
 */
public interface Organizer extends Actor{
	/**
	 * @return   所隶属的上级组织
	 */
	Organizer getBelong();
	
	/**
	 * 设置该组织所隶属的上级组织
	 * @param  belong
	 */
	void setBelong(Organizer belong);
	
	/**
	 * @return  用户的联系邮箱
	 */
	String getEmail();
	/**
	 * @param email
	 */
	void setEmail(String email);
	
	/**
	 * @return  用户的联系电话
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
