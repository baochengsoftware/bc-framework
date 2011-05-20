package cn.bc.identity.web.struts2;

import java.util.UUID;

import cn.bc.core.service.CrudService;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.test.mock.CrudServiceMock;
import cn.bc.web.struts2.AbstractCrudActionTest;

public abstract class AbstractActorActionTest extends AbstractCrudActionTest<Long, ActorImpl> {
	@Override
	protected String getContextLocations() {
		return "classpath:spring-test4struts.xml";
	}

	protected Class<ActorImpl> getEntityClass() {
		return ActorImpl.class;
	}

	protected String getEntityConfigName() {
		return "Actor";
	}

	public ActorImpl createEntity() {
		ActorImpl e = new ActorImpl();
		e.setUid(UUID.randomUUID().toString());
		
		//设置一些必填域
		e.setCode("code");
		e.setName("name");
		
		return e;
	}

	public CrudService<ActorImpl> createCrudService() {
		CrudServiceMock<ActorImpl> sm = new CrudServiceMock<ActorImpl>();//使用内存模拟避免写入数据库
		sm.setEntityClass(ActorImpl.class);
		return sm;
	}
}
