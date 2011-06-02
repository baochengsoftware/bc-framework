/**
 * 
 */
package cn.bc.web;


/**
 * 日期类型值的格式化
 * 
 * @author dragon
 * 
 */
public class YesNoCnFormater implements Formater {
	public String format(Object value) {
		if (value == null)
			return null;
		if (value instanceof Boolean)
			return ((Boolean)value).booleanValue() ? "是" : "否";
		else if (value instanceof String)
			return "true".equalsIgnoreCase((String)value) ? "是" : "否";
		else
			return value.toString();
	}
}
