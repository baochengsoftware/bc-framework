package cn.bc.web.ui.html.grid;

/**
 * 文本列
 * 
 * @author dragon
 * 
 */
public class TextColumn extends AbstractColumn {
	public TextColumn(String expression, String label) {
		this.setExpression(expression);
		this.setLabel(label);
	}

	public TextColumn(String expression, String label, int width) {
		this.setExpression(expression);
		this.setLabel(label);
		this.setWidth(width);
	}
}
