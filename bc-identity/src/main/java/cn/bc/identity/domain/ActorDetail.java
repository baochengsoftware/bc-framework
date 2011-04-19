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
	public abstract Long getId();

	public abstract void setId(Long id);
	
	/**
	 * @param key 键
	 * @return 指定键的属性值
	 */
	public abstract Object get(String key);
	
	/**
	 * 设置属性值
	 * @param key 键
	 * @param value 值
	 */
	public abstract void set(String key, Object value);
	
	/**
	 * @param key 键
	 * @return 字符串值
	 */
	public abstract String getString(String key);
	
	/**
	 * @param key 键
	 * @return 布尔值
	 */
	public abstract Boolean getBoolean(String key);
	
	/**
	 * @param key 键
	 * @return Integer值
	 */
	public abstract Integer getInteger(String key);
	
	/**
	 * @param key 键
	 * @return long值
	 */
	public abstract Long getLong(String key);
	
	/**
	 * @param key 键
	 * @return long值
	 */
	public abstract Float getFloat(String key);
	
	/**
	 * @param key 键
	 * @return Calendar值
	 */
	public abstract Calendar getCalendar(String key);
	
	/**
	 * @param key 键
	 * @return Calendar值
	 */
	public abstract Date getDate(String key);
}
