/**
 * 
 */
package cn.bc.identity.web.struts2;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import cn.bc.core.Entity;
import cn.bc.core.query.condition.Direction;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.web.ui.html.grid.Grid;
import cn.bc.web.ui.html.grid.IdColumn;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.ListPage;
import cn.bc.web.ui.html.page.PageOption;

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
		this.getE().setType(Actor.TYPE_UNIT);
		return r;
	}

	protected Grid buildGrid() {
		return super
				.buildGrid()
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

	protected PageOption buildListPageOption() {
		return super.buildListPageOption().setWidth(450).setMinWidth(300)
				.setHeight(400).setMinHeight(200);
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

	/**
	 * 选择单位信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String select() throws Exception {
		Grid grid = new Grid().setData(this.getActorService().findAllUnit(
				Entity.STATUS_ENABLED));
		// name属性设为bean的名称
		grid.setName(getText(StringUtils.uncapitalize(getEntityConfigName())));
		// 单选及双击行编辑
		grid.setSingleSelect(true).setDblClickRow("bc.ui.selectUnit");

		grid.addColumn(IdColumn.DEFAULT())
				.addColumn(
						new TextColumn("code", getText("actor.code"), 80)
								.setSortable(true).setDir(Direction.Asc))
				.addColumn(
						new TextColumn("name", getText("actor.name"))
								.setSortable(true))
				.addColumn(new TextColumn("phone", getText("actor.phone"), 120))
				.addColumn(new TextColumn("email", getText("actor.email"), 150));

		ListPage listPage = new ListPage();
		PageOption option = new PageOption()
		.setMinWidth(250).setMinHeight(200).setModal(true);
		listPage.setGrid(grid)
				.setJs("/bc/identity/unit/select.js")
				.setCss("/bc/identity/unit/select.css")
				.setTitle(this.getText("title.select"))
				.setIniMethod("bc_unit_select_init")
				.setOption(option.toString())
				.addClazz("bc-page");
		this.setHtml(listPage);
		return "list";

		// this.setEntities(this.getActorService().findAllUnit());
		// return "select";
	}
}
