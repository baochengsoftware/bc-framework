package cn.bc.core;

/**
 * 值名称的封装
 * 
 * @author dragon
 * 
 */
public class KeyValue {
	private String Label;//显示的名称
	private String value;//实际的值

	public KeyValue() {
	}

	public KeyValue(String Label, String value) {
		this();
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
