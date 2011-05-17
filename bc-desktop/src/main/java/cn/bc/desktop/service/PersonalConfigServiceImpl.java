package cn.bc.desktop.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bc.core.service.DefaultCrudService;
import cn.bc.desktop.dao.PersonalConfigDao;
import cn.bc.desktop.domain.PersonalConfig;

/**
 * 个人设置service接口的实现
 * 
 * @author dragon
 * 
 */
public class PersonalConfigServiceImpl extends
		DefaultCrudService<PersonalConfig> implements PersonalConfigService {
	private PersonalConfigDao personalConfigDao;

	@Autowired
	public void setPersonalConfigDao(PersonalConfigDao personalConfigDao) {
		this.personalConfigDao = personalConfigDao;
		this.setCrudDao(personalConfigDao);
	}

	public PersonalConfig loadByActor(Long actorId) {
		return personalConfigDao.loadByActor(actorId, false);
	}

	public PersonalConfig loadByActor(Long actorId, boolean autoUseGlobal) {
		return personalConfigDao.loadByActor(actorId, autoUseGlobal);
	}

	public PersonalConfig loadGlobalConfig() {
		return personalConfigDao.loadGlobalConfig();
	}
}
