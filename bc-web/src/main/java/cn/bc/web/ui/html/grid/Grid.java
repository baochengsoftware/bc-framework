package cn.bc.web.ui.html.grid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import cn.bc.web.ui.html.Span;
import cn.bc.web.ui.html.Table;
import cn.bc.web.ui.html.Tbody;
import cn.bc.web.ui.html.Td;
import cn.bc.web.ui.html.Text;
import cn.bc.web.ui.html.Thead;
import cn.bc.web.ui.html.Tr;

public class Grid extends Table {
	private List<? extends Object> data;
	private List<Column> columns = new ArrayList<Column>();

	public Grid() {
		this.addClazz("list");
		this.setAttr("cellspacing", "0");

	}

	public StringBuffer render(StringBuffer main) {
		// 构建thead
		buildThead();

		// 构建tbody
		buildTbody();

		return super.render(main);
	}

	private void buildThead() {
		Thead thead = new Thead();
		this.addChild(thead);
		thead.addClazz("ui-widget-header");
		Tr tr = new Tr();
		thead.addChild(tr);
		tr.addClazz("row");
		Td td;
		int i = 0;
		for (Column column : columns) {
			td = new Td();
			tr.addChild(td);

			if (i == 0) {
				td.addClazz("first");// 首列样式
			} else if (i == columns.size() - 1) {
				td.addClazz("last");// 最后列样式
			} else {
				td.addClazz("middle");// 中间列样式
			}

			// id列样式
			if (column instanceof IdColumn) {
				td.addClazz("id");
				Span span = new Span();
				span.addClazz("ui-icon ui-icon-info");// 全选反选标记符号
				td.addChild(span);
			} else {
				if (column.getWidth() > 0) {
					td.addStyle("width", column.getWidth() + "px");
				}
				td.addChild(new Text(column.getLabel()));
			}

			i++;
		}
	}

	private void buildTbody() {
		Tbody tbody = new Tbody();
		this.addChild(tbody);
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
					td.addChild(new Span().addClazz("ui-icon"));// 勾选标记符
					td.addChild(new Text(String.valueOf(rc + 1)));// 行号
				} else {
					//列宽已在thead中设置，这里不再设置

					//内容
					td.addChild(new Text(getValue(obj,column.getExpression())));
				}

				i++;
			}
			rc++;
		}
	}
	
	private ExpressionParser parser;
	public Grid setExpressionParser(ExpressionParser parser){
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
	 * @param obj 对象
	 * @param expression 表达式
	 * @return
	 */
	protected String getValue(Object obj, String expression) {
		ExpressionParser parser = (getParser() != null ? getParser() : new SpelExpressionParser());
		Expression exp = parser.parseExpression(expression);
		EvaluationContext context = new StandardEvaluationContext(obj);
		return (String) exp.getValue(context);
	}

	public Grid addColumn(Column column) {
		columns.add(column);
		return this;
	}

	public Grid setData(List<? extends Object> data) {
		this.data = data;
		return this;
	}
}
