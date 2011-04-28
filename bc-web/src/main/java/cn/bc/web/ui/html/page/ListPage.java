package cn.bc.web.ui.html.page;

import cn.bc.web.ui.html.grid.Grid;


/**
 * 列表页面
 * 
 * @author dragon
 * 
 */
public class ListPage extends Page {
	public ListPage() {
		setType("list");
	}
	
	public String getDeleteAction() {
		return getAttr("data-action-delete");
	}
	public ListPage setDeleteAction(String action) {
		setAttr("data-action-delete", action);
		return this;
	}
	public String getEditAction() {
		return getAttr("data-action-edit");
	}
	public ListPage setEditAction(String action) {
		setAttr("data-action-edit", action);
		return this;
	}
	public String getCreateAction() {
		return getAttr("data-action-create");
	}
	public ListPage setCreateAction(String action) {
		setAttr("data-action-create", action);
		return this;
	}

	public ListPage setGrid(Grid grid) {
		this.addChild(grid);
		return this;
	}
}
