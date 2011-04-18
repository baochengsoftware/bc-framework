package cn.bc.identity.impl.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.bc.core.dao.CrudDao;
import cn.bc.core.exception.CoreException;
import cn.bc.identity.domain.Generator;
import cn.bc.identity.service.GeneratorService;

public class GeneratorServiceImpl implements GeneratorService {
	private static Log logger = LogFactory.getLog(GeneratorServiceImpl.class);
	protected CrudDao<Generator> generatorDao;

	@Autowired
	public void setGeneratorDao(CrudDao<Generator> generatorDao) {
		this.generatorDao = generatorDao;
	}

	public Long nextValue(String type) {
		Generator entity = generatorDao.load(type);
		if (entity == null)
			throw new CoreException("type is not exist. type=" + type);
		entity.setValue(entity.getValue() + 1);
		generatorDao.save(entity);
		return entity.getValue();
	}

	public String next(String type) {
		Generator entity = generatorDao.load(type);
		if (entity == null)
			throw new CoreException("type is not exist. type=" + type);
		entity.setValue(entity.getValue() + 1);
		generatorDao.save(entity);
		return this.formatValue(type, entity.getValue(), entity.getFormat());
	}

	public Long currentValue(String type) {
		Generator entity = generatorDao.load(type);
		if (entity == null)
			throw new CoreException("type is not exist. type=" + type);
		return entity.getValue();
	}

	public String current(String type) {
		Generator entity = generatorDao.load(type);
		if (entity == null)
			throw new CoreException("type is not exist. type=" + type);
		return this.formatValue(type, entity.getValue(), entity.getFormat());
	}

	// 格式化
	protected String formatValue(String type, Long value, String format) {
		if (logger.isDebugEnabled())
			logger.debug("formatValue:type=" + type + ";value=" + value
					+ ";format=" + format);
		if (format == null || format.length() == 0) {
			return String.valueOf(value);
		} else {
			// TODO:${DATE},${TIME},${S}
			return format.replaceAll("\\$\\{T\\}", type).replaceAll(
					"\\$\\{V\\}", value.toString());
		}
	}

	public void save(Generator generator) {
		generatorDao.save(generator);
	}
}
