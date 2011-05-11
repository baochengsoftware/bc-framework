package cn.bc.desktop.service;

import java.util.List;

import cn.bc.core.service.CrudService;
import cn.bc.desktop.domain.Shortcut;

/**
 * 桌面快捷方式Service接口
 * 
 * @author dragon
 * 
 */
public interface ShortcutService extends CrudService<Shortcut> {
	/**
	 * 获取通用的快捷方式
	 * @return
	 */
	List<Shortcut> findCommon();
	
	/**
	 * 获取actor可以使用的快捷方式
	 * @param actorId 参与者的id,为空代表获取通用的快捷方式
	 * @param includeAncestor 是否包含上级组织可使用快捷方式（包括上级的上级） 
	 * @param includeCommon 是否包含通用快捷方式（actor为null的） 
	 * @return
	 */
	List<Shortcut> findByActor(Long actorId,boolean includeAncestor, boolean includeCommon);
	
	/**
	 * 获取actor可以使用的快捷方式
	 * <p>返回的列表中将包含如下额外的两项：
	 * 1)actor所隶属的任何上级组织（含上级的上级）可使用的快捷方式也会一并返回
	 * 2)系统通用的快捷方式
	 * </p>
	 * @param actorId 参与者的id,为空代表获取通用的快捷方式
	 * @return
	 */
	List<Shortcut> findByActor(Long actorId);
}
