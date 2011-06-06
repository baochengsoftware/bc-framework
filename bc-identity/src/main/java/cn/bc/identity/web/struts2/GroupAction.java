/**
 * 
 */
package cn.bc.identity.web.struts2;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.core.query.condition.Condition;
import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.AndCondition;
import cn.bc.core.query.condition.impl.EqualsCondition;
import cn.bc.core.query.condition.impl.OrderCondition;
import cn.bc.identity.domain.Actor;
import cn.bc.web.ui.html.grid.Column;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.PageOption;

/**
 * 岗位Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class GroupAction extends AbstractActorAction {
	private static final long serialVersionUID = 1L;

	protected String getEntityConfigName() {
		return "Group";
	}

	public String create() throws Exception {
		String r = super.create();
		this.getE().setType(Actor.TYPE_GROUP);
		return r;
	}

	// 设置页面的尺寸
	protected PageOption buildListPageOption() {
		return super.buildListPageOption().setWidth(500).setMinWidth(450)
				.setHeight(400).setMinHeight(200);
	}

	// 设置表格的列
	protected List<Column> buildGridColumns() {
		List<Column> columns = super.buildGridColumns();

		columns.add(new TextColumn("status", getText("actor.status"), 40));
		columns.add(new TextColumn("order", getText("actor.order"), 80)
				.setSortable(true).setDir(Direction.Asc));
		columns.add(new TextColumn("code", getText("actor.code"), 80)
				.setSortable(true));
		columns.add(new TextColumn("name", getText("actor.name"))
				.setSortable(true));
		//columns.add(new TextColumn("phone", getText("actor.phone"), 120));
		//columns.add(new TextColumn("email", getText("actor.email"), 150));

		return columns;
	}

	// 附加岗位的查询条件
	protected Condition getCondition() {
		AndCondition condition = new AndCondition();
		condition
				.add(new EqualsCondition("type", new Integer(Actor.TYPE_GROUP)));
		// condition.add(new EqualsCondition("status", new
		// Integer(Entity.STATUS_ENABLED)));
		condition.add(new OrderCondition("order", Direction.Asc).add("code",
				Direction.Asc));
		return condition.add(this.getSearchCondition());
	}

	protected Integer[] getBelongTypes() {
		return new Integer[] { Actor.TYPE_UNIT, Actor.TYPE_DEPARTMENT };
	}
}
