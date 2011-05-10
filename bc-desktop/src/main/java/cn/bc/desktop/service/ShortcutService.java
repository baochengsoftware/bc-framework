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
	 * 获取用户可以使用的快捷方式
	 * @param userId 
	 * @return
	 */
	List<Shortcut> findByUser(Long userId);
}
