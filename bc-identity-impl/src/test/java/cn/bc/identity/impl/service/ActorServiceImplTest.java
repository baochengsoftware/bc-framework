package cn.bc.identity.impl.service;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.bc.core.Entity;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorDetail;
import cn.bc.identity.domain.ActorDetailImpl;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.identity.service.ActorService;
import cn.bc.security.domain.Module;
import cn.bc.security.domain.Role;
import cn.bc.security.service.ModuleService;
import cn.bc.security.service.RoleService;
import cn.bc.test.AbstractEntityCrudTest;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:spring-test.xml")
public class ActorServiceImplTest extends AbstractEntityCrudTest<Long,Actor> {
	ActorService actorService;
	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
		this.crudOperations = actorService;//赋值基类的crud操作对象
	}
	RoleService roleService;
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	ModuleService moduleService;
	@Autowired
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	@Override
	protected Actor createInstance(String config) {
		ActorImpl actor = new ActorImpl();
		
		//补充一些必填域的设置
		actor.setType(Actor.TYPE_USER);
		actor.setInner(false);
		actor.setStatus(Entity.STATUS_DISABLED);
		actor.setUid(UUID.randomUUID().toString());
		actor.setCode("testCode");
		actor.setName("测试");
		
		return actor;
	}

	@Test
	public void testSaveWithDetail() {
		saveOneWithDetail();
	}
	
	private Actor saveOneWithDetail() {
		// 先插入一条数据
		Actor entity = this.createInstance(this.getDefaultConfig());
		ActorDetail detail = new ActorDetailImpl();
		Calendar now = Calendar.getInstance();
		now.set(Calendar.YEAR, 2010);
		now.set(Calendar.MONTH, 0);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.HOUR_OF_DAY, 20);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		detail.set("createDate", now);
		entity.setDetail(detail);

		Assert.assertNull(entity.getId());
		Assert.assertNull(entity.getDetail().getId());
		this.actorService.save(entity);
		Assert.assertNotNull(entity.getId());
		Assert.assertNotNull(entity.getDetail().getId());
		Assert.assertEquals(now, entity.getDetail().getCalendar("createDate"));
		Assert.assertEquals(now, ((ActorDetailImpl)entity.getDetail()).getCreateDate());
		return entity;
	}

	@Test
	public void testLoadWithDetail() {
		// 先插入一条数据
		Actor entity = saveOneWithDetail();
		Long id = entity.getId();
		Calendar now = entity.getDetail().getCalendar("createDate");

		//强制重新从数据库加载，如果直接使用load，因还在同一事务内，不会重新加载
		entity = this.actorService.forceLoad(id);
		
		Assert.assertNotNull(entity);
		Assert.assertNotNull(entity.getDetail());
		Assert.assertEquals(now, entity.getDetail().getCalendar("createDate"));
		Assert.assertEquals(now, ((ActorDetailImpl)entity.getDetail()).getCreateDate());
	}
	
	private Actor saveOneWithRoles() {
		Actor actor = this.createInstance(this.getDefaultConfig());
		
		//添加两个角色
		Role role = createRole();
		role.setCode("test1");
		this.roleService.save(role);
		Assert.assertNotNull(role.getId());
		Set<Role> roles = new LinkedHashSet<Role>();//使用有序的Set
		roles.add(role);
		Long rid = role.getId();//记录第一个角色的id
		role = createRole();
		role.setCode("test2");
		this.roleService.save(role);
		roles.add(role);
		Assert.assertNotNull(role.getId());
		actor.setRoles(roles);

		this.actorService.save(actor);
		Assert.assertNotNull(actor.getId());
		Assert.assertNotNull(actor.getRoles());
		Assert.assertEquals(2, actor.getRoles().size());
		Assert.assertEquals(rid, actor.getRoles().iterator().next().getId());
		return actor;
	}
	
	private Role createRole() {
		Role role = new Role();
		role.setType(Role.TYPE_DEFAULT);
		role.setStatus(Entity.STATUS_ENABLED);
		role.setInner(false);
		role.setCode("test"); 
		role.setName(role.getCode());
		return role;
	}

	@Test
	public void testSaveWithRoles() {
		saveOneWithRoles();
	}

	@Test
	public void testLoadWithRoles() {
		// 插入2条数据
		Actor actor = saveOneWithRoles();
		Long id = actor.getId();
		Long mid = actor.getRoles().iterator().next().getId();

		//强制重新从数据库加载，如果直接使用load，因还在同一事务内，不会重新加载
		actor = this.actorService.load(id);//TODO .forceLoad(id)
		
		Assert.assertNotNull(actor);
		Assert.assertNotNull(actor.getRoles());
		Assert.assertEquals(2, actor.getRoles().size());
		Assert.assertEquals(mid, actor.getRoles().iterator().next().getId());
	}

	private Actor saveOneWithRolesAndModule() {
		Actor actor = this.createInstance(this.getDefaultConfig());
		
		//添加1个角色
		Role role = createRole();
		role.setCode("code1");
		this.roleService.save(role);
		Assert.assertNotNull(role.getId());
		Set<Role> roles = new LinkedHashSet<Role>();//使用有序的Set
		roles.add(role);
		Long rid = role.getId();//记录角色的id
		roles.add(role);
		Assert.assertNotNull(role.getId());
		//为角色添加1个模块
		Module module = createModule();
		this.moduleService.save(module);
		Assert.assertNotNull(module.getId());
		Set<Module> ms = new LinkedHashSet<Module>();//使用有序的Set
		ms.add(module);
		Long mid = module.getId();
		ms.add(module);
		Assert.assertNotNull(module.getId());
		
		role.setModules(ms);
		actor.setRoles(roles);
		this.actorService.save(actor);
		
		Assert.assertNotNull(actor.getId());
		Assert.assertNotNull(actor.getRoles());
		role = actor.getRoles().iterator().next();
		Assert.assertEquals(1, actor.getRoles().size());
		Assert.assertEquals(rid, role.getId());
		
		Assert.assertNotNull(role.getModules());
		Assert.assertEquals(1, role.getModules().size());
		Assert.assertEquals(mid, role.getModules().iterator().next().getId());
		
		return actor;
	}
	private Module createModule() {
		Module module = new Module();
		module.setType(Module.TYPE_INNER_LINK);
		module.setStatus(Entity.STATUS_ENABLED);
		module.setInner(false);
		module.setCode("test"); 
		module.setName(module.getCode());
		return module;
	}
	
	@Test
	public void testSaveWithRolesAndModule() {
		saveOneWithRolesAndModule();
	}
}
