package cn.bc.web.ui;

public interface Component extends Render {
	String getId();
	void setId(String id);
	
	String getAttr(String key);
	void setAttr(String key, String value);
	
	String getName();
	void setName(String name);
	
	String getClazz();
	void setClazz(String clazz);
	
	String getStyle();
	void setStyle(String style);
}
