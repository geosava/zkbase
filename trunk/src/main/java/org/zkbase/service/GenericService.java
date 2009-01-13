package org.zkbase.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkbase.dao.BasicDao;
import org.zkbase.dao.EntityNotFoundException;

public abstract class GenericService<T> {
	
	private final Class<T> objectClass;
	
	public GenericService(final Class<T> objectClass){
		this.objectClass = objectClass;
	}
	
	@Autowired
	public BasicDao basicDao;
	

	@Transactional(readOnly=true)
	public Long count() {
		return this.basicDao.count(this.objectClass);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)	
	public T findByID(Serializable id) throws EntityNotFoundException {
		return basicDao.find(objectClass, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> findAll(){
		List<?> objects = this.basicDao.findAll(objectClass);
		return(List<T>) objects;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> findAll(int firstResult, int maxResults){
		List<?> objects = this.basicDao.findAll(objectClass, firstResult, maxResults);
		return(List<T>) objects;
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	protected <T> List<T> findByNamedQuery(String namedQuery, int firstResult, int maxResults, Object... params) throws EntityNotFoundException{
		return this.basicDao.findNamedQuery(namedQuery, firstResult, maxResults, params);		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	protected T findByNamedQuerySingle(String namedQuery, Object... params){
		return (T) this.basicDao.findNamedQuerySingle(namedQuery, params);		
	}
	
	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void persist(T object){
		this.basicDao.persist(object);
	}

	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void delete(Serializable id) throws EntityNotFoundException {
		this.basicDao.remove(objectClass, id);
	}

	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void merge(T object) throws EntityNotFoundException {
		this.basicDao.merge(object);
	}
}
