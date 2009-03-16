package org.zkbase.service;

import java.util.List;

public interface Searchable<T> {

	public abstract List<T> findByExample(T example, int firstResult,
			int maxResults);

	public abstract Long countByExample(T example);

}