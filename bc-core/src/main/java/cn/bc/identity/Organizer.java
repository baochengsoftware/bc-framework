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
}
