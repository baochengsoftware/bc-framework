package cn.bc.desktop.dao.hibernate.jpa;

import cn.bc.core.query.condition.impl.EqualsCondition;
import cn.bc.core.query.condition.impl.IsNullCondition;
import cn.bc.desktop.dao.PersonalConfigDao;
import cn.bc.desktop.domain.PersonalConfig;
import cn.bc.orm.hibernate.jpa.HibernateCrudJpaDao;

/**
 * 个人设置Dao接口的实现
 * 
 * @author dragon
 * 
 */
public class PersonalConfigDaoImpl extends HibernateCrudJpaDao<PersonalConfig>
		implements PersonalConfigDao {
	public PersonalConfig loadByActor(Long actorId, boolean autoUseGlobal) {
		PersonalConfig pc = this.createQuery()
				.condition(new EqualsCondition("actor.id", actorId))
				.singleResult();// 个人配置
		if (pc == null && autoUseGlobal)
			pc = this.createQuery().condition(new IsNullCondition("actor"))
					.singleResult();// 全局配置
		return pc;
	}

	public PersonalConfig loadGlobalConfig() {
		return this.createQuery().condition(new IsNullCondition("actor"))
				.singleResult();
	}
}
