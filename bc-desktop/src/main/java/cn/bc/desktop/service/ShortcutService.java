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
	 * 获取指定Actor可以使用的快捷方式
	 * @param actorId 
	 * @return
	 */
	List<Shortcut> findByActor(Long actorId);
}
