/**
 * 
 */
package cn.bc.identity.web.struts2;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.core.Page;
import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.AndCondition;
import cn.bc.core.query.condition.impl.EqualsCondition;
import cn.bc.core.query.condition.impl.MixCondition;
import cn.bc.core.query.condition.impl.OrderCondition;
import cn.bc.identity.domain.Actor;
import cn.bc.web.ui.html.grid.Column;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.PageOption;

/**
 * 单位Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class UnitAction extends AbstractActorAction {
	private static final long serialVersionUID = 1L;

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
		columns.add(new TextColumn("order", getText("actor.order"), 80)
				.setSortable(true).setDir(Direction.Asc));
		columns.add(new TextColumn("code", getText("actor.code"), 80)
				.setSortable(true));
		columns.add(new TextColumn("name", getText("actor.name"))
				.setSortable(true));
		columns.add(new TextColumn("phone", getText("actor.phone"), 120));
		// columns.add(new TextColumn("email", getText("actor.email"), 150));

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

	protected Integer[] getBelongTypes() {
		return new Integer[] { Actor.TYPE_UNIT };
	}
}
