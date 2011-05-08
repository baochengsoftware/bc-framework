/**
 * 
 */
package cn.bc.desktop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bc.core.DefaultEntity;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.security.domain.Module;

/**
 * 桌面快捷方式
 * 
 * @author dragon
 */
@Entity
@Table(name = "BC_DESKTOP_SHORTCUT")
public class Shortcut extends DefaultEntity {
	private static final long serialVersionUID = 1L;
	
	private int order;//排序号
	private String name;//名称
	private Module module;//对应的模块
	private Actor belong;//所属的参与者(如果为上级参与者,如单位部门,则其下的所有参与者都拥有该快捷方式)
	
	@Column(name = "ORDER_")
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MID", nullable=true, updatable=false)
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	
	@ManyToOne(targetEntity=ActorImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name="AID", nullable=true, updatable=false)
	public Actor getBelong() {
		return belong;
	}
	public void setBelong(Actor belong) {
		this.belong = belong;
	}
}
