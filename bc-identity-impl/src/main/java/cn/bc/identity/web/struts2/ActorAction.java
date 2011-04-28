/**
 * 
 */
package cn.bc.identity.web.struts2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.identity.impl.domain.ActorImpl;
import cn.bc.identity.impl.service.ActorService;
import cn.bc.web.struts2.CrudAction;
import cn.bc.web.ui.html.grid.Grid;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.Option;

/**
 * 单位Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class ActorAction extends CrudAction<Long, ActorImpl> {
	private static final long serialVersionUID = 1L;
	private ActorService actorService;

	public ActorService getActorService() {
		return actorService;
	}

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
		this.setCrudService(actorService);
	}

	protected Grid buildGrid(List<ActorImpl> entities) {
		return super.buildGrid(entities)
				.addColumn(new TextColumn("code", "编码", 80))
				.addColumn(new TextColumn("name", "名称"))
				.addColumn(new TextColumn("email", "邮箱", 100));
	}

	protected Option buildListPageOption() {
		return super.buildListPageOption();
	}
}
