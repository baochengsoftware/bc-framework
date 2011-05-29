package cn.bc.web.ui.html.grid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import cn.bc.web.ui.Component;
import cn.bc.web.ui.Render;
import cn.bc.web.ui.html.Div;
import cn.bc.web.ui.html.Span;
import cn.bc.web.ui.html.Table;
import cn.bc.web.ui.html.Tbody;
import cn.bc.web.ui.html.Td;
import cn.bc.web.ui.html.Text;
import cn.bc.web.ui.html.Tr;

public class Grid extends Div {
	private boolean singleSelect;// 行是否单选
	private String dblClickRow;// 双击行的处理事件
	private List<? extends Object> data;
	private List<Column> columns = new ArrayList<Column>();
	private GridFooter footer;// 底部的工具条
	private String toggleSelectTitle;// 全选反选的提示信息

	public Grid() {
		this.addClazz("bc-grid");
		// this.setAttr("cellspacing", "0");
	}

	public String getToggleSelectTitle() {
		return toggleSelectTitle;
	}

	public Grid setToggleSelectTitle(String toggleSelectTitle) {
		this.toggleSelectTitle = toggleSelectTitle;
		return this;
	}

	public boolean isSingleSelect() {
		return singleSelect;
	}

	public Grid setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
		return this;
	}

	public GridFooter getFooter() {
		return footer;
	}

	public Grid setFooter(GridFooter footer) {
		this.footer = footer;
		return this;
	}

	public String getDblClickRow() {
		return dblClickRow;
	}

	public Grid setDblClickRow(String dblClickRow) {
		this.dblClickRow = dblClickRow;
		return this;
	}

	public StringBuffer render(StringBuffer main) {
		// 特殊属性处理
		if (isSingleSelect())
			this.addClazz("singleSelect");
		else
			this.addClazz("multipleSelect");

		// 双击表格行的事件
		if (dblClickRow != null && dblClickRow.length() > 0)
			this.setAttr("data-dblclickrow", getDblClickRow());

		// 构建表格头
		buildThead();

		// 构建表格列表数据
		buildTbody();

		// 构建表格的底部工具条：分页条等按钮
		if (footer != null)
			this.addChild(this.footer);

		return super.render(main);
	}

	private void buildThead() {
		Component header = new Div().addClazz("ui-state-default header");
		this.addChild(header);
		Component left = new Div().addClazz("left");
		Component right = new Div().addClazz("right");
		header.addChild(left).addChild(right);

		// 左table
		Component leftTable = new Table().addClazz("table")
				.setAttr("cellspacing", "0").setAttr("cellpadding", "0");
		left.addChild(leftTable);
		leftTable
				.addChild(new Tr()
						.addClazz("ui-state-default row")
						.addChild(
								new Td().addClazz("id")
										.setTitle(this.getToggleSelectTitle())
										.addChild(
												new Span()
														.addClazz("ui-icon ui-icon-notice"))));

		// 右table
		Component rightTable = new Table().addClazz("table")
				.setAttr("cellspacing", "0").setAttr("cellpadding", "0");
		right.addChild(rightTable);
		Component tr = new Tr().addClazz("ui-state-default row");
		rightTable.addChild(tr);
		Component td;
		Column column;
		// table的总宽度（20为留给滚动条的余量）
		int totalWidth = 20;
		// 循环添加其余列（第一列为id列忽略）
		for (int i = 1; i < columns.size(); i++) {
			column = columns.get(i);
			td = new Td();
			tr.addChild(td);

			if (i == 1) {
				td.addClazz("first");// 首列样式
			} else if (i == columns.size() - 1) {
				td.addClazz("last");// 最后列样式
			} else {
				td.addClazz("middle");// 中间列样式
			}

			if (column.getWidth() > 0) {
				td.addStyle("width", column.getWidth() + "px");
				totalWidth += column.getWidth();
			}
			Component wrapper = new Div().addClazz("wrapper");// td内容的包装容器：相对定位的元素
			td.addChild(wrapper);
			wrapper.addChild(new Text(column.getLabel()));

			// 排序标记
			if (column.isSortable()) {
				td.addClazz("sortable");
				switch (column.getDir()) {
				case Asc:
					td.addClazz("current");// 标记当前列处于排序状态
					wrapper.addChild(new Span()
							.addClazz("sortableIcon ui-icon ui-icon-triangle-1-n"));// 正序
					break;
				case Desc:
					td.addClazz("current");// 标记当前列处于排序状态
					wrapper.addChild(new Span()
							.addClazz("sortableIcon ui-icon ui-icon-triangle-1-s"));// 逆序
					break;
				default:
					wrapper.addChild(new Span()
							.addClazz("sortableIcon ui-icon hide"));
					break;
				}
			}
		}
		rightTable.addStyle("width", totalWidth + "px");

		// 添加一列空列
		tr.addChild(EMPTY_TD);
	}

	/** 空的占位单元格 */
	public static Render EMPTY_TD = new Text("<td class=\"empty\">&nbsp;</td>");

	private void buildTbody() {
		Tbody tbody = new Tbody();
		// this.addChild(tbody);
		Tr tr;
		int rc = 0;
		for (Object obj : data) {// 每行
			tr = new Tr();
			tbody.addChild(tr);
			tr.addClazz("ui-state-default row");
			if (rc / 2 == 0) {
				tr.addClazz("odd");// 奇数行
			} else {
				tr.addClazz("even");// 偶数行
			}
			Td td;
			int i = 0;
			for (Column column : columns) {// 每列
				td = new Td();
				tr.addChild(td);

				if (i == 0) {
					td.addClazz("first");// 首列样式
				} else if (i == columns.size() - 1) {
					td.addClazz("last");// 最后列样式
				} else {
					td.addClazz("middle");// 中间列样式
				}

				if (column instanceof IdColumn) {
					td.addClazz("id");// id列样式
					td.setAttr("data-id", getValue(obj, column.getExpression()));// id的值
					if (((IdColumn) column).getNameExpression() != null)
						td.setAttr(
								"data-name",
								this.getName()
										+ " - "
										+ getValue(obj, ((IdColumn) column)
												.getNameExpression()));// 标题的值
					td.addChild(new Span().addClazz("ui-icon"));// 勾选标记符
					td.addChild(new Text(String.valueOf(rc + 1)));// 行号
				} else {
					// 列宽已在thead中设置，这里不再设置

					// 内容
					td.addChild(new Text(getValue(obj, column.getExpression())));
				}

				i++;
			}
			rc++;
		}
	}

	private ExpressionParser parser;

	public Grid setExpressionParser(ExpressionParser parser) {
		this.parser = parser;
		return this;
	}

	public ExpressionParser getParser() {
		return parser;
	}

	public void setParser(ExpressionParser parser) {
		this.parser = parser;
	}

	/**
	 * 获取对象指定表达式的计算值的字符串表示
	 * 
	 * @param obj
	 *            对象
	 * @param expression
	 *            表达式
	 * @return
	 */
	protected String getValue(Object obj, String expression) {
		ExpressionParser parser = (getParser() != null ? getParser()
				: new SpelExpressionParser());
		Expression exp = parser.parseExpression(expression);
		EvaluationContext context = new StandardEvaluationContext(obj);
		Object o = exp.getValue(context);
		return o != null ? String.valueOf(o) : "";
	}

	public Grid addColumn(Column column) {
		if (column instanceof IdColumn)
			columns.add(0, column);// 保证id列在前
		else
			columns.add(column);
		return this;
	}

	public Grid setData(List<? extends Object> data) {
		this.data = data;
		return this;
	}
}
