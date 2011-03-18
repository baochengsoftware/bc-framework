/**
 * 
 */
package cn.bc.identity;


/**
 * 用户接口，代表人的抽象标识 标准接口中只定义标识、姓名、邮箱、电话属性，其余的需通过 setAttribute(String key, String value)和getAttribute(String key)方法扩展
 * @author  dragon
 */
public interface User extends Actor{
	/**
	 * @return  用户的姓氏
	 */
	String getFirstName();
	/**
	 * @param firstName
	 */
	void setFirstName(String firstName);
	
	/**
	 * @return  用户的名称
	 */
	String getLastName();
	/**
	 * @param  lastName
	 */
	void setLastName(String lastName);
	
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
