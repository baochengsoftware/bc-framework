package cn.bc.test;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cn.bc.core.SetEntityClass;
import cn.bc.core.dao.CrudDao;
import cn.bc.core.exception.CoreException;
import cn.bc.core.query.Query;


public class CrudDaoMock implements CrudDao<Example>, SetEntityClass<Example> {
	private long id = 0;
	private Map<String, Example> entities = new HashMap<String, Example>();
	private Class<Example> entityClass;

	public CrudDaoMock(){
//		Example e = new Example();
//		e.setId(new Long(1));
//		e.setName("name");
//		entities.put("1", e);
	}
	public Class<Example> getEntityClass() {
		return this.entityClass;
	}

	public void setEntityClass(Class<Example> clazz) {
		this.entityClass = clazz;
	}

	public Query<Example> createQuery() {
		return new QueryMock<Example>(this.entities);
	}

	public void delete(Serializable id) {
		if (id == null) {
			return;
		}

		if (entities.containsKey(id.toString())) {
			entities.remove(id.toString());
		}
	}

	public void delete(Serializable[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}

		for (Serializable id : ids) {
			this.delete(id);
		}
	}

	public Example load(Serializable id) {
		if (id == null) {
			return null;
		}

		String _id = id.toString();
		if (entities.containsKey(_id)) {
			return entities.get(_id);
		}
		return null;
	}

	public void save(Example entity) {
		if (entity == null) {
			return;
		}

		if (entity.getId() == null) {
			entity.setId(new Long(++id));
			entities.put(String.valueOf(entity.getId()), entity);
		} else {
			if (entities.containsKey(String.valueOf(entity.getId()))) {
				Example e = entities.get(String.valueOf(entity.getId()));
				e.setName(entity.getName());
				e.setCode(entity.getCode());
			}
		}
	}

	public void save(Collection<Example> entities) {
		if (entities == null || entities.isEmpty()) {
			return;
		}

		for (Example e : entities) {
			this.save(e);
		}
	}

	public void update(Serializable id, Map<String, Object> attributes) {
		if (id == null) {
			return;
		}

		String _id = id.toString();
		Example e;
		if (entities.containsKey(_id)) {
			e = entities.get(_id);
		} else {
			throw new CoreException("no entity exists with id '" + _id + "'");
		}
		for (String key : attributes.keySet()) {
			if ("name".equals(key))
				e.setName((String) attributes.get(key));
			else if ("code".equals(key))
				e.setCode((String) attributes.get(key));
		}
	}

	public void update(Serializable[] ids, Map<String, Object> attributes) {
		if (ids == null || ids.length == 0) {
			return;
		}

		for (Serializable id : ids) {
			this.update(id, attributes);
		}
	}
}
