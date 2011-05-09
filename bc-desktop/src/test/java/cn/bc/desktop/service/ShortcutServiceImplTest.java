package cn.bc.desktop.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.bc.desktop.domain.Shortcut;
import cn.bc.desktop.service.ShortcutService;
import cn.bc.test.AbstractEntityCrudTest;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:spring-test.xml")
public class ShortcutServiceImplTest extends AbstractEntityCrudTest<Long,Shortcut> {
	ShortcutService shortcutService;
	@Autowired
	public void setShortcutService(ShortcutService moduleService) {
		this.shortcutService = moduleService;
		this.crudOperations = moduleService;//赋值基类的crud操作对象
	}

	@Override
	protected Shortcut createInstance(String config) {
		Shortcut entity = super.createInstance(config);
		
		//补充一些额外的设置
		entity.setOrder("01");
		entity.setName("name");
		
		return entity;
	}
}
