/**
 * 
 */
package cn.bc.web.struts2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author dragon
 * 
 */
public class ExampleAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	protected final Log logger = LogFactory.getLog(getClass());

	private String accountBean;

	public String execute() {
		return SUCCESS;
	}

	public void validate() {
		logger.debug("In method validate. accountBean's state is "
				+ accountBean);

		if (accountBean.length() == 0) {
			addFieldError("accountBean", "accountBean is required.");
		}
	}

	public String getAccountBean() {
		return accountBean;
	}

	public void setAccountBean(String accountBean) {
		this.accountBean = accountBean;
	}
}
