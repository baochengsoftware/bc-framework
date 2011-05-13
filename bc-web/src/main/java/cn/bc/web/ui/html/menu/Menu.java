package cn.bc.web.ui.html.menu;

import java.util.ArrayList;
import java.util.List;

import cn.bc.web.ui.html.Ul;

public class Menu extends Ul {
	private List<MenuItem> menuItems;

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public Menu addMenuItem(MenuItem item) {
		if(this.menuItems == null)
			this.menuItems = new ArrayList<MenuItem>();
		this.menuItems.add(item);
		return this;
	}

	@Override
	public StringBuffer render(StringBuffer main) {
		this.children.clear();
		for (MenuItem item : menuItems)
			this.addChild(item);
		return super.render(main);
	}

}
