package cn.bc.security.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.bc.core.Entity;
import cn.bc.security.domain.Module;
import cn.bc.security.domain.Role;
import cn.bc.test.AbstractEntityCrudTest;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:spring-test.xml")
public class RoleServiceImplTest extends AbstractEntityCrudTest<Long,Role> {
	ModuleService moduleService;
	RoleService roleService;
	@Autowired
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
		this.crudOperations = roleService;//赋值基类的crud操作对象
	}

	@Override
	protected Role createInstance(String config) {
		Role entity = super.createInstance(config);
		entity.setUid(null);
		
		//补充一些额外的设置
		entity.setCode("code");
		entity.setName(entity.getCode());
		
		return entity;
	}

	@Test
	public void testSaveWithModules() {
		saveOneWithModules();
	}
	
	private Role saveOneWithModules() {
		Role role = this.createInstance(this.getDefaultConfig());
		
		Module module = createModule();
		this.moduleService.save(module);
		Assert.assertNotNull(module.getId());
		List<Module> ms = new ArrayList<Module>();
		ms.add(module);
		
		module = createModule();
		this.moduleService.save(module);
		ms.add(module);
		Assert.assertNotNull(module.getId());
		
		role.setModules(ms);

		this.roleService.save(role);
		Assert.assertNotNull(role.getId());
		Assert.assertNotNull(role.getModules());
		Assert.assertEquals(2, role.getModules().size());
		Assert.assertEquals(module.getId(), role.getModules().get(1).getId());
		return role;
	}
	
	private Module createModule() {
		Module module = new Module();
		module.setType(Module.TYPE_INNER_LINK);
		module.setStatus(Entity.STATUS_ENABLED);
		module.setInner(false);
		module.setCode("code"); 
		module.setName(module.getCode());
		return module;
	}

	@Test
	@Rollback(false)
	public void testLoadWithModules() {
		// 先插入一条数据
//		Role role = saveOneWithModules();
//		Long id = role.getId();
//		Long mid = role.getModules().get(0).getId();

		//强制重新从数据库加载，如果直接使用load，因还在同一事务内，不会重新加载
		Role role = this.roleService.load(new Long(466));//TODO .forceLoad(id)
		
		Assert.assertNotNull(role);
		Assert.assertNotNull(role.getModules());
		Assert.assertEquals(2, role.getModules().size());
		Assert.assertEquals(new Long(220), role.getModules().get(1).getId());
	}
}
