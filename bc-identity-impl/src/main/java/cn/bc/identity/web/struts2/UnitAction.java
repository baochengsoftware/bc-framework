/**
 * 
 */
package cn.bc.identity.web.struts2;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.core.query.condition.Direction;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
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
@Controller("unitAction")
public class UnitAction extends AbstractActorAction {
	private static final long serialVersionUID = 1L;
	private ActorImpl belong;// 隶属的上级单位

	public ActorImpl getBelong() {
		return belong;
	}

	public void setBelong(ActorImpl belong) {
		this.belong = belong;
	}

	@Override
	protected String getEntityConfigName() {
		return "Unit";
	}

	@Override
	public String create() throws Exception {
		String r = super.create();
		this.getEntity().setType(Actor.TYPE_UNIT);
		return r;
	}

	protected Grid buildGrid(List<Actor> entities) {
		return super
				.buildGrid(entities)
				.addColumn(
						new TextColumn("status", getText("actor.status"), 40))
				.addColumn(
						new TextColumn("code", getText("actor.code"), 80)
								.setSortable(true).setDir(Direction.Asc))
				.addColumn(
						new TextColumn("name", getText("actor.name"))
								.setSortable(true))
				.addColumn(new TextColumn("phone", getText("actor.phone"), 120))
				.addColumn(new TextColumn("email", getText("actor.email"), 150));
	}

	protected Option buildListPageOption() {
		return super.buildListPageOption().setWidth(650).setMinWidth(500)
				.setMinHeight(300);
	}

	private String value;
	private String label;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String list() throws Exception {
		this.setEntities(this.getActorService().findAllUnit());
		this.setPage(buildListPage(this.getEntities()));
		return "list";
	}

	/**
	 * 选择单位信息
	 * @return
	 * @throws Exception
	 */
	public String select() throws Exception {
		this.setEntities(this.getActorService().findAllUnit());
		return "select";
	}
}
