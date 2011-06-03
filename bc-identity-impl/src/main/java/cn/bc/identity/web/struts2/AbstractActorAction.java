/**
 * 
 */
package cn.bc.identity.web.struts2;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorImpl;
import cn.bc.identity.service.ActorRelationService;
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
	private ActorRelationService actorRelationService;
	private ActorImpl belong;// 隶属的上级单位

	public ActorImpl getBelong() {
		return belong;
	}

	public void setBelong(ActorImpl belong) {
		this.belong = belong;
	}

	public ActorService getActorService() {
		return actorService;
	}

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
		this.setCrudService(actorService);
	}

	public ActorRelationService getActorRelationService() {
		return actorRelationService;
	}

	@Autowired
	public void setActorRelationService(
			ActorRelationService actorRelationService) {
		this.actorRelationService = actorRelationService;
	}

	protected Class<? extends Actor> getEntityClass() {
		return ActorImpl.class;
	}

	protected String getEntityConfigName() {
		return "Actor";
	}

	// 查询条件中要匹配的域
	protected String[] getSearchFields() {
		return new String[] { "code", "order", "name", "phone", "email" };
	}

	@Override
	public String save() throws Exception {
		this.getActorService().save4belong(this.getE(), belong);
		return "saveSuccess";
	}

	@Override
	public String edit() throws Exception {
		this.setE(this.getCrudService().load(this.getId()));

		// 加载上级信息
		this.setBelong((ActorImpl) this.getActorService().loadBelong(
				this.getId(), getBelongTypes()));

		return "form";
	}

	protected Integer[] getBelongTypes() {
		return null;
	};
}
