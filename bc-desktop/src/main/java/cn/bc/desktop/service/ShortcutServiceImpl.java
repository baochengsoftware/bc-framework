package cn.bc.desktop.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bc.core.query.condition.Direction;
import cn.bc.core.query.condition.impl.AndCondition;
import cn.bc.core.query.condition.impl.InCondition;
import cn.bc.core.query.condition.impl.OrderCondition;
import cn.bc.core.service.DefaultCrudService;
import cn.bc.desktop.domain.Shortcut;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.service.ActorService;
import cn.bc.security.domain.Module;
import cn.bc.security.domain.Role;

/**
 * 桌面快捷方式ervice接口的实现
 * 
 * @author dragon
 * 
 */
public class ShortcutServiceImpl extends DefaultCrudService<Shortcut> implements
		ShortcutService {
	// private static Log logger = LogFactory.getLog(ShortcutServiceImpl.class);
	private ActorService actorService;

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}

	public List<Shortcut> findByUser(Long userId) {
		if (userId == null)
			return null;
		Actor user = this.actorService.load(userId);

		// 获取actor隶属的所有上级组织，包括上级的上级，单位+部门+岗位
		List<Actor> parents = this.actorService.findAncestorOrganization(userId);

		// 汇总所有可以访问的模块列表
		Set<Module> modules = new LinkedHashSet<Module>();
		Set<Role> roles = new LinkedHashSet<Role>();
		for (Actor a : parents) {
			if (a.getRoles() != null)
				roles.addAll(a.getRoles());
		}
		if (user.getRoles() != null)
			roles.addAll(user.getRoles());
		for (Role r : roles) {
			if (r.getModules() != null)
				modules.addAll(r.getModules());
		}
		Collection<Long> mids = new ArrayList<Long>();
		for (Module m : modules) {
			mids.add(m.getId());
		}
		Collection<Long> aids = new ArrayList<Long>();
		for (Actor a : parents) {
			aids.add(a.getId());
		}

		if (mids.isEmpty() && aids.isEmpty())
			return null;

		AndCondition condition = new AndCondition();
		if (!mids.isEmpty())
			condition.add(new InCondition("module.id", mids));
		if (!aids.isEmpty())
			condition.add(new InCondition("actor.id", aids));

		condition.add(new OrderCondition("order", Direction.Asc));
		return this.createQuery().condition(condition).list();
	}
}
