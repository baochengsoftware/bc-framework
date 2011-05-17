/**
 * 
 */
package cn.bc.desktop.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bc.core.DefaultEntity;
import cn.bc.identity.domain.ActorImpl;

/**
 * 个人设置
 * 
 * @author dragon
 */
@Entity
@Table(name = "BC_DESKTOP_PERSONAL")
public class Personal extends DefaultEntity {
	private static final long serialVersionUID = 1L;

	private String font;// 字体大小
	private String theme;// 主题名称
	private ActorImpl actor;// 所属的参与者

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	//TODO struts2属性为接口时保存出错，无法实例化
	@ManyToOne(targetEntity = ActorImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "AID", nullable = true, updatable = false)
	public ActorImpl getActor() {
		return actor;
	}

	public void setActor(ActorImpl actor) {
		this.actor = actor;
	}
}
