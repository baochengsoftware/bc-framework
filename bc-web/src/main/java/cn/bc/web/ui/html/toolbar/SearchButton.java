package cn.bc.web.ui.html.toolbar;

import cn.bc.web.ui.html.A;
import cn.bc.web.ui.html.Span;
import cn.bc.web.ui.html.TextInput;

/**
 * 搜索按钮
 * 
 * @author dragon
 * 
 */
public class SearchButton extends Span {
	protected void init() {
		this.addClazz("bc-searchButton");
	}

	public StringBuffer render(StringBuffer main) {
		// 清空环境
		if (this.children != null)
			this.children.clear();

		// 构建搜索按钮
		this.addChild(new A().setHref("#").addClazz("ui-icon ui-icon-search"));
		this.addChild(new TextInput());
		
		return super.render(main);
	}
}
