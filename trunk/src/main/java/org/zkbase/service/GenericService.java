package org.zkbase.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkbase.dao.BasicDao;

public abstract class GenericService<T> {
	
	private final Class<T> objectClass;
	
	public GenericService(final Class<T> objectClass){
		this.objectClass = objectClass;
	}
	
	protected BasicDao basicDao;

	public BasicDao getBasicDao() {
		return basicDao;
	}

	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> findAll(){
		List<?> objects = this.basicDao.findAll(objectClass);
		return(List<T>) objects;
	}
	
	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void persist(T object){
		this.basicDao.persist(object);
	}

	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void delete(Long id) throws EntityNotFoundException {
		this.basicDao.remove(objectClass, id);
	}

	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void merge(T object) throws EntityNotFoundException {
		this.basicDao.merge(object);
	}
}
