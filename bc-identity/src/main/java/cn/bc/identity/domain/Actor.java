/**
 * 
 */
package cn.bc.identity.domain;

import cn.bc.core.Entity;

/**
 * 参与者
 * <p>
 * 参与者既可以代表一个人也可以代表一个组织, 使用该接口可以模糊人与组织间的关系， 组织也是一种抽象表示，可以代表单位、部门、岗位、团队等。
 * 如一项任务可能派给具体的人也可以派给具体的某个部门或岗位。
 * </p>
 * 
 * @author dragon
 */
public interface Actor extends Entity<Long> {
	/** 类型:未定义 */
	public static final int TYPE_UNDEFINED = 0;
	/** 类型:用户 */
	public static final int TYPE_USER = 1;
	/** 类型:单位 */
	public static final int TYPE_UNIT = 2;
	/** 类型:部门 */
	public static final int TYPE_DEPARTMENT = 3;
	/** 类型:岗位或团队 */
	public static final int TYPE_GROUP = 4;

	/**
	 * @return 编码
	 */
	String getCode();

	/**
	 * 设置编码
	 * 
	 * @param code
	 */
	void setCode(String code);

	/**
	 * @return 名称
	 */
	String getName();

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * @return 类型
	 */
	int getType();

	/**
	 * 设置类型
	 * 
	 * @param type
	 */
	void setType(int type);

	/**
	 * @return 邮箱
	 */
	String getEmail();

	/**
	 * 设置邮箱
	 * 
	 * @param email
	 */
	void setEmail(String email);

	/**
	 * @return 联系电话
	 */
	String getPhone();

	/**
	 * 设置联系电话
	 * 
	 * @param phone
	 */
	void setPhone(String phone);
	
	/**
	 * @return 同类参与者之间的排序号
	 */
	String getOrder();
	
	/**
	 * 设置同类参与者之间的排序号
	 * @param order
	 */
	void setOrder(String order);

	/**
	 * @return 扩展属性
	 */
	ActorDetail getDetail();

	/**
	 * 设置扩展属性
	 * 
	 * @param detail
	 *            扩展属性
	 */
	void setDetail(ActorDetail detail);
}
