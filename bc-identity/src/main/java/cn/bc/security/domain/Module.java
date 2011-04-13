/**
 * 
 */
package cn.bc.security.domain;

import cn.bc.core.DefaultEntity;

/**
 * 模块
 * 
 * @author dragon
 */
public class Module extends DefaultEntity {
	/**模块类型为文件夹*/
	public static final int TYPE_FOLDER = 1;
	/**模块类型为内部链接*/
	public static final int TYPE_INNER_LINK = 2;
	/**模块类型为外部链接*/
	public static final int TYPE_OUTER_LINK = 3;

	private String label;//名称
	private String code;//编码
	private int type;//模块类型，TYPE_*定义的相关常数
	private String url;//模块地址
	private Module belong;//所隶属的模块
	private String options;//模块的初始化参数
	
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Module getBelong() {
		return belong;
	}
	public void setBelong(Module belong) {
		this.belong = belong;
	}
}
