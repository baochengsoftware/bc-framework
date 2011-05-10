package cn.bc.identity.dao;

import java.util.List;

import cn.bc.core.dao.CrudDao;
import cn.bc.identity.domain.Actor;

/**
 * 参与者Dao接口
 * @author dragon
 *
 */
public interface ActorDao extends CrudDao<Actor>{

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

	/**
	 * 获取隶属关系中的所有下级组织信息
	 * @param higherOrganizationId 上级组织的id
	 * @param lowerOrganizationTypes 下级组织的类型，对应Actor的type属性，默认为单位+部门+岗位
	 * @return
	 */
	List<Actor> findLowerOrganization(Long higherOrganizationId, Integer... lowerOrganizationTypes);

	/**
	 * 获取隶属关系中的所有上级组织信息
	 * @param lowerOrganizationId 下级组织的id
	 * @param higherOrganizationTypes 上级组织的类型，对应Actor的type属性，默认为单位+部门+岗位
	 * @return
	 */
	List<Actor> findHigherOrganization(Long lowerOrganizationId, Integer... higherOrganizationTypes);

	/**
	 * 获取组织中的人员信息
	 * @param organizationId 上级组织的id
	 * @return
	 */
	List<Actor> findUser(Long organizationId);
}