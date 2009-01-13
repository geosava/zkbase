package org.zkbase.service;

import java.util.List;

import org.zkbase.dao.EntityNotFoundException;
import org.zkbase.model.User;

public class UserService extends GenericService<User> {

	public UserService() {
		super(User.class);
	}
	
	public User findByUserName(String username) {
		return super.findByNamedQuerySingle("User.findByName", username);
	}

	public List<User> findByUserNameLike(String username, int firstResult, int maxResults)
			throws EntityNotFoundException {
		return super.findByNamedQuery("User.findByNameLike", firstResult, maxResults, username + "%");
	}

}
