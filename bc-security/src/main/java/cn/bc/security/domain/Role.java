/**
 * 
 */
package cn.bc.security.domain;

import java.util.List;

import cn.bc.core.DefaultEntity;

/**
 * 角色
 * 
 * @author dragon
 */
public class Role extends DefaultEntity {
	/**类型：默认*/
	public static final int TYPE_DEFAULT = 0;

	private String name;//名称
	private String code;//编码，兼排序号的作用
	private int type;//类型，保留未用的字段
	private List<Module> modules;//可访问的模块列表
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
}
