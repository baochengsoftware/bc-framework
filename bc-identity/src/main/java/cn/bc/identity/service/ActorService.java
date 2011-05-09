package cn.bc.identity.service;

import java.util.List;

import cn.bc.core.service.CrudService;
import cn.bc.identity.domain.Actor;

/**
 * 参与者Service接口
 * @author dragon
 *
 */
public interface ActorService extends CrudService<Actor>{

	/**
	 * 获取actor隶属的所有上级组织，包括上级的上级
	 * @return
	 */
	List<Actor> findParents();

}
