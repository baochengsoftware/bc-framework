/**
 * 
 */
package cn.bc.identity.web.struts2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import cn.bc.core.Entity;
import cn.bc.core.Page;
import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.AndCondition;
import cn.bc.core.query.condition.impl.EqualsCondition;
import cn.bc.core.query.condition.impl.MixCondition;
import cn.bc.core.query.condition.impl.OrderCondition;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.web.ui.html.grid.Column;
import cn.bc.web.ui.html.grid.Grid;
import cn.bc.web.ui.html.grid.GridData;
import cn.bc.web.ui.html.grid.GridHeader;
import cn.bc.web.ui.html.grid.IdColumn;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.ButtonOption;
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

	protected String getEntityConfigName() {
		return "Unit";
	}

	public String create() throws Exception {
		String r = super.create();
		this.getE().setType(Actor.TYPE_UNIT);
		return r;
	}

	// 复写非分页数据的获取
	protected List<Actor> findList() {
		return this
				.getCrudService()
				.createQuery()
				.condition(this.getMyCondition().add(this.getSearchCondition()))
				.list();
	}

	// 复写分页数据的获取
	protected Page<Actor> findPage() {
		return this
				.getCrudService()
				.createQuery()
				.condition(this.getMyCondition().add(this.getSearchCondition()))
				.page(this.getPage().getPageNo(), this.getPage().getPageSize());
	}

	// 设置页面的尺寸
	protected PageOption buildListPageOption() {
		return super.buildListPageOption().setWidth(620).setMinWidth(450)
				.setHeight(400).setMinHeight(200);
	}

	// 设置表格的列
	protected List<Column> buildGridColumns() {
		List<Column> columns = super.buildGridColumns();

		columns.add(new TextColumn("status", getText("actor.status"), 40));
		columns.add(new TextColumn("code", getText("actor.code"), 80)
				.setSortable(true).setDir(Direction.Asc));
		columns.add(new TextColumn("name", getText("actor.name"))
				.setSortable(true));
		columns.add(new TextColumn("phone", getText("actor.phone"), 120));
		columns.add(new TextColumn("email", getText("actor.email"), 150));

		return columns;
	}

	// 附加单位的查询条件
	private MixCondition getMyCondition() {
		AndCondition condition = new AndCondition();
		condition
				.add(new EqualsCondition("type", new Integer(Actor.TYPE_UNIT)));
		// condition.add(new EqualsCondition("status", new
		// Integer(Entity.STATUS_ENABLED)));
		condition.add(new OrderCondition("order", Direction.Asc).add("code",
				Direction.Asc));
		return condition;
	}

	// 查询条件中要匹配的域
	protected String[] getSearchFields() {
		return new String[] { "code", "name", "phone", "email" };
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
		ListPage listPage = new ListPage();
		PageOption option = new PageOption().setMinWidth(250).setMinHeight(200)
				.setModal(true);
		ButtonOption bo = new ButtonOption();
		bo.put("text", getText("label.ok"));
		bo.put("click", "bc.unitForm.afterSelected");
		option.addButton(bo);
		listPage.setOption(option.toString());

		Grid grid = new Grid();
		listPage.setGrid(grid);

		List<Column> columns = new ArrayList<Column>();
		columns.add(IdColumn.DEFAULT());
		columns.add(new TextColumn("code", getText("actor.code"), 80)
				.setSortable(true).setDir(Direction.Asc));
		columns.add(new TextColumn("name", getText("actor.name"))
				.setSortable(true));

		GridHeader header = new GridHeader();
		grid.setGridHeader(header);
		header.setColumns(columns);
		grid.setGridHeader(header);

		GridData data = new GridData();
		grid.setGridData(data);
		data.setData(this.getActorService().findAllUnit(Entity.STATUS_ENABLED));
		data.setColumns(columns);

		// name属性设为bean的名称
		grid.setName(getText(StringUtils.uncapitalize(getEntityConfigName())));
		// 单选及双击行编辑
		grid.setSingleSelect(true).setDblClickRow("bc.unitForm.afterSelected");

		return "page";
	}
}
