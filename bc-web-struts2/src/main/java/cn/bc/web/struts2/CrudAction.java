/**
 * 
 */
package cn.bc.web.struts2;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import cn.bc.core.Entity;
import cn.bc.core.Page;
import cn.bc.core.SetEntityClass;
import cn.bc.core.exception.CoreException;
import cn.bc.core.query.condition.Condition;
import cn.bc.core.service.CrudService;
import cn.bc.web.ui.Component;
import cn.bc.web.ui.html.grid.FooterButton;
import cn.bc.web.ui.html.grid.Grid;
import cn.bc.web.ui.html.grid.GridFooter;
import cn.bc.web.ui.html.grid.IdColumn;
import cn.bc.web.ui.html.page.HtmlPage;
import cn.bc.web.ui.html.page.ListPage;
import cn.bc.web.ui.html.page.PageOption;
import cn.bc.web.ui.html.toolbar.Toolbar;
import cn.bc.web.ui.html.toolbar.ToolbarButton;
import cn.bc.web.ui.html.toolbar.ToolbarSearchButton;

import com.opensymphony.xwork2.ActionSupport;

/**
 * CRUD通用Action
 * 
 * @author dragon
 * 
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller
public class CrudAction<K extends Serializable, E extends Entity<K>> extends
		ActionSupport implements SetEntityClass<E> {
	private static final long serialVersionUID = 1L;
	protected Log logger = LogFactory.getLog(getClass());

	private CrudService<E> crudService;
	private Long id;
	private E e; // entity的简写
	private List<E> es; // entities的简写,非分页页面用
	private String ids; // 批量删除的id，多个id间用逗号连接
	private Component html; // 后台生成的html页面
	private Class<E> entityClass;
	private Page<E> page; // 分页页面用
	private String search; // 搜索框输入的文本

	@SuppressWarnings("unchecked")
	public CrudAction() {
		// 这个需要子类中指定T为实际的类才有效(指定接口也不行的)
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getActualTypeArguments()[1];
			if (type instanceof Class) {
				this.entityClass = (Class<E>) type;
				if (logger.isInfoEnabled())
					logger.info("auto judge entityClass to '"
							+ this.entityClass + "' [" + this.getClass() + "]");
			}
		}
	}

	public Page<E> getPage() {
		return page;
	}

	public void setPage(Page<E> page) {
		this.page = page;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	protected Class<? extends E> getEntityClass() {
		return this.entityClass;
	}

	protected String getEntityConfigName() {
		return this.getEntityClass().getSimpleName();
	}

	public void setEntityClass(Class<E> clazz) {
		this.entityClass = clazz;
	}

	public CrudService<E> getCrudService() {
		return crudService;
	}

	public void setCrudService(CrudService<E> crudService) {
		this.crudService = crudService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public E getE() {
		if (e == null) {
			// 这是使用泛型没有办法之举：ERROR InstantiatingNullHandler Could not create
			// and/or set value back on to object
			// at
			// com.opensymphony.xwork2.spring.SpringObjectFactory.buildBean(SpringObjectFactory.java:169)
			try {
				e = this.getEntityClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return e;
	}

	public void setE(E entity) {
		this.e = entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<E> getEs() {
		return es;
	}

	public void setEs(List<E> entities) {
		this.es = entities;
	}

	public Component getHtml() {
		return html;
	}

	public void setHtml(Component page) {
		this.html = page;
	}

	public String execute() throws Exception {
		logger.debug("do in method execute.");
		return "success";
	}

	// 新建
	public String create() throws Exception {
		e = this.getCrudService().create();
		return "form";
	}

	// 编辑
	public String edit() throws Exception {
		e = this.getCrudService().load(this.getId());
		return "form";
	}

	// 保存
	public String save() throws Exception {
		this.getCrudService().save(e);
		return "saveSuccess";
	}

	// 删除
	public String delete() throws Exception {
		if (this.getId() != null) {// 删除一条
			this.getCrudService().delete(this.getId());
		} else {// 删除一批
			if (this.getIds() != null && this.getIds().length() > 0) {
				Long[] ids = cn.bc.core.util.StringUtils
						.stringArray2LongArray(this.getIds().split(","));
				this.getCrudService().delete(ids);
			} else {
				throw new CoreException("must set property id or ids");
			}
		}
		return "deleteSuccess";
	}

	// 获取列表视图页面----无分页
	public String list() throws Exception {
		// 根据请求的条件查找信息
		this.es = this.findList(this.getSearchCondition());

		// 构建页面的html
		this.setHtml(buildHtml4List(this.es));

		// 返回全局的global-results：在cn/bc/web/struts2/struts.xml中定义的
		return "page";
	}

	// 获取列表视图页面----分页
	public String paging() throws Exception {
		// 首次请求时page对象为空，需要初始化一下
		if (this.page == null)
			this.page = new Page<E>();
		if (this.page.getPageSize() < 1)
			this.page.setPageSize(Integer.parseInt(getText("app.pageSize")));

		// 根据请求的条件查找分页信息
		this.page = this.findPage(this.getSearchCondition(), page.getPageNo(),
				page.getPageSize());
		this.es = page.getData();

		// 构建页面的html
		this.setHtml(buildHtml4Paging(this.page));

		// 返回全局的global-results：在cn/bc/web/struts2/struts.xml中定义的
		return "page";
	}

	// 仅获取表格的数据信息部分
	public String data() throws Exception {
		if (this.page != null) {// 分页的处理
			// 根据请求的条件查找分页信息
			this.page = this.findPage(this.getSearchCondition(),
					this.page.getPageNo(), this.page.getPageSize());
			this.es = this.page.getData();

			// 构建页面的html
			this.setHtml(buildHtml4Data(this.page.getData(),
					this.page.getPageNo(), this.page.getPageCount()));
		} else {// 非分页的处理
			// 根据请求的条件查找分页信息
			this.es = this.findList(this.getSearchCondition());

			// 构建页面的html
			this.setHtml(buildHtml4Data(this.es, 0, 0));
		}

		// 返回全局的global-results：在cn/bc/web/struts2/struts.xml中定义的
		return "page";
	}

	/**
	 * 构建数据部分的html
	 * 
	 * @param page
	 * @return
	 */
	protected Component buildHtml4Data(List<E> es, int pageNo, int pageCount) {
		return null;
	}

	/**
	 * 根据请求的条件查找列表对象
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	protected List<E> findList(Condition condition) {
		return this.getCrudService().createQuery().condition(condition).list();
	}

	/**
	 * 根据请求的条件查找分页信息对象
	 * 
	 * @param condition
	 *            查询条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	protected Page<E> findPage(Condition condition, int pageNo, int pageSize) {
		return this.getCrudService().createQuery().condition(condition)
				.page(pageNo, pageSize);
	}

	/**
	 * 构建查询条件
	 * 
	 * @return
	 */
	protected Condition getSearchCondition() {
		return null;
	}

	/**
	 * 构建分页视图页面的html
	 * 
	 * @param page
	 *            分页信息对象
	 * @return
	 */
	protected HtmlPage buildHtml4Paging(Page<E> page) {
		ListPage listPage = new ListPage();
		// 工具条
		listPage.setToolbar(buildToolbar());
		// 表格
		listPage.setGrid(buildGrid());
		// 参数
		listPage.setCreateUrl(getCreateUrl())
				.setDeleteUrl(getDeleteUrl())
				.setEditUrl(this.getEditUrl())
				.setNamespace(getPageNamespace())
				.setJs(getJs())
				.setCss(getCss())
				.setTitle(this.getPageTitle())
				.setIniMethod(getIniMethod())
				.setOption(buildListPageOption().toString())
				.setBeautiful(true)
				.setAttr(
						"data-name",
						getText(StringUtils.uncapitalize(getEntityConfigName())))
				.addClazz("bc-page");
		return listPage;
	}

	/**
	 * 构建非分页视图页面的html
	 * 
	 * @return
	 */
	protected ListPage buildHtml4List(List<E> entities) {
		ListPage listPage = new ListPage();
		listPage.setGrid(buildGrid()).setToolbar(buildToolbar())
				.setCreateUrl(getCreateUrl()).setDeleteUrl(getDeleteUrl())
				.setEditUrl(this.getEditUrl()).setNamespace(getPageNamespace())
				.setJs(getJs()).setCss(getCss()).setTitle(this.getPageTitle())
				.setIniMethod(getIniMethod())
				.setOption(buildListPageOption().toString());
		listPage.setAttr("data-name",
				getText(StringUtils.uncapitalize(getEntityConfigName())))
				.addClazz("bc-page");
		return listPage;
	}

	/** 构建视图页面的对话框初始化配置 */
	protected PageOption buildListPageOption() {
		return new PageOption().setMinWidth(250).setMinHeight(200)
				.setModal(false);
	}

	/** 构建视图页面的工具条 */
	protected Toolbar buildToolbar() {
		Toolbar tb = new Toolbar();

		// 新建按钮
		tb.addButton(new ToolbarButton().setIcon("ui-icon-document")
				.setText(getText("label.create")).setAction("create"));
		// 编辑按钮
		tb.addButton(new ToolbarButton().setIcon("ui-icon-pencil")
				.setText(getText("label.edit")).setAction("edit"));
		// 删除按钮
		tb.addButton(new ToolbarButton().setIcon("ui-icon-trash")
				.setText(getText("label.delete")).setAction("delete"));

		// 搜索按钮
		ToolbarSearchButton sb = new ToolbarSearchButton();
		sb.setAction("search").setTitle(getText("title.click2search"));
		tb.addButton(sb);

		return tb;
	}

	/** 构建视图页面的表格 */
	protected Grid buildGrid() {
		// id列
		Grid grid = new Grid().setData(this.es).addColumn(IdColumn.DEFAULT());
		// name属性设为bean的名称
		grid.setName(getText(StringUtils.uncapitalize(getEntityConfigName())));
		// 多选及双击行编辑
		grid.setSingleSelect(false).setDblClickRow("bc.page.edit");

		// 分页条
		grid.setFooter(buildGridFooter());

		return grid;
	}

	/** 构建视图页面表格底部的工具条 */
	protected GridFooter buildGridFooter() {
		GridFooter footer = new GridFooter();

		// 刷新按钮
		footer.addButton(new FooterButton().setIcon("ui-icon-refresh")
				.setAction("refresh").setTitle(getText("label.refresh")));

		return footer;
	}

	/**
	 * 获取访问action的前缀。 struts配置文件中package节点的namespace属性去除最后面的bean名称的部分
	 * 如namespace="/bc/duty"，则这里应返回"/bc"
	 * 
	 * @return
	 */
	protected String getActionPathPrefix() {
		return "/bc";
	}

	/** 访问的主路径 */
	protected String getPageNamespace() {
		return getContextPath() + this.getActionPathPrefix() + "/"
				+ StringUtils.uncapitalize(getEntityConfigName());
	}

	/** 对话框的标题 */
	protected String getPageTitle() {
		return this.getText(StringUtils.uncapitalize(getEntityConfigName())
				+ ".title");
	}

	/** 编辑的url */
	protected String getEditUrl() {
		return getPageNamespace() + "/edit";
	}

	/** 删除的url */
	protected String getDeleteUrl() {
		return getPageNamespace() + "/delete";
	}

	/** 新建的url */
	protected String getCreateUrl() {
		return getPageNamespace() + "/create";
	}

	/** 获取访问该ation的上下文路径 */
	protected String getContextPath() {
		return ServletActionContext.getRequest().getContextPath();
	}

	/** 页面加载后的调用js初始化方法 */
	protected String getIniMethod() {
		return null;
	}

	/** 页面需要另外加载的css文件，逗号连接多个文件 */
	protected String getCss() {
		return null;
	}

	/** 页面需要另外加载的js文件，逗号连接多个文件 */
	protected String getJs() {
		return null;
	}
}