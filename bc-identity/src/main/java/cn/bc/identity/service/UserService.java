package cn.bc.identity.service;

import cn.bc.identity.domain.AuthData;

/**
 * 用户Service接口
 * 
 * @author dragon
 * 
 */
public interface UserService extends ActorService {
	/**
	 * 批量更新密码
	 * 
	 * @param ids 用户的id
	 * @param password 新的密码（应为已经加密的密文）
	 */
	int updatePassword(Long[] ids, String password);

	/**
	 * 获取用户的认证信息
	 * @param userId 用户的id
	 * @return
	 */
	AuthData loadAuthData(Long userId);
}
