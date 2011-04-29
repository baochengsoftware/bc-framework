package cn.bc.identity.impl.service;

import java.util.Calendar;
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
}
