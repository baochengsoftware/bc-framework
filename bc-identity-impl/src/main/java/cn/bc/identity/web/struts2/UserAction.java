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
import cn.bc.identity.domain.ActorImpl;
import cn.bc.web.ui.html.grid.Column;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.PageOption;

/**
 * 用户Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class UserAction extends AbstractActorAction {
	private static final long serialVersionUID = 1L;
	private List<ActorImpl> groups;// 所在的岗位

	public List<ActorImpl> getGroups() {
		return groups;
	}

	public void setGroups(List<ActorImpl> groups) {
		this.groups = groups;
	}

	protected String getEntityConfigName() {
		return "User";
	}

	public String create() throws Exception {
		String r = super.create();
		this.getE().setType(Actor.TYPE_USER);
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
		return super.buildListPageOption().setWidth(700).setMinWidth(450)
				.setHeight(500).setMinHeight(200);
	}

	// 设置表格的列
	protected List<Column> buildGridColumns() {
		List<Column> columns = super.buildGridColumns();

		columns.add(new TextColumn("status", getText("actor.status"), 40));
		columns.add(new TextColumn("order", getText("actor.order"), 80)
				.setSortable(true).setDir(Direction.Asc));
		columns.add(new TextColumn("code", getText("user.code"))
				.setSortable(true));
		columns.add(new TextColumn("name", getText("user.name"), 100)
				.setSortable(true));
		columns.add(new TextColumn("phone", getText("user.phone"), 100));
		columns.add(new TextColumn("email", getText("user.email"), 100));

		return columns;
	}

	// 附加用户查询条件
	private MixCondition getMyCondition() {
		AndCondition condition = new AndCondition();
		condition
				.add(new EqualsCondition("type", new Integer(Actor.TYPE_USER)));
		// condition.add(new EqualsCondition("status", new
		// Integer(Entity.STATUS_ENABLED)));
		condition.add(new OrderCondition("order", Direction.Asc).add("code",
				Direction.Asc));
		return condition;
	}

	protected Integer[] getBelongTypes() {
		return new Integer[] { Actor.TYPE_UNIT, Actor.TYPE_DEPARTMENT };
	}

	@Override
	public String save() throws Exception {
		this.getActorService().save4belong(this.getE(), this.getBelong());
		return "saveSuccess";
	}

	@Override
	public String edit() throws Exception {
		this.setE(this.getCrudService().load(this.getId()));

		// 加载上级信息
		this.setBelong((ActorImpl) this.getActorService().loadBelong(
				this.getId(), getBelongTypes()));

		// 加载岗位信息 TODO

		return "form";
	}
}
