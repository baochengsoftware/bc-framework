package cn.bc.web.ui.html.grid;

public interface Column {
	/**
	 * @return 列宽
	 */
	int getWidth();

	/**
	 * @return 显示的名称
	 */
	String getLabel();
	
	/**
	 * @return 表达式
	 */
	String getExpression();
}
