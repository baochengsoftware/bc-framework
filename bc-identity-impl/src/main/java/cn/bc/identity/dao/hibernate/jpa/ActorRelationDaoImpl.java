package cn.bc.identity.dao.hibernate.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.AndCondition;
import cn.bc.core.query.condition.impl.EqualsCondition;
import cn.bc.core.query.condition.impl.OrderCondition;
import cn.bc.identity.dao.ActorRelationDao;
import cn.bc.identity.domain.Actor;
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

	@SuppressWarnings("unchecked")//
	public List<Actor> findMaster(Long followerId, Integer[] relationTypes,
			Integer[] masterTypes) {
		if (followerId == null)
			return new ArrayList<Actor>();//TODO: 如顶层单位、底层叶子

		ArrayList<Object> args = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select m from ActorImpl m,ActorRelationImpl ar,ActorImpl f");
		hql.append(" where m.id=ar.master.id");
		hql.append(" and f.id=ar.follower.id and f.id=?");
		args.add(followerId);

		// 关联的类型，对应ActorRelation的type属性
		if (relationTypes != null && relationTypes.length > 0) {
			if (relationTypes.length == 1) {
				hql.append(" and ar.type=?");
				args.add(relationTypes[0]);
			} else {
				hql.append(" and ar.type in (?");
				args.add(relationTypes[0]);
				for (int i = 1; i < relationTypes.length; i++) {
					hql.append(",?");
					args.add(relationTypes[i]);
				}
				hql.append(")");
			}
		}

		// 主控方的类型，对应Actor的type属性
		if (masterTypes != null && masterTypes.length > 0) {
			if (masterTypes.length == 1) {
				hql.append(" and m.type=?");
				args.add(masterTypes[0]);
			} else {
				hql.append(" and m.type in (?");
				args.add(masterTypes[0]);
				for (int i = 1; i < masterTypes.length; i++) {
					hql.append(",?");
					args.add(masterTypes[i]);
				}
				hql.append(")");
			}
		}

		// 排序
		hql.append(" order by ar.type,m.type,m.code");
		if (logger.isDebugEnabled()) {
			logger.debug("hql=" + hql.toString());
			logger.debug("args="
					+ StringUtils.collectionToCommaDelimitedString(args));
		}
		return this.getJpaTemplate().find(hql.toString(), args.toArray());
	}

	@SuppressWarnings("unchecked")//
	public List<Actor> findFollower(Long masterId, Integer[] relationTypes,
			Integer[] followerTypes) {
		if (masterId == null)
			return new ArrayList<Actor>();

		ArrayList<Object> args = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select f from ActorImpl f,ActorRelationImpl ar,ActorImpl m");
		hql.append(" where f.id=ar.follower.id");
		hql.append(" and m.id=ar.master.id and m.id=?");
		args.add(masterId);

		// 关联的类型，对应ActorRelation的type属性
		if (relationTypes != null && relationTypes.length > 0) {
			if (relationTypes.length == 1) {
				hql.append(" and ar.type=?");
				args.add(relationTypes[0]);
			} else {
				hql.append(" and ar.type in (?");
				args.add(relationTypes[0]);
				for (int i = 1; i < relationTypes.length; i++) {
					hql.append(",?");
					args.add(relationTypes[i]);
				}
				hql.append(")");
			}
		}

		// 从属方的类型，对应Actor的type属性
		if (followerTypes != null && followerTypes.length > 0) {
			if (followerTypes.length == 1) {
				hql.append(" and f.type=?");
				args.add(followerTypes[0]);
			} else {
				hql.append(" and f.type in (?");
				args.add(followerTypes[0]);
				for (int i = 1; i < followerTypes.length; i++) {
					hql.append(",?");
					args.add(followerTypes[i]);
				}
				hql.append(")");
			}
		}

		// 排序
		hql.append(" order by ar.type,ar.order,f.type,f.code");
		if (logger.isDebugEnabled()) {
			logger.debug("hql=" + hql.toString());
			logger.debug("args="
					+ StringUtils.collectionToCommaDelimitedString(args));
		}
		return this.getJpaTemplate().find(hql.toString(), args.toArray());
	}
}
