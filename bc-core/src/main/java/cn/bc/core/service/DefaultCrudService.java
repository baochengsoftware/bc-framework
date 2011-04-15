/**
 * 
 */
package cn.bc.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.bc.core.SetEntityClass;
import cn.bc.core.dao.CrudDao;
import cn.bc.core.query.Query;


/**
 * CrudService接口
 * 
 * @author dragon
 * 
 * @param <T>
 *            对象类型
 */
public class DefaultCrudService<T extends Object> implements CrudService<T>,
		SetEntityClass<T> {
	private static Log logger = LogFactory.getLog(DefaultCrudService.class);
	//protected final Log logger = LogFactory.getLog(getClass());
	private CrudDao<T> crudDao;
	protected Class<T> entityClass;

	public void setCrudDao(CrudDao<T> crudDao) {
		if (logger.isDebugEnabled())
			logger.debug("crudDao:" + crudDao);
		this.crudDao = crudDao;
	}

	public DefaultCrudService() {
	}

	public DefaultCrudService(Class<T> clazz) {
		this.entityClass = clazz;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	public void setEntityClass(Class<T> clazz) {
		this.entityClass = clazz;
		if (this.crudDao.getEntityClass() == null) {
			if (this.crudDao instanceof SetEntityClass) {
				if (logger.isDebugEnabled())
					logger.debug("reset crudDao's entityClass to '" + entityClass + "'");
				// 补充设置crudDao的entityClass
				((SetEntityClass<T>) this.crudDao).setEntityClass(entityClass);
			}
		}
	}

	public CrudDao<T> getCrudDao() {
		return this.crudDao;
	}

	public Query<T> createQuery() {
		return this.crudDao.createQuery();
	}

	public void delete(Serializable id) {
		this.crudDao.delete(id);
	}

	public void delete(Serializable[] ids) {
		this.crudDao.delete(ids);
	}

	public T load(Serializable id) {
		return this.crudDao.load(id);
	}

	public T create() {
		return this.crudDao.create();
	}

	public void save(T entity) {
		this.crudDao.save(entity);
	}

	public void save(Collection<T> entities) {
		this.crudDao.save(entities);
	}

	public void update(Serializable id, Map<String, Object> attributes) {
		this.crudDao.update(id, attributes);
	}

	public void update(Serializable[] ids, Map<String, Object> attributes) {
		this.crudDao.update(ids, attributes);
	}
}
