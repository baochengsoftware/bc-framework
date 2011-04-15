/**
 * 
 */
package cn.bc.security.domain;

import java.util.List;

import cn.bc.core.DefaultEntity;
import cn.bc.identity.domain.Actor;

/**
 * 角色
 * 
 * @author dragon
 */
public class Role extends DefaultEntity {

	private String label;//名称
	private String code;//编码
	private Actor organizer;//所隶属的组织
	private int type;//类型，TYPE_*定义的相关常数
	private List<Module> modules;//可访问的模块列表
	
	public Actor getOrganizer() {
		return organizer;
	}
	public void setOrganizer(Actor organizer) {
		this.organizer = organizer;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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
