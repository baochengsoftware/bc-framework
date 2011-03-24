package cn.bc.web.struts2;

import org.apache.struts2.StrutsSpringTestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import cn.bc.core.service.CrudService;
import cn.bc.test.Example;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;

public class StrutsCRUDActionTest extends StrutsSpringTestCase {
	private CrudService<Example> crudService;

	@Override
	protected String getContextLocations() {
		return "classpath:cn/bc/web/struts2/StrutsCRUDActionTest-context.xml";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.crudService = (CrudService<Example>) applicationContext
				.getBean("crudService");
		assertNotNull(this.crudService);

		// XmlConfigurationProvider c = new
		// XmlConfigurationProvider("struts.xml");
		// configurationManager.addConfigurationProvider(c);
		// configurationManager.reload();

		// this.loadConfigurationProviders((ConfigurationProvider[])
		// this.configurationManager
		// .getConfigurationProviders().toArray(
		// new ConfigurationProvider[0]));
	}

	// 测试Action配置文件是否正确
	public void testGetActionMapping() {
		ActionMapping mapping = getActionMapping("/example");
		assertNotNull(mapping);
		assertEquals("/", mapping.getNamespace());
		assertEquals("example", mapping.getName());
	}

	// 测试Action配置文件是否正确
	public void testGetActionProxy() throws Exception {
		request.setParameter("id", "111");
		//ActionProxy proxy = getActionProxy("/example.action");
		ActionProxy proxy = getActionProxy("/example!open");
		assertNotNull(proxy);

		@SuppressWarnings("unchecked")
		StrutsCRUDAction<Example> action = (StrutsCRUDAction<Example>) proxy
				.getAction();
		assertNotNull(action);

//		proxy.execute();
//		String result = proxy.execute();
//		assertEquals(Action.SUCCESS, result);
//		assertEquals("111", action.getId());
	}

	// @SuppressWarnings("unchecked")
	// public void testOpenSuccess() throws Exception {
	// // 保存一条记录
	// Example entity = new Example("dragon");
	// crudService.save(entity);
	// Long id = entity.getId();
	// Assert.assertNotNull(id);
	// Assert.assertTrue(id > 0);
	//
	// request.setParameter("id", id.toString());
	//
	// ActionProxy proxy = getActionProxy("/example!open");
	// StrutsCRUDAction<Example> crudAction = (StrutsCRUDAction<Example>) proxy
	// .getAction();
	// String result = proxy.execute();
	//
	// assertTrue(crudAction.getFieldErrors().size() == 0);
	// assertEquals("success", result);
	//
	// // 验证
	// entity = crudAction.getEntity();
	// Assert.assertNotNull(entity);
	// Assert.assertEquals(id, entity.getId());
	// Assert.assertEquals("dragon", entity.getName());
	// Assert.assertEquals(true, crudAction.isReadOnly());
	// }
}
