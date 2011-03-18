package cn.bc.test;

import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.DigestUtils;

public class GsonTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws UnsupportedEncodingException {
		// Gson gson = new Gson();
		// Example e = new Example("dragon");
		// System.out.println(gson.toJson(e));
		//		
		// List<Example> list = new ArrayList<Example>();
		// list.add(e);
		// Page<Example> page = new Page<Example>(0,20,100,list);
		// System.out.println(gson.toJson(page,new TypeToken<Page<Example>>()
		// {}.getType()));
		//
		// gson = new GsonBuilder().serializeNulls().create();
		// System.out.println(gson.toJson(e));

		String passwordMD5 = "5f4dcc3b5aa765d61d8327deb882cf99";
		Assert.assertEquals(passwordMD5, DigestUtils
				.md5DigestAsHex("password".getBytes()));
		Assert.assertEquals(passwordMD5, DigestUtils.md5DigestAsHex("password"
				.getBytes("utf-8")));
		Assert.assertEquals(passwordMD5, DigestUtils.md5DigestAsHex("password"
				.getBytes("gbk")));
	}
}
