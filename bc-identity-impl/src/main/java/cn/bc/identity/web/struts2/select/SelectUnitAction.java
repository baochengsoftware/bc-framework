/**
 * 
 */
package cn.bc.identity.web.struts2.select;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.core.Entity;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.service.ActorService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 选择单位信息
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("selectUnitAction")
public class SelectUnitAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private List<Actor> es;
	private ActorService actorService;
	private long[] selected;// 当前选中项的id值，多个用逗号连接
	private long[] exclude;// 要排除可选择的项的id，多个用逗号连接
	private boolean multiple;// 是否可以多选

	public ActorService getActorService() {
		return actorService;
	}

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}

	public List<Actor> getEs() {
		return es;
	}

	public void setEs(List<Actor> es) {
		this.es = es;
	}

	public long[] getSelected() {
		return selected;
	}

	public void setSelected(long[] selected) {
		this.selected = selected;
	}

	public long[] getExclude() {
		return exclude;
	}

	public void setExclude(long[] exclude) {
		this.exclude = exclude;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String execute() throws Exception {
		//获取所有可用的单位信息
		this.es = this.actorService
				.findAllUnit(new Integer[] { Entity.STATUS_ENABLED });

		// 排除要不能选择的单位
		if (this.exclude != null && this.exclude.length > 0) {
			List<Actor> ex = new ArrayList<Actor>();
			for (Actor actor : this.es) {
				for (int i=0;i<this.exclude.length;i++) {
					if(actor.getId().longValue() == this.exclude[i]){
						ex.add(actor);
						break;
					}
				}
			}
			this.es.removeAll(ex);
		}

		return SUCCESS;
	}
}
