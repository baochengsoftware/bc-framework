package cn.bc.web.ui.html.grid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 列的抽象实现
 * 
 * @author dragon
 * 
 */
public abstract class AbstractColumn implements Column {
	protected Log logger = LogFactory.getLog(getClass());
	private int width;
	private String label;
	private String expression;
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
}
