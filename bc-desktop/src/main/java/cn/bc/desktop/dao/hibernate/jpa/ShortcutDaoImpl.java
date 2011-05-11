package cn.bc.desktop.dao.hibernate.jpa;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import cn.bc.desktop.dao.ShortcutDao;
import cn.bc.desktop.domain.Shortcut;
import cn.bc.identity.dao.ActorDao;
import cn.bc.identity.domain.Actor;
import cn.bc.orm.hibernate.jpa.HibernateCrudJpaDao;
import cn.bc.security.domain.Module;
import cn.bc.security.domain.Role;

/**
 * ShortcutService接口的实现
 * 
 * @author dragon
 * 
 */
public class ShortcutDaoImpl extends HibernateCrudJpaDao<Shortcut> implements
		ShortcutDao {
	private ActorDao actorDao;

	@Autowired
	public void setActorDao(ActorDao actorDao) {
		this.actorDao = actorDao;
	}

	@SuppressWarnings("unchecked")
	public List<Shortcut> findByActor(Long actorId, boolean includeAncestor,
			boolean includeCommon) {
		ArrayList<Object> args = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select s from Shortcut s");

		if (actorId == null) {
			hql.append(" where s.actor is null");
			hql.append(" order by s.order");
		} else {
			Actor actor = this.actorDao.load(actorId);
			if (includeAncestor) {
				List<Long> mids = new ArrayList<Long>();
				List<Long> aids = new ArrayList<Long>();

				// 获取actor隶属的所有上级组织，包括上级的上级，单位+部门+岗位
				List<Actor> parents = this.actorDao
						.findAncestorOrganization(actorId);

				// 汇总所有可以访问的模块列表
				Set<Module> modules = new LinkedHashSet<Module>();
				Set<Role> roles = new LinkedHashSet<Role>();
				for (Actor a : parents) {
					if (a.getRoles() != null)
						roles.addAll(a.getRoles());
				}
				if (actor.getRoles() != null)
					roles.addAll(actor.getRoles());
				for (Role r : roles) {
					if (r.getModules() != null)
						modules.addAll(r.getModules());
				}
				for (Module m : modules) {
					mids.add(m.getId());
				}
				for (Actor a : parents) {
					aids.add(a.getId());
				}
				aids.add(actorId);

				hql.append(" left join s.actor sa");
				if (mids != null && !mids.isEmpty())
					hql.append(" left join s.module sm");

				// actorIds
				if (aids.size() == 1) {
					hql.append(" where " + (includeCommon ? "(" : "")
							+ "sa.id=?");
					args.add(aids.get(0));
				} else {
					hql.append(" where " + (includeCommon ? "(" : "")
							+ "sa.id in (?");
					args.add(aids.get(0));
					for (int i = 1; i < aids.size(); i++) {
						hql.append(",?");
						args.add(aids.get(i));
					}
					hql.append(")");
				}

				// moduleIds
				if (mids != null && !mids.isEmpty()) {
					if (mids.size() == 1) {
						hql.append(" and sm.id=?");
						args.add(mids.get(0));
					} else {
						hql.append(" and sm.id in (?");
						args.add(mids.get(0));
						for (int i = 1; i < aids.size(); i++) {
							hql.append(",?");
							args.add(mids.get(i));
						}
						hql.append(")");
					}
				}
				if (includeCommon)
					hql.append(" or s.actor is null)");

				hql.append(" order by sa.order,s.order");
			} else {
				hql.append(" left join s.actor sa");
				hql.append(" where sa.id=?");
				args.add(actorId);
				if (includeCommon)
					// 不要使用sa is null：a.id is null
					hql.append(" or s.actor is null");
				hql.append(" order by sa.order,s.order");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("hql=" + hql.toString());
			logger.debug("args="
					+ StringUtils.collectionToCommaDelimitedString(args));
		}
		return this.getJpaTemplate().find(hql.toString(), args.toArray());
	}
}
