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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import cn.bc.core.Entity;
import cn.bc.core.SetEntityClass;
import cn.bc.core.exception.CoreException;
import cn.bc.core.service.CrudService;
import cn.bc.web.ui.html.grid.Grid;
import cn.bc.web.ui.html.grid.IdColumn;
import cn.bc.web.ui.html.page.ButtonOption;
import cn.bc.web.ui.html.page.ListPage;
import cn.bc.web.ui.html.page.Option;
import cn.bc.web.ui.html.page.Page;
import cn.bc.web.util.WebUtils;

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
	private E entity;
	private List<E> entities;
	private String ids;
	private Page page;
	private Class<E> entityClass;

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

	/**
	 * entity的缩写形式，方便页面使用e.id的简写
	 * 
	 * @return
	 */
	// public E getE() {
	// return entity;
	// }

	public E getEntity() {
		if (entity == null) {
			// 这是使用泛型没有办法之举：ERROR InstantiatingNullHandler Could not create
			// and/or set value back on to object
			// at
			// com.opensymphony.xwork2.spring.SpringObjectFactory.buildBean(SpringObjectFactory.java:169)
			try {
				entity = this.getEntityClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<E> getEntities() {
		return entities;
	}

	public void setEntities(List<E> entities) {
		this.entities = entities;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String execute() throws Exception {
		logger.debug("do in method execute.");
		return "success";
	}

	// 新建
	public String create() throws Exception {
		entity = this.getCrudService().create();
		return "form";
	}

	// 编辑
	public String edit() throws Exception {
		entity = this.getCrudService().load(this.getId());
		return "form";
	}

	// 保存
	public String save() throws Exception {
		logger.debug("do in method save:" + entity);
		this.getCrudService().save(entity);
		return "saveSuccess";
	}

	// 删除
	public String delete() throws Exception {
		logger.debug("do in method delete.");
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

	// 获取列表视图页面
	public String list() throws Exception {
		logger.debug("do in method list.");
		entities = this.getCrudService().createQuery().list();
		this.setPage(buildListPage(entities));
		return "list";
	}

	protected ListPage buildListPage(List<E> entities) {
		ListPage listPage = new ListPage();
		listPage.setGrid(buildGrid(entities))
				.setCreateAction(getCreateAction())
				.setDeleteAction(getDeleteAction())
				.setEditAction(this.getEditAction()).setJs(getJs())
				.setCss(getCss()).setTitle(this.getPageTitle())
				.setIniMethod(getIniMethod())
				.setOption(buildListPageOption().toString()).setBeautiful(true)
				.setAttr("data-name",getText(StringUtils.uncapitalize(getEntityConfigName())))
				.addClazz("bc-content");
		return listPage;
	}

	protected String getPageTitle() {
		return this.getText(StringUtils.uncapitalize(getEntityConfigName())
				+ ".title");
	}

	protected Grid buildGrid(List<E> entities) {
		//id列
		Grid grid = new Grid().setData(entities).addColumn(IdColumn.DEFAULT());
		//name属性设为bean的名称
		grid.setName(getText(StringUtils.uncapitalize(getEntityConfigName())));
		//多选及双击行编辑
		grid.setSingleSelect(false).setDblClickRow("bc.page.edit");
		return grid;
	}

	protected Option buildListPageOption() {
		return new Option()
				.addButton(new ButtonOption(getText("label.delete"), "delete"))
				.addButton(new ButtonOption(getText("label.edit"), "edit"))
				.addButton(new ButtonOption(getText("label.create"), "create"))
				.setMinWidth(250).setMinHeight(200).setModal(false);
	}
	
	protected String getActionPathPrefix() {
		return "/bc";
	}
	
	// 统一定义的值
	protected String getEditAction() {
		return WebUtils.rootPath + getActionPathPrefix() + "/"
				+ StringUtils.uncapitalize(getEntityConfigName()) + "/edit";
	}

	protected String getDeleteAction() {
		return WebUtils.rootPath + getActionPathPrefix() + "/"
				+ StringUtils.uncapitalize(getEntityConfigName()) + "/delete";
	}

	protected String getCreateAction() {
		return WebUtils.rootPath + getActionPathPrefix() + "/"
				+ StringUtils.uncapitalize(getEntityConfigName()) + "/create";
	}

	protected String getIniMethod() {
		return null;
	}

	protected String getCss() {
		return null;
	}

	protected String getJs() {
		return null;
	}
}