package cn.bc.web.ui.html;

import cn.bc.web.ui.AbstractComponent;

/**
 * li
 * 
 * @author dragon
 * 
 */
public class A extends AbstractComponent {
	public String getHref() {
		return getAttr("href");
	}
	public A setHref(String href) {
		setAttr("href", href);
		return this;
	}
	
	public String getTag() {
		return "a";
	}
}
