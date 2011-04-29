package cn.bc.web.ui.html.grid;

/**
 * id列
 * 
 * @author dragon
 * 
 */
public class IdColumn extends AbstractColumn {
	private boolean canCheckedAll;

	/**
	 * @param canCheckedAll
	 *            是否含全选和反选功能
	 */
	public IdColumn(boolean canCheckedAll) {
		this.canCheckedAll = canCheckedAll;
		this.setExpression("id");
	}

	public boolean isCanCheckedAll() {
		return canCheckedAll;
	}

	public void setCanCheckedAll(boolean canCheckedAll) {
		this.canCheckedAll = canCheckedAll;
	}
}
