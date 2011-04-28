package cn.bc.web.ui.html.page;

import cn.bc.web.ui.html.Div;

/**
 * 页面
 * 
 * @author dragon
 * 
 */
public class Page extends Div {
	public String getTitle() {
		return getAttr("title");
	}
	public Page setTitle(String title) {
		setAttr("title", title);
		return this;
	}
	public String getJs() {
		return getAttr("data-js");
	}
	public Page setJs(String js) {
		setAttr("data-js", js);
		return this;
	}
	public String getCss() {
		return getAttr("data-css");
	}
	public Page setCss(String css) {
		setAttr("data-css", css);
		return this;
	}
	public String getIniMethod() {
		return getAttr("data-iniMethod");
	}
	public Page setIniMethod(String iniMethod) {
		setAttr("data-iniMethod", iniMethod);
		return this;
	}
	public String getType() {
		return getAttr("data-type");
	}
	public Page setType(String type) {
		setAttr("data-type", type);
		return this;
	}
	public String getAction() {
		return getAttr("data-action");
	}
	public Page setAction(String action) {
		setAttr("data-action", action);
		return this;
	}
	public String getOption() {
		return getAttr("data-option");
	}
	public Page setOption(String option) {
		setAttr("data-option", option);
		return this;
	}
}
