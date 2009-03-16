package org.zkbase.service;


public abstract class SearchableService<T> extends GenericService<T> implements Searchable<T>{

	public SearchableService(Class<T> objectClass) {
		super(objectClass);
	}

}
