package cn.bc.identity.dao;

import java.util.List;

import cn.bc.core.dao.CrudDao;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.ActorRelation;

/**
 * 参与者Dao接口
 * @author dragon
 *
 */
public interface ActorRelationDao extends CrudDao<ActorRelation>{
	/**
	 * 获取某个关联关系
	 * @param type 关联类型,不能为空
	 * @param masterId 主控方Id,不能为空
	 * @param followerId 从属方Id,不能为空
	 * @return
	 */
	ActorRelation load(Integer type, Long masterId, Long followerId);
	
	/**
	 * 获取主控方某类型的关联关系列表
	 * @param type 关联类型,不能为空
	 * @param masterId 主控方Id,不能为空
	 * @return
	 */
	List<ActorRelation> findByMaster(Integer type, Long masterId);
	
	/**
	 * 获取主控方某类型的关联关系列表
	 * @param type 关联类型,不能为空
	 * @param followerId 从属方Id,不能为空
	 * @return
	 */
	List<ActorRelation> findByFollower(Integer type, Long followerId);

	/**
	 * 获取从属方指定关联关系的主控方
	 * @param followerId 从属方id
	 * @param relationTypes 关联的类型，对应ActorRelation的type属性
	 * @param masterTypes 主控方的类型，对应Actor的type属性
	 * @return
	 */
	List<Actor> findMaster(Long followerId, Integer[] relationTypes, Integer[] masterTypes);
	
	/**
	 * 获取隶属指定actor的所有actor
	 * @param masterId 主控方id
	 * @param relationTypes 关联的类型，对应ActorRelation的type属性
	 * @param followerTypes 从属方的类型，对应Actor的type属性
	 * @return
	 */
	List<Actor> findFollower(Long masterId, Integer[] relationTypes, Integer[] followerTypes);

	/**
	 * 获取顶层单位信息
	 * @return
	 */
	List<Actor> findTopUnit();
}
