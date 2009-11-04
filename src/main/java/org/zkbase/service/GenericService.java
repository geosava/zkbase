package org.zkbase.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkbase.dao.BaseDao;

import com.trg.search.ExampleOptions;
import com.trg.search.Filter;
import com.trg.search.Search;

@Transactional(readOnly=true,propagation = Propagation.REQUIRED)
public abstract class GenericService<T>  {
	
	private BaseDao baseDao;
	
	public GenericService(BaseDao baseDao){
		this.baseDao = baseDao;
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
	// TODO consider paging
	public List<T> findAll(){
		return(List<T>) this.baseDao.findAll();
	}
	
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

	// TODO consider paging
	public List<T> findByExample(T example) {
		ExampleOptions eo = new ExampleOptions();
		eo.setLikeMode(ExampleOptions.ANYWHERE);
		eo.setIgnoreCase(true);
		Filter filter = this.baseDao.getFilterFromExample(example, eo);
		filter.setOperator(Filter.OP_OR);
		Search search = new Search();
		List<Filter> filterList = new ArrayList<Filter>();
		filterList.add(filter);
		search.setFilters(filterList);
		return (List<T>)this.baseDao.search(search);
	}


}
