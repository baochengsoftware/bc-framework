package cn.bc.desktop.service;

import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.bc.core.Entity;
import cn.bc.desktop.domain.PersonalConfig;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.identity.service.ActorService;
import cn.bc.test.AbstractEntityCrudTest;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:spring-test.xml")
public class PersionConfigServiceImplTest extends
		AbstractEntityCrudTest<Long, PersonalConfig> {
	PersonalConfigService personalConfigService;
	ActorService actorService;

	@Autowired
	public void setPersonalConfigService(
			PersonalConfigService personalConfigService) {
		this.personalConfigService = personalConfigService;
		this.crudOperations = personalConfigService;// 赋值基类的crud操作对象
	}

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}

	@Override
	protected PersonalConfig createInstance(String config) {
		PersonalConfig c = super.createInstance(config);
		c.setFont("14");
		c.setTheme("base");
		return c;
	}

	protected PersonalConfig createPersonalConfig(Actor actor) {
		PersonalConfig c = this.createInstance(this.getDefaultConfig());
		c.setActor(actor);
		return c;
	}

	@Test
	public void testLoadGlobal() {
		// 清空测试现场
		deleteAll();

		// 通用的
		PersonalConfig personalConfig4common = this.createPersonalConfig(null);
		this.personalConfigService.save(personalConfig4common);
		Assert.assertNotNull(personalConfig4common.getId());

		// 反查
		PersonalConfig c = this.personalConfigService.loadGlobalConfig();
		Assert.assertEquals(personalConfig4common, c);
	}

	@Test
	public void testLoadByActor() {
		// 清空测试现场
		deleteAll();

		// user
		Actor user = this.createActor(Actor.TYPE_USER, "user1", "01");
		this.actorService.save(user);
		Assert.assertNotNull(user.getId());

		// 通用的
		PersonalConfig personalConfig4common = this.createPersonalConfig(null);
		this.personalConfigService.save(personalConfig4common);
		Assert.assertNotNull(personalConfig4common.getId());

		// 反查
		PersonalConfig c = this.personalConfigService.loadByActor(user.getId(),true);
		Assert.assertEquals(personalConfig4common, c);
		c = this.personalConfigService.loadByActor(user.getId());
		Assert.assertNull(c);

		// 专用的
		PersonalConfig personalConfig4user = this.createPersonalConfig(user);
		this.personalConfigService.save(personalConfig4user);
		Assert.assertNotNull(personalConfig4user.getId());

		// 反查
		c = this.personalConfigService.loadByActor(user.getId());
		Assert.assertEquals(personalConfig4user, c);
		c = this.personalConfigService.loadByActor(user.getId(),true);
		Assert.assertEquals(personalConfig4user, c);
	}

	@Test
	public void testLoadByActorError() {
		// 清空测试现场
		deleteAll();

		Assert.assertNull(this.personalConfigService.loadByActor(new Long(1)));
	}

	private Actor createActor(int type, String code, String order) {
		ActorImpl actor = new ActorImpl();
		actor.setType(type);
		actor.setInner(false);
		actor.setStatus(Entity.STATUS_ENABLED);
		actor.setUid(UUID.randomUUID().toString());
		actor.setCode(code);
		actor.setOrder(order);
		actor.setName("测试" + code);

		return actor;
	}
}
