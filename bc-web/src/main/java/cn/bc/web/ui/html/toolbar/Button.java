package cn.bc.web.ui.html.toolbar;

import cn.bc.core.exception.CoreException;
import cn.bc.web.ui.AbstractComponent;
import cn.bc.web.ui.html.Span;
import cn.bc.web.ui.html.Text;
import cn.bc.web.ui.json.Json;

/**
 * 工具条按钮
 * 
 * @author dragon
 * 
 */
public class Button extends AbstractComponent {
	private String icon;// = "ui-icon-locked";
	private String text;
	private String action;
	private String click;
	private String title;
	
	public String getTag() {
		return "button";
	}

	protected void init() {
		// this.setAttr("href", "#");// 因使用了a元素，默认设置#避免ie6、ie7的无事件bug
		this.addClazz("bc-button ui-button ui-widget ui-state-default ui-corner-all");
	}

	public Button() {
		super();
	}

	public Button(String icon, String text) {
		this();
		this.icon = icon;
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public Button setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public Button setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public String getText() {
		return text;
	}

	public Button setText(String text) {
		this.text = text;
		return this;
	}

	public StringBuffer render(StringBuffer main) {
		// 按钮的提示信息
		this.setAttr("title", title != null ? title : null);

		// 样式控制
		if (icon != null) {
			if (text != null) {
				this.addClazz("ui-button-text-icon-primary");// 图标+文字
			} else {
				this.addClazz("ui-button-icon-only");// 仅图标
			}
		} else {
			if (text != null) {
				this.addClazz("ui-button-text-only");// 仅文字
			} else {
				throw new CoreException(
						"at least set property 'icon' or 'text'.");
			}
		}

		Json metadata = new Json();
		if (icon != null) {
			//metadata.put("icon", icon);
			this.addChild(new Span().addClazz("ui-button-icon-primary ui-icon "
					+ icon));
		}
		if (text != null) {
			//metadata.put("text", text);
			// this.addChild(new Text(text));
			this.addChild(new Span().addClazz("ui-button-text").addChild(
					new Text(text)));
		}
		if (click != null)
			metadata.put("click", click);
		if (action != null)
			metadata.put("action", action);

		this.addClazz(metadata.toString());

		return super.render(main);
	}
}
