package cn.bc.identity.dao.hibernate.jpa;

import java.util.List;

import org.springframework.util.Assert;

import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.AndCondition;
import cn.bc.core.query.condition.impl.EqualsCondition;
import cn.bc.core.query.condition.impl.OrderCondition;
import cn.bc.identity.dao.ActorRelationDao;
import cn.bc.identity.domain.ActorRelation;
import cn.bc.orm.hibernate.jpa.HibernateCrudJpaDao;

/**
 * 参与者Service接口的实现
 * 
 * @author dragon
 * 
 */
public class ActorRelationDaoImpl extends HibernateCrudJpaDao<ActorRelation>
		implements ActorRelationDao {
	public List<ActorRelation> findByMaster(Integer type, Long masterId) {
		Assert.notNull(type);
		Assert.notNull(masterId);
		AndCondition condition = new AndCondition();
		condition.add(new EqualsCondition("type", type));
		condition.add(new EqualsCondition("master.id", masterId));
		condition.add(new OrderCondition("order", Direction.Asc));
		return this.createQuery().condition(condition).list();
	}

	public ActorRelation load(Integer type, Long masterId, Long followerId) {
		Assert.notNull(type);
		Assert.notNull(masterId);
		Assert.notNull(followerId);
		AndCondition condition = new AndCondition();
		condition.add(new EqualsCondition("type", type));
		condition.add(new EqualsCondition("master.id", masterId));
		condition.add(new EqualsCondition("follower.id", followerId));
		return this.createQuery().condition(condition).singleResult();
	}

	public List<ActorRelation> findByFollower(Integer type, Long followerId) {
		Assert.notNull(type);
		Assert.notNull(followerId);
		AndCondition condition = new AndCondition();
		condition.add(new EqualsCondition("type", type));
		condition.add(new EqualsCondition("follower.id", followerId));

		OrderCondition oc = new OrderCondition("master.order", Direction.Asc);
		oc.add("order", Direction.Asc);
		condition.add(oc);

		return this.createQuery().condition(condition).list();
	}
}
