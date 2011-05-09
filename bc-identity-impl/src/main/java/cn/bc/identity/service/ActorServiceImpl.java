package cn.bc.identity.service;

import java.util.List;

import cn.bc.core.service.DefaultCrudService;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.service.ActorService;

/**
 * 参与者Service接口的实现
 * 
 * @author dragon
 * 
 */
public class ActorServiceImpl extends DefaultCrudService<Actor> implements ActorService {

	public List<Actor> findParents() {
		// TODO Auto-generated method stub
		return null;
	}
}
