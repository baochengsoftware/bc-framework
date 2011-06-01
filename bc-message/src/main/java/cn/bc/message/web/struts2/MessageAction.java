/**
 * 
 */
package cn.bc.message.web.struts2;

import java.util.List;

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
import cn.bc.message.domain.Message;
import cn.bc.message.service.MessageService;
import cn.bc.web.struts2.CrudAction;
import cn.bc.web.ui.html.grid.Column;
import cn.bc.web.ui.html.grid.GridData;
import cn.bc.web.ui.html.grid.TextColumn;
import cn.bc.web.ui.html.page.PageOption;
import cn.bc.web.ui.html.toolbar.Toolbar;

/**
 * 消息Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class MessageAction extends CrudAction<Long, Message> {
	private static final long serialVersionUID = 1L;
	private MessageService messageService;

	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	protected GridData buildGridData() {
		return super.buildGridData().setRowLabelExpression("title");
	}

	@Override
	protected Condition getCondition() {
		AndCondition condition = new AndCondition();
		condition.add(new OrderCondition("sendTime", Direction.Desc));
		return condition.add(this.getSearchCondition());
	}

	@Override
	protected String[] getSearchFields() {
		return new String[] { "title", "content" };
	}

	// 设置页面的尺寸
	@Override
	protected PageOption buildListPageOption() {
		return super.buildListPageOption().setWidth(620).setMinWidth(450)
				.setHeight(400).setMinHeight(200);
	}

	@Override
	protected Toolbar buildToolbar() {
		return super.buildToolbar();
	}

	@Override
	protected List<Column> buildGridColumns() {
		List<Column> columns = super.buildGridColumns();
		columns.add(new TextColumn("status", getText("label.status"), 40));
		columns.add(new TextColumn("sendTime", getText("message.sendTime"), 80)
				.setSortable(true).setDir(Direction.Asc));
		columns.add(new TextColumn("sender.name",
				getText("message.sender.name"), 80).setSortable(true));
		columns.add(new TextColumn("title", getText("message.title"))
				.setSortable(true));
		return columns;
	}
}
