package cn.bc.web.ui.html.menu;

import cn.bc.web.ui.html.A;
import cn.bc.web.ui.html.Li;

public class MenuItem extends Li {
	private A a;
	private Menu childMenu;
	
	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}


	public Menu getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(Menu childMenu) {
		this.childMenu = childMenu;
	}

	@Override
	public StringBuffer render(StringBuffer main) {
		// TODO Auto-generated method stub
		return super.render(main);
	}

}
