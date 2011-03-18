package cn.bc.test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.bc.core.Page;
import cn.bc.core.query.condition.Condition;


/**
 * 基于内存查询接口实现，用于测试
 * 
 * @author dragon
 * 
 * @param <T>
 */
public class QueryMock<T extends Object> implements
		cn.bc.core.query.Query<T> {
	//private Condition condition;
	private Class<T> entityClass;
	private Map<String, Example> entities;
	
	@SuppressWarnings("unchecked")
	private QueryMock() {
		// 这个需要子类中指定T为实际的类才有效
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getActualTypeArguments()[0];
			if (type instanceof Class)
				this.entityClass = (Class<T>) type;
		}
		// System.out.println("T.class="+this.persistentClass);
	}

	/**
	 * 构造一个基于hibernate的Query实现
	 * 
	 * @param jpaTemplate
	 *            hibernate会话
	 */
	public QueryMock(Map<String, Example> entities) {
		this();
		this.entities = entities;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> persistentClass) {
		this.entityClass = persistentClass;
	}

	// --implements Query

	public QueryMock<T> condition(Condition condition) {
		//this.condition = condition;
		return this;
	}

	public int count() {
		return entities.size();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> list() {
		return new ArrayList(entities.values());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> list(final int pageNo, final int pageSize) {
		return new ArrayList(entities.values());
	}

	public Page<T> page(int pageNo, int pageSize) {
		return new Page<T>(pageNo, pageSize, this.count(), this.list(
				pageNo, pageSize));
	}

	@SuppressWarnings("unchecked")
	public T singleResult() {
		Collection<Example> c = entities.values();
		return c.iterator().hasNext() ? (T)c.iterator().next() : null;
	}
}
