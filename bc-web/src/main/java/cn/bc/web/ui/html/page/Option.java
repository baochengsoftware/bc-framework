package cn.bc.web.ui.html.page;

import cn.bc.web.ui.json.Json;
import cn.bc.web.ui.json.JsonArray;

public class Option extends Json {

	public Option setButtons(Button... buttons) {
		JsonArray jsons = (JsonArray) get("buttons");
		if (jsons == null)
			jsons = new JsonArray();
		else
			jsons.clear();
		for (Button button : buttons)
			jsons.add(button);
		put("buttons", jsons);
		return this;
	}

	public Option addButton(Button button) {
		JsonArray jsons = (JsonArray) get("buttons");
		if (jsons == null)
			jsons = new JsonArray();
		jsons.add(button);
		put("buttons", jsons);
		return this;
	}

	public Option setMinWidth(int minWidth) {
		put("minWidth", minWidth);
		return this;
	}

	public Option setMinHeight(int minHeight) {
		put("minHeight", minHeight);
		return this;
	}

	public Option setHeight(int height) {
		put("height", height);
		return this;
	}

	public Option setWidth(int width) {
		put("width", width);
		return this;
	}

	public Option setModal(boolean modal) {
		put("modal", modal);
		return this;
	}
}
