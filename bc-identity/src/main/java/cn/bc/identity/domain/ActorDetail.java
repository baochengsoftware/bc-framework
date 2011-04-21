/**
 * 
 */
package cn.bc.identity.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 * 参与者扩展属性的抽象
 * 
 * @author dragon
 * 
 */
public interface ActorDetail extends Serializable{
	Long getId();

	void setId(Long id);
	
	/**
	 * @param key 键
	 * @return 指定键的属性值
	 */
	Object get(String key);
	
	/**
	 * 设置属性值
	 * @param key 键
	 * @param value 值
	 */
	void set(String key, Object value);
	
	/**
	 * @param key 键
	 * @return 字符串值
	 */
	String getString(String key);
	
	/**
	 * @param key 键
	 * @return 布尔值
	 */
	Boolean getBoolean(String key);
	
	/**
	 * @param key 键
	 * @return Integer值
	 */
	Integer getInteger(String key);
	
	/**
	 * @param key 键
	 * @return long值
	 */
	Long getLong(String key);
	
	/**
	 * @param key 键
	 * @return long值
	 */
	Float getFloat(String key);
	
	/**
	 * @param key 键
	 * @return Calendar值
	 */
	Calendar getCalendar(String key);
	
	/**
	 * @param key 键
	 * @return Calendar值
	 */
	Date getDate(String key);
}
