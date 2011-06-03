/**
 * 
 */
package cn.bc.identity.web.struts2;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.core.query.condition.Condition;
import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.AndCondition;
import cn.bc.core.query.condition.impl.EqualsCondition;
import cn.bc.core.query.condition.impl.OrderCondition;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.identity.service.UserService;
import cn.bc.web.ui.html.grid.Column;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.PageOption;
import cn.bc.web.ui.html.toolbar.Toolbar;
import cn.bc.web.ui.html.toolbar.ToolbarButton;

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
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
		this.setActorService(userService);
	}

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

	// 设置页面的尺寸
	protected PageOption buildListPageOption() {
		return super.buildListPageOption().setWidth(700).setMinWidth(450)
				.setHeight(500).setMinHeight(200);
	}

	@Override
	protected Toolbar buildToolbar() {
		return super.buildToolbar().addButton(
				new ToolbarButton().setIcon("ui-icon-document")
						.setText(getText("user.password.reset"))
						.setClick("bc.userList.setPassword"));
	}

	@Override
	protected String getJs() {
		String cp = ServletActionContext.getRequest().getContextPath();
		return cp + "/bc/identity/user/list.js";
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
	protected Condition getCondition() {
		AndCondition condition = new AndCondition();
		condition
				.add(new EqualsCondition("type", new Integer(Actor.TYPE_USER)));
		// condition.add(new EqualsCondition("status", new
		// Integer(Entity.STATUS_ENABLED)));
		condition.add(new OrderCondition("order", Direction.Asc).add("code",
				Direction.Asc));
		return condition.add(this.getSearchCondition());
	}

	protected Integer[] getBelongTypes() {
		return new Integer[] { Actor.TYPE_UNIT, Actor.TYPE_DEPARTMENT };
	}

	@Override
	public String save() throws Exception {
		this.userService.save4belong(this.getE(), this.getBelong());
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
