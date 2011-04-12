/**
 * 
 */
package cn.bc.identity.struts2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.bc.identity.domain.UserImpl;
import cn.bc.identity.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author dragon
 * 
 */
public class UserFormAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(UserFormAction.class);
	private UserService userService;
	private Long id;
	private UserImpl user;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserImpl getUser() {
		return user;
	}

	public void setUser(UserImpl user) {
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
		user = new UserImpl();
		user.setFirstName("王");
		user.setLastName("小明");
		user.setEmail("test@163.com");
		if(getId() != null){
			//user = this.getUserService().load(getId());
			user.setId(getId());
		}
		return SUCCESS;
	}
}
