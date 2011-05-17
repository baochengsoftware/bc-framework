/**
 * 
 */
package cn.bc.desktop.web.struts2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.core.Entity;
import cn.bc.desktop.domain.PersonalConfig;
import cn.bc.desktop.service.PersonalConfigService;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.service.ActorService;
import cn.bc.web.struts2.CrudAction;

/**
 * 个人设置Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class PersonalConfigAction extends CrudAction<Long, PersonalConfig> {
	private static final long serialVersionUID = 1L;
	private String font;
	private String theme;
	private PersonalConfigService personalConfigService;
	private ActorService actorService;

	public PersonalConfigService getPersonalConfigService() {
		return personalConfigService;
	}

	@Autowired
	public void setPersonalConfigService(PersonalConfigService personalConfigService) {
		this.personalConfigService = personalConfigService;
		this.setCrudService(personalConfigService);
	}

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	// 保存个人设置
	public String update() throws Exception {
		Long actorId = new Long(14);
		Actor actor = this.actorService.load(actorId);
		PersonalConfig old = this.personalConfigService.loadByActor(actorId);
		if(old == null){//创建一个新的个人配置
			PersonalConfig common = this.personalConfigService.loadGlobalConfig();
			PersonalConfig _new = new PersonalConfig();
			_new.setStatus(Entity.STATUS_ENABLED);
			_new.setFont(common.getFont());
			_new.setTheme(common.getTheme());
			_new.setActor(actor);
			if(font != null && font.length()>0)
				_new.setFont(font);
			if(theme != null && theme.length()>0)
				_new.setTheme(theme);
			this.personalConfigService.save(_new);
		}else{//更新现有配置
			if(font != null && font.length()>0)
				old.setFont(font);
			if(theme != null && theme.length()>0)
				old.setTheme(theme);
			this.personalConfigService.save(old);
		}

		return "saveSuccess";
	}
}
