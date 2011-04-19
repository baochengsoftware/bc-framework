package cn.bc.identity.impl.service;

import java.util.Calendar;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorDetail;
import cn.bc.identity.impl.domain.ActorDetailImpl;
import cn.bc.identity.impl.domain.ActorImpl;
import cn.bc.identity.service.ActorService;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:spring-test.xml")
public class ActorServiceImplTest {
	protected ActorService actorService;

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}

	// 先插入一条数据，并返回类型的值
	private String insertTestData(Long value, String format) {
		String uuid = UUID.randomUUID().toString();// 使用uuid避免与现有数据产生冲突
		Actor entity = new ActorImpl();
		entity.setUid(uuid);
		entity.setName("黄小明");
		entity.setCode("dragon");
		actorService.save(entity);
		return uuid;
	}

	@Test
	public void testLoad() {
//		Long id = new Long(1);
//		Actor entity = actorService.load(id);
//		Assert.assertNotNull(entity);
//		Assert.assertEquals(id, entity.getDetail().getId());
//		Assert.assertEquals("a", entity.getDetail().getString("firstName"));
	}

	@Test
	@Rollback(false)
	public void testSave() {
		// 先插入一条数据
		Actor entity = new ActorImpl();
		ActorDetail detail = new ActorDetailImpl();
		detail.set("createDate", Calendar.getInstance());
		entity.setDetail(detail);
		
		entity.setUid(UUID.randomUUID().toString());
		entity.setName("黄小明");
		entity.setCode("dragon");
		//entity.getDetail().set("createDate", Calendar.getInstance());

		Assert.assertNull(entity.getId());
		actorService.save(entity);
		Assert.assertNotNull(entity.getId());
	}
}
