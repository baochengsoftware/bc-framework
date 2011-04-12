/**
 * 
 */
package cn.bc.identity;



/**
 * 用户接口，代表人的抽象标识 标准接口中只定义标识、姓名、邮箱、电话属性，其余的需通过 setAttribute(String key, String value)和getAttribute(String key)方法扩展
 * @author  dragon
 */
public interface User extends Actor{
	/**性别:男*/
	public static final int SEX_MAN = 1;
	/**性别:女*/
	public static final int SEX_WOMEN = 2;
	
	/**
	 * @return  姓氏
	 */
	String getFirstName();
	/**
	 * @param firstName
	 */
	void setFirstName(String firstName);
	
	/**
	 * @return  名称
	 */
	String getLastName();
	/**
	 * @param  lastName
	 */
	void setLastName(String lastName);
	
	/**
	 * @return 性别,参考SEX_*常数的定义
	 */
	int getSex();
	/**
	 * @param  sex
	 */
	void setSex(int sex);
}
