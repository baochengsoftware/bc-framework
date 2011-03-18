package cn.bc.core.query.condition;

/** 排序方式 */
public enum Direction {
	/** 正向排序 */
	Asc,
	/** 逆向排序 */
	Desc;

	public String toSymbol() {
		switch (this) {
		case Asc:
			return "asc";
		case Desc:
			return "desc";
		default:
			return this.toString();
		}
	}
}
