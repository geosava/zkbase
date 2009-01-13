package org.zkbase.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.zkbase.model.User;

public class UserAuthenticationDetailsService extends GenericService<User>
		implements UserDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	public UserAuthenticationDetailsService() {
		super(User.class);
	}

	public User findByUserName(String username) {
		return super.findByNamedQuerySingle("User.findByName", username);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		 UserDetails userDetails = this.findByUserName(username);
		// UserDetails userDetails = entityManager.find(User.class, new Long(1));
		// UserDetails userDetails = new UserAuthenticationDetails(null);
//		User user = new User();
//		user.setUsername("admin");
//		user.setPassword("admin");
//		user.setEnabled(true);
//		UserDetails userDetails = user;		
//		if (userDetails == null)
//			throw new UsernameNotFoundException(username);
		return userDetails;
	}
}
