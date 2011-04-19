package cn.bc.test;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gson.reflect.TypeToken;

public class TypeReferenceTest {
	@Test
	public void test() {
		TypeReference<Map<String, Example>> tr = new TypeReference<Map<String, Example>>() {};
		Assert.assertEquals("java.util.Map<java.lang.String, cn.bc.test.Example>", tr.getType().toString());
		Assert.assertEquals("java.util.Map", tr.getRawType().getName());
		
		//或者
		TypeToken<Map<String, Example>> tt = new TypeToken<Map<String, Example>>() {};
		Assert.assertEquals("java.util.Map<java.lang.String, cn.bc.test.Example>", tt.getType().toString());
		Assert.assertEquals("java.util.Map", tt.getRawType().getName());
	}
	
	//@Test
	public void test1() {
		String pkKey = "id";
		String sql = "INSERT INTO [SchemaName].[TableName] ([columnName1], [columnName2], ...) VALUES(?, ?, ...)";
		sql = sql.replaceFirst(" \\(", " (" + pkKey + ", ");
		sql = sql.replaceFirst(" VALUES\\(", " VALUES(" + "hibernate_sequence" + ".nextval, ");
		System.out.println(sql);
	}
}
