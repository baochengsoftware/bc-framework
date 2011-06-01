package cn.bc.work.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bc.core.service.DefaultCrudService;
import cn.bc.work.dao.WorkDao;
import cn.bc.work.domain.Work;

/**
 * 消息service接口的实现
 * 
 * @author dragon
 * 
 */
public class WorkServiceImpl extends
		DefaultCrudService<Work> implements WorkService {
	private WorkDao messageDao;

	@Autowired
	public void setMessageDao(WorkDao messageDao) {
		this.messageDao = messageDao;
		this.setCrudDao(messageDao);
	}
}
