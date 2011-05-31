/**
 * 
 */
package cn.bc.security.web.struts2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.core.query.condition.Condition;
import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.OrCondition;
import cn.bc.core.query.condition.impl.OrderCondition;
import cn.bc.security.domain.Role;
import cn.bc.security.service.RoleService;
import cn.bc.web.struts2.CrudAction;
import cn.bc.web.ui.html.grid.Column;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.PageOption;

/**
 * 角色Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class RoleAction extends CrudAction<Long, Role> {
	private static final long serialVersionUID = 1L;

	@Autowired
	public void setRoleService(RoleService moduleService) {
		this.setCrudService(moduleService);
	}

	public String create() throws Exception {
		String r = super.create();
		this.getE().setType(Role.TYPE_DEFAULT);
		return r;
	}

	// 附加查询条件
	protected Condition getCondition() {
		OrCondition or = this.getSearchCondition();
		OrderCondition order = new OrderCondition("code", Direction.Asc);
		return or == null ? order : or.add(order);
	}

	// 设置页面的尺寸
	protected PageOption buildListPageOption() {
		return super.buildListPageOption().setWidth(500).setHeight(400)
				.setMinWidth(300).setMinHeight(200);
	}

	// 设置表格的列
	protected List<Column> buildGridColumns() {
		List<Column> columns = super.buildGridColumns();

		columns.add(new TextColumn("code", getText("label.order"), 200)
				.setSortable(true).setDir(Direction.Asc));
		columns.add(new TextColumn("name", getText("label.name"))
				.setSortable(true));

		return columns;
	}

	// 查询条件中要匹配的域
	protected String[] getSearchFields() {
		return new String[] { "code", "name" };
	}
}
