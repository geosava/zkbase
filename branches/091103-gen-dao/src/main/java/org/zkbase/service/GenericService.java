package org.zkbase.service;

import java.io.Serializable;
import java.util.List;

import javax.management.relation.Role;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkbase.dao.BaseDao;

import com.trg.search.ISearch;
import com.trg.search.Search;

@Transactional(readOnly=true,propagation = Propagation.REQUIRED)
public abstract class GenericService<T>  {
	
	private BaseDao baseDao;
	
	public GenericService(BaseDao baseDao){
		this.baseDao = baseDao;
		//this.objectClass = objectClass;
	}	
	
	public GenericService() {
		// needed for aop cglib 
	}

	public int count() {
		return this.baseDao.count(new Search());
	}

	@SuppressWarnings("unchecked")	
	public T findById(Serializable id) {
		return (T) baseDao.find(id);
	}

	@SuppressWarnings("unchecked")	
	public List<T> findAll(){
		return(List<T>) this.baseDao.findAll();
	}
	
	/* (non-Javadoc)
	 * @see org.zkbase.service.BaseService#findAll(int, int)
	 */
//	@SuppressWarnings("unchecked")
//	public List<T> findAll(int firstResult, int maxResults) {
//		List<?> objects = this.baseDao.findAll(objectClass, firstResult,
//				maxResults);
//		return (List<T>) objects;
//	}
//
//	@SuppressWarnings("unchecked")
//	protected <T> List<T> findByNamedQuery(String namedQuery, int firstResult,
//			int maxResults, Object... params) {
//		return this.baseDao.findNamedQuery(namedQuery, firstResult, maxResults,
//				params);
//	}
//
//	@SuppressWarnings("unchecked")
//	protected Object findByNamedQuerySingle(String namedQuery, Object... params) {
//		return this.baseDao.findNamedQuerySingle(namedQuery, params);
//	}
//

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void persist(T object) {
		this.baseDao.persist(object);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean delete(Serializable id)  {
		return this.baseDao.removeById(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void merge(T object) {
		this.baseDao.merge(object);
	}

//	abstract public List<T> findByExample(T example, int firstResult,
//			int maxResults);
//
//	abstract public Long countByExample(T example);	

}
