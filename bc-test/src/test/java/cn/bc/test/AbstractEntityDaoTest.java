package cn.bc.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cn.bc.core.Entity;
import cn.bc.core.Page;
import cn.bc.core.dao.CrudDao;
import cn.bc.core.jdbc.SimpleJdbcInsertEx;
import cn.bc.core.query.condition.impl.EqualsCondition;

@Transactional
// 基类也要声明这个
public abstract class AbstractEntityDaoTest<K extends Serializable,E extends Entity<K>>
		implements InitializingBean {
	protected CrudDao<E> entityDao;
	protected DataSource dataSource;
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	private SimpleJdbcInsert jdbcInsert;

	// ==dependency inject

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	public void setEntityDao(CrudDao<E> entityDao) {
		this.entityDao = entityDao;
	}

	public void afterPropertiesSet() throws Exception {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsertEx(dataSource,
				TestUtils.getDbSequence()).withTableName(this.getEntityTableName())
				.usingGeneratedKeyColumns(this.getEntityIdName());
	}

	// == inner getter

	protected SimpleJdbcInsert getJdbcInsert() {
		return jdbcInsert;
	}

	@Test
	@Rollback(true)
	public void save() {
		E entity = createEntity("test1");
		Assert.assertTrue(entity.isNew());
		entityDao.save(entity);
		Assert.assertTrue(!entity.isNew());

		// load
		entity = entityDao.load(entity.getId());
		Assert.assertNotNull(entity);

		// repeat save
		entityDao.save(entity);
	}

	@Test
	public void saveMul() {
		List<E> entities = new ArrayList<E>();
		entities.add(createEntity("test1"));
		entities.add(createEntity("test2"));
		entityDao.save(entities);
		Assert.assertTrue(!entities.get(0).isNew());
		Assert.assertTrue(!entities.get(1).isNew());
	}

	@Test
	public void delete() {
		K id1 = insertOne("name");
		entityDao.delete(id1);
		E entity = entityDao.load(id1);
		Assert.assertNull(entity);
	}

	@Test
	public void deleteNotExists() {
		K id1 = createID(Integer.MAX_VALUE);
		entityDao.delete(id1);
		E entity = entityDao.load(id1);
		Assert.assertNull(entity);
	}

	@Test
	public void deleteMul() {
		K id1 = insertOne("name");
		K id2 = insertOne("name1");

		entityDao.delete(createIDArray(id1, id2));
		E entity = entityDao.load(id1);
		Assert.assertNull(entity);
		entity = entityDao.load(id2);
		Assert.assertNull(entity);
	}

	@Test
	public void update() {
		K id1 = insertOne("newUID");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", "newUID9");
		entityDao.update(id1, map);
		E entity = entityDao.load(id1);
		Assert.assertNotNull(entity);
		Assert.assertEquals("newUID9", entity.getUid());
	}

	@Test
	public void updateMul() {
		K id1 = insertOne("newUID1");
		K id2 = insertOne("newUID2");

		// update
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", "newUID9");
		entityDao.update(createIDArray(id1, id2), map);

		E entity = entityDao.load(id1);
		Assert.assertNotNull(entity);
		Assert.assertEquals("newUID9", entity.getUid());

		entity = entityDao.load(id2);
		Assert.assertNotNull(entity);
		Assert.assertEquals("newUID9", entity.getUid());
	}

	@Test
	@Rollback(true)
	public void load() {
		K id1 = insertOne("newUID");
		E entity = entityDao.load(id1);
		Assert.assertNotNull(entity);
		Assert.assertEquals("newUID", entity.getUid());
	}

	@Test
	public void query_count() {
		// 插入0条
		cn.bc.core.query.Query<E> q = entityDao.createQuery();
		q.condition(new EqualsCondition("id", new Long(0)));
		Assert.assertNotNull(q);
		Assert.assertEquals(0, q.count());

		// 插入1条
		K id1 = insertOne("name0");
		Assert.assertNotNull(id1);
		q = entityDao.createQuery();
		q.condition(new EqualsCondition("id", id1));
		Assert.assertEquals(1, q.count());

		// 插入10条
		String uuid = UUID.randomUUID().toString();
		for (int i = 0; i < 10; i++)
			insertOne(uuid);
		q.condition(new EqualsCondition("name", uuid));
		Assert.assertEquals(10, q.count());
	}

	@Test
	public void query_singleResult() {
		K id1 = insertOne("uid");
		Assert.assertNotNull(id1);
		cn.bc.core.query.Query<E> q = entityDao.createQuery();
		q.condition(new EqualsCondition("id", id1));
		Assert.assertNotNull(q);
		E e = q.singleResult();
		Assert.assertNotNull(e);
		Assert.assertEquals(id1, e.getId());
		Assert.assertEquals("uid", e.getUid());
	}

	@Test
	public void query_list() {
		// 插入0条
		cn.bc.core.query.Query<E> q = entityDao.createQuery();
		q.condition(new EqualsCondition("id", new Long(0)));
		Assert.assertNotNull(q);
		List<E> list = q.list();
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() == 0);

		// 插入1条
		K id1 = insertOne("name0");
		Assert.assertNotNull(id1);
		q = entityDao.createQuery();
		q.condition(new EqualsCondition("id", id1));
		list = q.list();
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() == 1);

		// 插入10条
		String uuid = UUID.randomUUID().toString();
		for (int i = 0; i < 10; i++)
			insertOne(uuid);
		q.condition(new EqualsCondition("uid", uuid));
		list = q.list();
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() == 10);
	}

	@Test
	public void query_overList() {
		// 插入10条，然后查询超过这些数据范围的页
		String uuid = UUID.randomUUID().toString();
		for (int i = 0; i < 10; i++)
			insertOne(uuid);
		cn.bc.core.query.Query<E> q = entityDao.createQuery();
		q.condition(new EqualsCondition("uid", uuid));
		List<E> list = q.list(1, 10);
		Assert.assertNotNull(list);
		Assert.assertEquals(10, list.size());

		list = q.list(2, 10);
		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void query_page() {
		// 插入0条
		cn.bc.core.query.Query<E> q = entityDao.createQuery();
		q.condition(new EqualsCondition("id", new Long(0)));
		Assert.assertNotNull(q);
		Page<E> page = q.page(1, 100);
		Assert.assertNotNull(page);
		Assert.assertTrue(page.getList() == null || page.getList().isEmpty());
		Assert.assertEquals(1, page.getPageNo());
		Assert.assertEquals(100, page.getPageSize());
		Assert.assertEquals(0, page.getPageCount());
		Assert.assertEquals(0, page.getTotalCount());
		Assert.assertEquals(0, page.getFirstResult());

		// 插入1条
		K id1 = insertOne("name0");
		Assert.assertNotNull(id1);
		q = entityDao.createQuery();
		q.condition(new EqualsCondition("id", id1));
		page = q.page(1, 100);
		Assert.assertTrue(page.getList() != null && !page.getList().isEmpty());
		Assert.assertEquals(1, page.getPageNo());
		Assert.assertEquals(100, page.getPageSize());
		Assert.assertEquals(1, page.getPageCount());
		Assert.assertEquals(1, page.getTotalCount());
		Assert.assertEquals(0, page.getFirstResult());

		// 插入10条
		String uuid = UUID.randomUUID().toString();
		for (int i = 0; i < 10; i++)
			insertOne(uuid);
		q.condition(new EqualsCondition("name", uuid));
		page = q.page(1, 100);
		Assert.assertNotNull(page.getList());
		Assert.assertEquals(10, page.getList().size());
		Assert.assertEquals(1, page.getPageNo());
		Assert.assertEquals(100, page.getPageSize());
		Assert.assertEquals(1, page.getPageCount());
		Assert.assertEquals(10, page.getTotalCount());
		Assert.assertEquals(0, page.getFirstResult());

		// 第1页
		page = q.page(1, 5);
		Assert.assertEquals(5, page.getList().size());
		Assert.assertEquals(1, page.getPageNo());
		Assert.assertEquals(5, page.getPageSize());
		Assert.assertEquals(2, page.getPageCount());
		Assert.assertEquals(10, page.getTotalCount());
		Assert.assertEquals(0, page.getFirstResult());

		// 第2页
		page = q.page(2, 3);
		Assert.assertEquals(3, page.getList().size());
		Assert.assertEquals(2, page.getPageNo());
		Assert.assertEquals(3, page.getPageSize());
		Assert.assertEquals(4, page.getPageCount());
		Assert.assertEquals(10, page.getTotalCount());
		Assert.assertEquals(3, page.getFirstResult());
	}

	@Test
	public void query_overPage() {
		// 插入10条，然后查询超过这些数据范围的页
		cn.bc.core.query.Query<E> q = entityDao.createQuery();
		String uuid = UUID.randomUUID().toString();
		for (int i = 0; i < 10; i++)
			insertOne(uuid);
		q.condition(new EqualsCondition("name", uuid));
		Page<E> page = q.page(1, 5);
		Assert.assertNotNull(page.getList());
		Assert.assertEquals(5, page.getList().size());
		Assert.assertEquals(1, page.getPageNo());
		Assert.assertEquals(5, page.getPageSize());
		Assert.assertEquals(2, page.getPageCount());
		Assert.assertEquals(10, page.getTotalCount());
		Assert.assertEquals(0, page.getFirstResult());

		// 第1页
		page = q.page(3, 5);
		Assert.assertEquals(0, page.getList().size());
		Assert.assertEquals(3, page.getPageNo());
		Assert.assertEquals(5, page.getPageSize());
		Assert.assertEquals(2, page.getPageCount());
		Assert.assertEquals(10, page.getTotalCount());
		Assert.assertEquals(10, page.getFirstResult());
	}

	/**
	 * 向数据库中插入一条新数据
	 * 
	 * @return 返回主键的值
	 */
	protected abstract K insertOne(String uid);

	protected abstract E createEntity(String value);

	@SuppressWarnings("unchecked")
	protected K createID(int value){
		return (K)new Long(value);
	}
	
	@SuppressWarnings("unchecked")
	protected K[] createIDArray(K value1,K value2){
		return (K[])new Long[]{(Long)value1,(Long)value2};
	}

	/**
	 * @return 实体对象使用的数据库表名
	 */
	protected abstract String getEntityTableName();

	/**
	 * @return 实体对象使用的数据库主键列的名称
	 */
	protected String getEntityIdName() {
		return "id";
	}

}
