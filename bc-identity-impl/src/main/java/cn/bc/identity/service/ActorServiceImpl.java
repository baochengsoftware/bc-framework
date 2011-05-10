package cn.bc.identity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bc.core.service.DefaultCrudService;
import cn.bc.identity.dao.ActorDao;
import cn.bc.identity.domain.Actor;

/**
 * 参与者Service接口的实现
 * 
 * @author dragon
 * 
 */
public class ActorServiceImpl extends DefaultCrudService<Actor> implements
		ActorService {
	private ActorDao actorDao;

	@Autowired
	public void setActorDao(ActorDao actorDao) {
		this.actorDao = actorDao;
		this.setCrudDao(actorDao);
	}

	public List<Actor> findMaster(Long followerId, Integer[] relationTypes,
			Integer[] masterTypes) {
		return this.actorDao.findMaster(followerId, relationTypes, masterTypes);
	}

	public List<Actor> findFollower(Long masterId, Integer[] relationTypes,
			Integer[] followerTypes) {
		return this.actorDao.findFollower(masterId, relationTypes, followerTypes);
	}

	public List<Actor> findTopUnit() {
		return this.actorDao.findTopUnit();
	}

	public List<Actor> findLowerOrganization(Long higherOrganizationId,
			Integer... lowerOrganizationTypes) {
		return this.actorDao.findLowerOrganization(higherOrganizationId, lowerOrganizationTypes);
	}

	public List<Actor> findHigherOrganization(Long lowerOrganizationId,
			Integer... higherOrganizationTypes) {
		return this.actorDao.findHigherOrganization(lowerOrganizationId, higherOrganizationTypes);
	}

	public List<Actor> findUser(Long organizationId) {
		return this.actorDao.findUser(organizationId);
	}
}
