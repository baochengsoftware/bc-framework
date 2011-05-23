package cn.bc.web.ui.html.toolbar;

import cn.bc.web.ui.html.Span;

/**
 * 按钮分隔符
 * 
 * @author dragon
 * 
 */
public class SeparatorButton extends Span {
	protected void init() {
		this.addClazz("bc-separatorButton ui-icon ui-icon-grip-dotted-vertical");
	}
}
