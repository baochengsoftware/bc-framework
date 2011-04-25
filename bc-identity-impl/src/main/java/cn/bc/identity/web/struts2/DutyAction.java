/**
 * 
 */
package cn.bc.identity.web.struts2;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.bc.identity.domain.Duty;
import cn.bc.identity.service.DutyService;
import cn.bc.identity.service.IdGeneratorService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 职务Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class DutyAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(DutyAction.class);

	private DutyService dutyService;
	private IdGeneratorService idGeneratorService;
	private Long id;
	private Duty b;
	private List<Duty> list;

	@Autowired
	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	@Autowired
	public void setIdGeneratorService(IdGeneratorService idGeneratorService) {
		this.idGeneratorService = idGeneratorService;
	}

	public Duty getB() {
		return b;
	}

	public void setB(Duty b) {
		this.b = b;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Duty> getList() {
		return list;
	}

	public void setList(List<Duty> list) {
		this.list = list;
	}

	public String execute() throws Exception {
		logger.debug("do in method execute.");
		return SUCCESS;
	}

	public String form() throws Exception {
		try {
			logger.debug("do in method form.");
			b = this.dutyService.create();
			b.setCode(this.idGeneratorService.next("duty.code"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "form";
	}

	public String list() throws Exception {
		logger.debug("do in method list.");
		list = this.dutyService.createQuery().list();
		return "list";
	}
}
