package cn.bc.desktop.service;

import cn.bc.core.service.CrudService;
import cn.bc.desktop.domain.PersonalConfig;

/**
 * 个人设置Service接口
 * 
 * @author dragon
 * 
 */
public interface PersonalConfigService extends CrudService<PersonalConfig> {
	/**
	 * 获取全局配置信息
	 * @param actorId
	 * @return
	 */
	PersonalConfig loadGlobalConfig();
	
	/**
	 * 获取actor专用的配置信息
	 * @param actorId
	 * @return
	 */
	PersonalConfig loadByActor(Long actorId);

	/**
	 * 获取actor可用的配置信息
	 * @param actorId
	 * @param autoUseGlobal 如果actor没有专用的配置是否使用全局配置
	 * @return
	 */
	PersonalConfig loadByActor(Long actorId, boolean autoUseGlobal);
}
