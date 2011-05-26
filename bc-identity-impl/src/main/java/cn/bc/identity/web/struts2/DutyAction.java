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

import cn.bc.core.Page;
import cn.bc.core.exception.CoreException;
import cn.bc.core.query.condition.Condition;
import cn.bc.core.query.condition.impl.LikeCondition;
import cn.bc.core.query.condition.impl.OrCondition;
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
	private Duty e;// entity的简写
	private List<Duty> es;// entities的简写,非分页页面用
	private Page<Duty> page;// 分页页面用
	private String ids;
	private String search;// 搜索框输入的文本

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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Duty getE() {
		return e;
	}

	public void setE(Duty e) {
		this.e = e;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Duty> getEs() {
		return es;
	}

	public void setEs(List<Duty> es) {
		this.es = es;
	}

	public Page<Duty> getPage() {
		return page;
	}

	public void setPage(Page<Duty> page) {
		this.page = page;
	}

	public String execute() throws Exception {
		logger.debug("do in method execute.");
		return SUCCESS;
	}

	// 新建
	public String create() throws Exception {
		e = this.dutyService.create();
		e.setCode(this.idGeneratorService.next("duty.code"));
		return "form";
	}

	// 编辑
	public String edit() throws Exception {
		e = this.dutyService.load(this.getId());
		return "form";
	}

	// 保存
	public String save() throws Exception {
		this.dutyService.save(e);
		return "saveSuccess";
	}

	// 删除
	public String delete() throws Exception {
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

	// 获取列表视图页面----无分页
	public String list() throws Exception {
		es = this.dutyService.createQuery()
				.condition(this.getSearchCondition()).list();
		return "list";
	}

	// 获取列表视图页面----分页
	public String paging() throws Exception {
		if (page == null)
			page = new Page<Duty>();
		if (page.getPageSize() < 1)
			page.setPageSize(Integer.parseInt(getText("app.pageSize")));

		// 构建查询条件并执行查询
		page = this.dutyService.createQuery()
				.condition(this.getSearchCondition())
				.page(page.getPageNo(), page.getPageSize());
		this.es = page.getData();
		return "paging";
	}

	/**
	 * 构建查询条件
	 * 
	 * @return
	 */
	protected Condition getSearchCondition() {
		if (this.search != null && this.search.length() > 0) {
			return new OrCondition()
					.add(new LikeCondition("code", this.search)).add(
							new LikeCondition("name", this.search));
		} else {
			return null;
		}
	}

	// 仅获取表格的数据信息部分
	public String data() throws Exception {
		if (this.page != null) {// 分页的处理
			this.page = this.dutyService.createQuery()
					.condition(this.getSearchCondition())
					.page(page.getPageNo(), page.getPageSize());
			this.es = page.getData();
		} else {// 非分页的处理
			this.es = this.dutyService.createQuery().list();
		}
		return "data";
	}
}
