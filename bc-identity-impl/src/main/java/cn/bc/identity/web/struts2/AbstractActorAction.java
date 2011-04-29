/**
 * 
 */
package cn.bc.identity.web.struts2;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.identity.service.ActorService;
import cn.bc.web.struts2.CrudAction;

/**
 * 通用的Actor Action
 * 
 * @author dragon
 * 
 */
public abstract class AbstractActorAction extends CrudAction<Long, Actor> {
	private static final long serialVersionUID = 1L;
	private ActorService actorService;

	public ActorService getActorService() {
		return actorService;
	}

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
		this.setCrudService(actorService);
	}

	protected Class<? extends Actor> getEntityClass() {
		return ActorImpl.class;
	}
	protected String getEntityConfigName() {
		return "Actor";
	}
}
