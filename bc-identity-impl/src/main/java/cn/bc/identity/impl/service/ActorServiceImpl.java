package cn.bc.identity.impl.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.bc.core.service.DefaultCrudService;
import cn.bc.identity.domain.Actor;
import cn.bc.identity.service.ActorService;

public class ActorServiceImpl extends DefaultCrudService<Actor> implements ActorService {
	private static Log logger = LogFactory.getLog(ActorServiceImpl.class);

}