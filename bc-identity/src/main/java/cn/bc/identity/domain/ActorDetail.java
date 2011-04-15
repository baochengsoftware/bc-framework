/**
 * 
 */
package cn.bc.identity.domain;

import java.util.Calendar;

/**
 * 参与者的扩展属性封装
 * @author dragon
 *
 */
public interface ActorDetail {
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
	 * @return 字符串属性的值
	 */
	String getString(String key);
	
	/**
	 * 设置字符串属性的值
	 * @param key 键
	 * @param value 值
	 */
	void set(String key, String value);
	
	/**
	 * @param key 键
	 * @return 布尔值
	 */
	boolean getBoolean(String key);
	
	/**
	 * 设置布尔值
	 * @param key 键
	 * @param value 值
	 */
	void set(String key, boolean value);
	
	/**
	 * @param key 键
	 * @return Integer值
	 */
	Integer getInteger(String key);
	
	/**
	 * 设置Integer值
	 * @param key 键
	 * @param value 值
	 */
	void set(String key, Integer value);
	
	/**
	 * @param key 键
	 * @return long值
	 */
	Long getLong(String key);
	
	/**
	 * 设置Long值
	 * @param key 键
	 * @param value 值
	 */
	void set(String key, Long value);
	
	/**
	 * @param key 键
	 * @return long值
	 */
	Float getFloat(String key);
	
	/**
	 * 设置Float值
	 * @param key 键
	 * @param value 值
	 */
	void set(String key, Float value);
	
	/**
	 * @param key 键
	 * @return Calendar值
	 */
	Calendar getCalendar(String key);
	
	/**
	 * 设置Calendar值
	 * @param key 键
	 * @param value 值
	 */
	void set(String key, Calendar value);
}
