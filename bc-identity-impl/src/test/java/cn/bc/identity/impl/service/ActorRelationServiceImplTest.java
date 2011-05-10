package cn.bc.identity.impl.service;

import java.util.List;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.bc.core.Entity;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.identity.domain.ActorRelation;
import cn.bc.identity.domain.ActorRelationImpl;
import cn.bc.identity.domain.ActorRelationPK;
import cn.bc.identity.service.ActorRelationService;
import cn.bc.identity.service.ActorService;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:spring-test.xml")
public class ActorRelationServiceImplTest {
	ActorRelationService actorRelationService;
	ActorService actorService;

	@Autowired
	public void setActorRelationService(
			ActorRelationService actorRelationService) {
		this.actorRelationService = actorRelationService;
	}

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}

	@Test
	public void testLoad() {
		ActorRelation ar1 = saveOne();
		ActorRelation ar2 = actorRelationService.load(ar1.getType(), ar1
				.getMaster().getId(), ar1.getFollower().getId());
		Assert.assertNotNull(ar2);
		Assert.assertEquals(ar1, ar2);
	}

	@Test
	public void testLoadByPK() {
		ActorRelation ar = saveOne();
		Long mid = ar.getMaster().getId();
		Long fid = ar.getFollower().getId();
		Integer type = ar.getType();
		String order = ar.getOrder();

		ActorRelationPK id = new ActorRelationPK(ar.getMaster(),
				ar.getFollower(), ar.getType());
		ar = actorRelationService.load(id);
		Assert.assertNotNull(ar);
		Assert.assertEquals(mid, ar.getMaster().getId());
		Assert.assertEquals(fid, ar.getFollower().getId());
		Assert.assertEquals(type, ar.getType());
		Assert.assertEquals(order, ar.getOrder());
	}

	@Test(expected = NullPointerException.class)
	// 主键为空无法保存
	public void testSaveError() {
		Actor master = createActor(Actor.TYPE_UNIT, "mtest1");
		// actorService.save(master);

		Actor follower = createActor(Actor.TYPE_DEPARTMENT, "ftest1");
		// actorService.save(follower);

		ActorRelation ar = createInstance(master, follower,
				ActorRelation.TYPE_BELONG, "01");
		actorRelationService.save(ar);
	}

	@Test(expected = org.springframework.orm.jpa.JpaSystemException.class)
	// 游离实体无法保存
	// org.hibernate.PersistentObjectException: detached entity passed to
	// persist: cn.bc.identity.domain.ActorImpl
	public void testSaveErrorByDetachedEntity() {
		Actor master = createActor(Actor.TYPE_UNIT, "mtest1");
		actorService.save(master);
		Long id = master.getId();
		master = createActor(Actor.TYPE_UNIT, "mtest1");// 使master处于游离状态
		master.setId(id);

		Actor follower = createActor(Actor.TYPE_DEPARTMENT, "ftest1");
		actorService.save(follower);
		id = follower.getId();
		follower = createActor(Actor.TYPE_DEPARTMENT, "ftest1");// 使follower处于游离状态
		follower.setId(id);

		ActorRelation ar = createInstance(master, follower,
				ActorRelation.TYPE_BELONG, "01");
		actorRelationService.save(ar);
	}

	@Test
	public void testSave() {
		saveOne();
	}

	private ActorRelation saveOne() {
		Actor master = createActor(Actor.TYPE_UNIT, "mtest1");
		actorService.save(master);
		Assert.assertNotNull(master.getId());

		Actor follower = createActor(Actor.TYPE_DEPARTMENT, "ftest1");
		actorService.save(follower);
		Assert.assertNotNull(follower.getId());

		ActorRelation ar = createInstance(master, follower,
				ActorRelation.TYPE_BELONG, "01");
		actorRelationService.save(ar);
		return ar;
	}

	private ActorRelation createInstance(Actor master, Actor follower,
			Integer type, String order) {
		ActorRelation ar = new ActorRelationImpl();
		ar.setMaster(master);
		ar.setFollower(follower);
		ar.setType(type);
		ar.setOrder(order);
		return ar;
	}

	protected Actor createActor(int type, String code) {
		ActorImpl actor = new ActorImpl();
		actor.setType(type);
		actor.setInner(false);
		actor.setStatus(Entity.STATUS_ENABLED);
		actor.setUid(UUID.randomUUID().toString());
		actor.setCode(code);
		actor.setName("测试" + code);

		return actor;
	}

	@Test
	public void testFindByMaster() {
		// 保存一个ActorRelation
		ActorRelation ar1 = saveOne();

		// 保存另一个ActorRelation：follower重新创建，其他相同
		Actor follower2 = createActor(Actor.TYPE_DEPARTMENT, "ftest2");
		actorService.save(follower2);
		Assert.assertNotNull(follower2.getId());
		ActorRelation ar2 = createInstance(ar1.getMaster(), follower2,
				ar1.getType(), "02");
		actorRelationService.save(ar2);

		// 执行查询
		List<ActorRelation> ars = actorRelationService.findByMaster(
				ar1.getType(), ar1.getMaster().getId());

		// 验证
		Assert.assertNotNull(ars);
		Assert.assertEquals(2, ars.size());
		Assert.assertEquals(ar1, ars.get(0));
		Assert.assertEquals(ar2, ars.get(1));
	}

	@Test
	public void testFindByFollower() {
		// 保存一个ActorRelation
		ActorRelation ar1 = saveOne();

		// 保存另一个ActorRelation：master重新创建，其他相同
		Actor master2 = createActor(Actor.TYPE_DEPARTMENT, "mtest2");
		actorService.save(master2);
		Assert.assertNotNull(master2.getId());
		ActorRelation ar2 = createInstance(master2, ar1.getFollower(),
				ar1.getType(), "02");
		actorRelationService.save(ar2);

		// 执行查询
		List<ActorRelation> ars = actorRelationService.findByFollower(
				ar1.getType(), ar1.getFollower().getId());

		// 验证
		Assert.assertNotNull(ars);
		Assert.assertEquals(2, ars.size());
		Assert.assertEquals(ar1, ars.get(0));
		Assert.assertEquals(ar2, ars.get(1));
	}

	@Test
	public void testFindFollower() {
		// 单位
		Actor unit = this.createActor(Actor.TYPE_UNIT, "unit1");
		this.actorService.save(unit);
		Assert.assertNotNull(unit.getId());

		// 单位下的子单位1
		Actor cunit = this.createActor(Actor.TYPE_UNIT, "unit1-1");
		this.actorService.save(cunit);
		Assert.assertNotNull(cunit.getId());
		ActorRelation ar0 = createInstance(unit, cunit,
				ActorRelation.TYPE_BELONG, null);
		actorRelationService.save(ar0);

		// 单位下的部门1
		Actor dep1 = this.createActor(Actor.TYPE_DEPARTMENT, "dep-b");
		this.actorService.save(dep1);
		Assert.assertNotNull(dep1.getId());
		ActorRelation ar1 = createInstance(unit, dep1,
				ActorRelation.TYPE_BELONG, "02");
		actorRelationService.save(ar1);

		// 单位下的部门2
		Actor dep2 = this.createActor(Actor.TYPE_DEPARTMENT, "dep-a");
		this.actorService.save(dep2);
		Assert.assertNotNull(dep2.getId());
		ActorRelation ar2 = createInstance(unit, dep2,
				ActorRelation.TYPE_BELONG, "01");
		actorRelationService.save(ar2);

		// 部门1下的子部门1
		Actor cdep1 = this.createActor(Actor.TYPE_DEPARTMENT, "cdep1");
		this.actorService.save(cdep1);
		Assert.assertNotNull(cdep1.getId());
		ActorRelation ar3 = createInstance(dep1, cdep1,
				ActorRelation.TYPE_BELONG, "01");
		actorRelationService.save(ar3);

		// 反查单位1下的部门列表
		List<Actor> children = this.actorRelationService.findFollower(
				unit.getId(), new Integer[] { ActorRelation.TYPE_BELONG },
				new Integer[] { Actor.TYPE_DEPARTMENT });
		Assert.assertNotNull(children);
		Assert.assertEquals(2, children.size());
		Assert.assertEquals(dep2, children.get(0));
		Assert.assertEquals(dep1, children.get(1));

		// 反查单位1下的子单位列表
		children = this.actorRelationService.findFollower(unit.getId(),
				new Integer[] { ActorRelation.TYPE_BELONG },
				new Integer[] { Actor.TYPE_UNIT });
		Assert.assertNotNull(children);
		Assert.assertEquals(1, children.size());
		Assert.assertEquals(cunit, children.get(0));

		// 反查单位1下的子单位+部门列表
		children = this.actorRelationService.findFollower(unit.getId(),
				new Integer[] { ActorRelation.TYPE_BELONG }, new Integer[] {
						Actor.TYPE_UNIT, Actor.TYPE_DEPARTMENT });
		Assert.assertNotNull(children);
		Assert.assertEquals(3, children.size());
		Assert.assertEquals(cunit, children.get(0));
		Assert.assertEquals(dep2, children.get(1));
		Assert.assertEquals(dep1, children.get(2));

		// 反查部门1下的子部门列表
		children = this.actorRelationService.findFollower(dep1.getId(),
				new Integer[] { ActorRelation.TYPE_BELONG },
				new Integer[] { Actor.TYPE_DEPARTMENT });
		Assert.assertNotNull(children);
		Assert.assertEquals(1, children.size());
		Assert.assertEquals(cdep1, children.get(0));
	}

	@Test
	public void testFindMaster() {
		// 单位
		Actor unit = this.createActor(Actor.TYPE_UNIT, "unit1");
		this.actorService.save(unit);
		Assert.assertNotNull(unit.getId());

		// 单位下的子单位1
		Actor cunit = this.createActor(Actor.TYPE_UNIT, "unit1-1");
		this.actorService.save(cunit);
		Assert.assertNotNull(cunit.getId());
		ActorRelation ar0 = createInstance(unit, cunit,
				ActorRelation.TYPE_BELONG, null);
		actorRelationService.save(ar0);

		// 单位下的部门1
		Actor dep1 = this.createActor(Actor.TYPE_DEPARTMENT, "dep-b");
		this.actorService.save(dep1);
		Assert.assertNotNull(dep1.getId());
		ActorRelation ar1 = createInstance(unit, dep1,
				ActorRelation.TYPE_BELONG, "02");
		actorRelationService.save(ar1);

		// 单位下的部门2
		Actor dep2 = this.createActor(Actor.TYPE_DEPARTMENT, "dep-a");
		this.actorService.save(dep2);
		Assert.assertNotNull(dep2.getId());
		ActorRelation ar2 = createInstance(unit, dep2,
				ActorRelation.TYPE_BELONG, "01");// 隶属单位1
		actorRelationService.save(ar2);
		ActorRelation ar22 = createInstance(cunit, dep2,
				ActorRelation.TYPE_BELONG, null);// 同时隶属单位1下的子单位1: 矩阵式结构
		actorRelationService.save(ar22);

		// 部门1下的子部门1
		Actor cdep1 = this.createActor(Actor.TYPE_DEPARTMENT, "cdep1");
		this.actorService.save(cdep1);
		Assert.assertNotNull(cdep1.getId());
		ActorRelation ar3 = createInstance(dep1, cdep1,
				ActorRelation.TYPE_BELONG, "01");
		actorRelationService.save(ar3);

		// 反查子单位1的上级单位1
		List<Actor> parents = this.actorRelationService.findMaster(
				cunit.getId(), new Integer[] { ActorRelation.TYPE_BELONG },
				new Integer[] { Actor.TYPE_UNIT });
		Assert.assertNotNull(parents);
		Assert.assertEquals(1, parents.size());
		Assert.assertEquals(unit, parents.get(0));

		// 反查部门1的上级单位1
		parents = this.actorRelationService.findMaster(dep1.getId(),
				new Integer[] { ActorRelation.TYPE_BELONG },
				new Integer[] { Actor.TYPE_UNIT });
		Assert.assertNotNull(parents);
		Assert.assertEquals(1, parents.size());
		Assert.assertEquals(unit, parents.get(0));

		// 反查部门2的上级单位+部门
		parents = this.actorRelationService.findMaster(dep2.getId(),
				new Integer[] { ActorRelation.TYPE_BELONG }, new Integer[] {
						Actor.TYPE_UNIT, Actor.TYPE_DEPARTMENT });
		Assert.assertNotNull(parents);
		Assert.assertEquals(2, parents.size());
		Assert.assertEquals(unit, parents.get(0));
		Assert.assertEquals(cunit, parents.get(1));

		// 反查子部门1的上级部门
		parents = this.actorRelationService.findMaster(cdep1.getId(),
				new Integer[] { ActorRelation.TYPE_BELONG },
				new Integer[] { Actor.TYPE_DEPARTMENT });
		Assert.assertNotNull(parents);
		Assert.assertEquals(1, parents.size());
		Assert.assertEquals(dep1, parents.get(0));
	}
}
