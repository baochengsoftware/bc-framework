package cn.bc.web.ui.html.grid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.bc.core.query.condition.Direction;

/**
 * 列的抽象实现
 * 
 * @author dragon
 * 
 */
public abstract class AbstractColumn implements Column {
	protected Log logger = LogFactory.getLog(getClass());
	private int width = 100;//默认值
	private String id;
	private String label;
	private String expression;
	private boolean sortable;
	private Direction dir = Direction.None;
	
	public boolean isSortable() {
		return sortable;
	}
	public Column setSortable(boolean sortable) {
		this.sortable = sortable;
		return this;
	}
	public Direction getDir() {
		return dir;
	}
	public Column setDir(Direction dir) {
		this.dir = dir;
		return this;
	}
	public int getWidth() {
		return width;
	}
	public Column setWidth(int width) {
		this.width = width;
		return this;
	}
	public String getId() {
		return id;
	}
	public Column setId(String id) {
		this.id = id;
		return this;
	}
	public String getLabel() {
		return label;
	}
	public Column setLabel(String label) {
		this.label = label;
		return this;
	}
	public String getExpression() {
		return expression;
	}
	public Column setExpression(String expression) {
		this.expression = expression;
		return this;
	}
}
