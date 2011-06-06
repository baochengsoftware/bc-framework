package cn.bc.identity.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.DigestUtils;

import cn.bc.identity.dao.AuthDataDao;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.domain.AuthData;

/**
 * 用户Service接口的实现
 * 
 * @author dragon
 * 
 */
public class UserServiceImpl extends ActorServiceImpl implements UserService {
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);
	private AuthDataDao authDataDao;

	public void setAuthDataDao(AuthDataDao authDataDao) {
		this.authDataDao = authDataDao;
	}

	public int updatePassword(Long[] ids, String password) {
		return this.authDataDao.updatePassword(ids, password);
	}

	public Actor save4belong(Actor follower, Actor belong) {
		boolean isNew = follower.isNew();
		// 先保存获取id值
		follower = super.save4belong(follower, belong);

		// 如果是新建用户，须新建AuthData对象
		if (isNew) {
			AuthData authData = new AuthData();
			authData.setId(follower.getId());
			try {
				// 设置默认密码(32位md5加密)
				authData.setPassword(DigestUtils.md5DigestAsHex("888888"
						.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
			this.authDataDao.save(authData);
		}
		return follower;
	}

	public AuthData loadAuthData(Long userId) {
		return this.authDataDao.load(userId);
	}

	@Override
	public void delete(Serializable id) {
		super.delete(id);
		// 同时删除认证信息
		this.authDataDao.delete((Long) id);
	}

	@Override
	public void delete(Serializable[] ids) {
		super.delete(ids);
		// 同时删除认证信息
		this.authDataDao.delete((Long[]) ids);
	}
}
