package cn.bc.identity.dao;

import java.io.Serializable;
import java.util.List;

import cn.bc.core.dao.CrudDao;
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
	 * 获取某关联关系列表
	 * @param mfIds 从属方或主控方的Id,不能为空
	 * @return
	 */
	List<ActorRelation> findByMasterOrFollower(Serializable[] mfIds);
	
	/**
	 * 删除关联关系
	 * @param mfIds 从属方或主控方的Id,不能为空
	 * @return
	 */
	void deleteByMasterOrFollower(Serializable[] mfIds);
}
