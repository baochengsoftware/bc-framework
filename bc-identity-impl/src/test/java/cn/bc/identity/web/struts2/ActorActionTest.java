package cn.bc.identity.web.struts2;

import java.util.UUID;

import cn.bc.core.service.CrudService;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.test.mock.CrudServiceMock;
import cn.bc.web.struts2.AbstractCrudActionTest;

public class ActorActionTest extends AbstractCrudActionTest<Long, ActorImpl> {

	@Override
	protected String getEntityConfigName() {
		return "Actor";
	}

	public ActorImpl createEntity() {
		ActorImpl e = new ActorImpl();
		e.setCode("code");
		e.setName("name");
		e.setUid(UUID.randomUUID().toString());
		return e;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CrudService<ActorImpl> createCrudService() {
		CrudServiceMock sm = new CrudServiceMock<ActorImpl>();//使用内存模拟避免写入数据库
		sm.setEntityClass(ActorImpl.class);
		return sm;
	}
}
