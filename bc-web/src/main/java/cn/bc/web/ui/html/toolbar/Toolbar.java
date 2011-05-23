package cn.bc.web.ui.html.toolbar;

import cn.bc.web.ui.Render;
import cn.bc.web.ui.html.Div;

/**
 * 工具条
 * @author dragon
 *
 */
public class Toolbar extends Div {
	protected void init() {
		this.addClazz("bc-toolbar");
	}
	
	public String getTag() {
		return "div";
	}

	public Toolbar addButton(Render button) {
		this.addChild(button);
		return this;
	}
}
