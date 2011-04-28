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

import cn.bc.core.exception.CoreException;
import cn.bc.core.util.StringUtils;
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
	private List<Duty> bs;
	private String ids;

	@Autowired
	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	@Autowired
	public void setIdGeneratorService(IdGeneratorService idGeneratorService) {
		this.idGeneratorService = idGeneratorService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public List<Duty> getBs() {
		return bs;
	}

	public void setBs(List<Duty> bs) {
		this.bs = bs;
	}

	public String execute() throws Exception {
		logger.debug("do in method execute.");
		return SUCCESS;
	}

	// 新建
	public String create() throws Exception {
		b = this.dutyService.create();
		b.setCode(this.idGeneratorService.next("duty.code"));
		return "form";
	}

	// 编辑
	public String edit() throws Exception {
		b = this.dutyService.load(this.getId());
		return "form";
	}

	// 保存
	public String save() throws Exception {
		logger.debug("do in method save:" + b);
		this.dutyService.save(b);
		return "saveSuccess";
	}

	// 删除
	public String delete() throws Exception {
		logger.debug("do in method delete.");
		if (this.getId() != null) {// 删除一条
			this.dutyService.delete(this.getId());
		} else {// 删除一批
			if (this.getIds() != null && this.getIds().length() > 0) {
				Long[] ids = StringUtils.stringArray2LongArray(this.getIds()
						.split(","));
				this.dutyService.delete(ids);
			} else {
				throw new CoreException("must set property id or ids");
			}
		}
		return "deleteSuccess";
	}

	// 获取列表视图页面
	public String list() throws Exception {
		logger.debug("do in method list.");
		bs = this.dutyService.createQuery().list();
		return "list";
	}
}
