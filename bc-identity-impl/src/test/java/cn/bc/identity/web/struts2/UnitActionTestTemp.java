package cn.bc.identity.web.struts2;

import java.util.UUID;

import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;

public class UnitActionTestTemp extends AbstractActorActionTest {
	@Override
	protected String getEntityConfigName() {
		return "Unit";
	}

	public ActorImpl createEntity() {
		ActorImpl e = super.createEntity();
		e.setUid(UUID.randomUUID().toString());
		e.setType(Actor.TYPE_UNIT);
		e.setCode("code");
		e.setName("name");
		return e;
	}
}
