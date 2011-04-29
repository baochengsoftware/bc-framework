/**
 * 
 */
package cn.bc.identity.web.struts2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.bc.identity.domain.User;
import cn.bc.identity.service.ActorService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author dragon
 * 
 */
public class UserFormAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(UserFormAction.class);
	private ActorService userService;
	private Long id;
	private User user;

	public ActorService getUserService() {
		return userService;
	}

	public void setUserService(ActorService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String execute() throws Exception {
		logger.debug("UserFormAction.execute");
		user = new User();
		user.setFirstName("王");
		user.setLastName("小明");
		user.setEmail("test@163.com");
		if (getId() != null) {
			// user = this.getUserService().load(getId());
			user.setId(getId());
		}
		return SUCCESS;
	}
}
