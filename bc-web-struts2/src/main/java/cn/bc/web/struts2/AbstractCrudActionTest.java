package cn.bc.web.struts2;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsSpringTestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.junit.Assert;
import org.springframework.util.StringUtils;

import cn.bc.core.Entity;
import cn.bc.core.service.CrudService;

import com.opensymphony.xwork2.ActionProxy;

/**
 * CrudAction实现类的测试基类,测试相关的CURD操作
 * 
 * @author dragon
 * 
 * @param <K>
 *            主键类型
 * @param <E>
 *            实体类型
 */
public abstract class AbstractCrudActionTest<K extends Serializable, E extends Entity<K>>
		extends StrutsSpringTestCase {
	private static Log logger = LogFactory.getLog(AbstractCrudActionTest.class);
	private CrudService<E> crudService;
	private Class<E> entityClass;

	public abstract CrudService<E> createCrudService();

	public abstract E createEntity();

	@SuppressWarnings("unchecked")
	public AbstractCrudActionTest() {
		// 这个需要子类中指定T为实际的类才有效(指定接口也不行的)
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getActualTypeArguments()[1];
			if (type instanceof Class) {
				this.entityClass = (Class<E>) type;
				if (logger.isInfoEnabled())
					logger.info("auto judge entityClass to '"
							+ this.entityClass + "' [" + this.getClass() + "]");
			}
		}
	}

	public CrudService<E> getCrudService() {
		return crudService;
	}

	@Override
	protected String getContextLocations() {
		return "classpath:spring-test.xml";
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.crudService = createCrudService();
		assertNotNull(this.crudService);
		// System.out.println("****"+applicationContext.getBean("actorAction"));
	}

	protected String getServiceBeanName() {
		return StringUtils.uncapitalize(getEntityName()) + "Service";
	}

	protected String getEntityName() {
		return this.getEntityClass().getSimpleName();
	}

	protected Class<E> getEntityClass() {
		return this.entityClass;
	}

	protected CrudAction<K, E> getProxyAction(ActionProxy proxy) {
		@SuppressWarnings("unchecked")
		CrudAction<K, E> action = (CrudAction<K, E>) proxy.getAction();
		action.setCrudService(this.getCrudService());// 将Action注入的实际Action修改为内存型的
		return action;
	}

	public String getNamespace() {
		return "/" + StringUtils.uncapitalize(getEntityName());
	}

	// edit
	public void testEdit() throws Exception {
		// 保存一个对象
		E e = createEntity();
		this.getCrudService().save(e);
		K id = e.getId();

		// 在调用getActionProxy方法前设置请求的参数
		request.setParameter("id", id.toString());

		// 测试ActionProxy的配置
		ActionProxy proxy = getActionProxy(getNamespace() + "/edit");
		Assert.assertNotNull(proxy);
		Assert.assertEquals("edit", proxy.getMethod());

		// 测试action中的service是否通过spring注入了
		CrudAction<K, E> action = this.getProxyAction(proxy);
		Assert.assertNotNull(action);

		// 运行action并检验返回值
		String result = proxy.execute();
		Assert.assertEquals("form", result);

		// action执行后检验参数的值
		Assert.assertEquals(id, action.getId());
		Assert.assertNotNull(action.getEntity());
		Assert.assertEquals(id, action.getEntity().getId());
	}

	// save
	public void testSave() throws Exception {
		// 在调用getActionProxy方法前设置请求的参数
		String uid = UUID.randomUUID().toString();
		request.setParameter("e.type", "1");
		request.setParameter("e.uid", uid);
		
		// 测试ActionProxy的配置
		ActionProxy proxy = getActionProxy(getNamespace() + "/save");
		Assert.assertNotNull(proxy);
		Assert.assertEquals("save", proxy.getMethod());

		// 测试action中的service是否通过spring注入了
		CrudAction<K, E> action = this.getProxyAction(proxy);
		Assert.assertNotNull(action);
		Assert.assertNull(action.getEntity());
		Assert.assertNull(action.getE());

		// 运行action并检验返回值
		String result = proxy.execute();
		Assert.assertEquals("saveSuccess", result);

		// action执行后检验参数的值
		Assert.assertNotNull(action.getEntity());
		Assert.assertNotNull(action.getEntity().getId());
		Assert.assertEquals(uid,action.getEntity().getUid());
		Assert.assertNotNull(action.getE());
	}

	// create
	public void testCreate() throws Exception {
		// 测试ActionProxy的配置
		ActionProxy proxy = getActionProxy(getNamespace() + "/create");
		Assert.assertNotNull(proxy);
		Assert.assertEquals("create", proxy.getMethod());

		// 测试action中的service是否通过spring注入了
		CrudAction<K, E> action = this.getProxyAction(proxy);
		Assert.assertNotNull(action);

		// 运行action并检验返回值
		String result = proxy.execute();
		Assert.assertEquals("form", result);

		// action执行后检验参数的值
		Assert.assertNull(action.getId());
		Assert.assertNotNull(action.getEntity());
	}

	// delete
	public void testDelete() throws Exception {
		// 保存一个对象
		E e = createEntity();
		this.getCrudService().save(e);
		K id = e.getId();

		// 在调用getActionProxy方法前设置请求的参数
		request.setParameter("id", id.toString());
		
		// 测试ActionProxy的配置
		ActionProxy proxy = getActionProxy(getNamespace() + "/delete");
		Assert.assertNotNull(proxy);
		Assert.assertEquals("delete", proxy.getMethod());

		// 测试action中的service是否通过spring注入了
		CrudAction<K, E> action = this.getProxyAction(proxy);
		Assert.assertNotNull(action);

		// 运行action并检验返回值
		String result = proxy.execute();
		Assert.assertEquals("deleteSuccess", result);

		// 确认已被删除
		e = this.getCrudService().load(id);
		Assert.assertNull(e);
	}

	// list
	public void testList() throws Exception {
		// 保存一个对象
		E e = createEntity();
		this.getCrudService().save(e);

		// 测试ActionProxy的配置
		ActionProxy proxy = getActionProxy(getNamespace() + "/list");
		Assert.assertNotNull(proxy);
		Assert.assertEquals("list", proxy.getMethod());

		// 取得action
		CrudAction<K, E> action = this.getProxyAction(proxy);
		Assert.assertNotNull(action);

		// 运行action
		String result = proxy.execute();
		Assert.assertEquals("list", result);

		// 确认找到列表
		Assert.assertEquals(1,action.getEntities().size());
	}
}
