package cn.bc.web.ui.html.page;

import cn.bc.web.ui.json.Json;

public class Button extends Json {
	public Button(String label, String action) {
		this.put("text", label);
		this.put("action", action);
	}
	public Button(String label, String action, String click) {
		this.put("text", label);
		this.put("action", action);
		this.put("click", click);
	}
}
