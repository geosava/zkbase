package org.zkbase.service;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.zkbase.model.Role;
import org.zkbase.model.User;

public class UserAuthenticationDetails implements UserDetails {

	private static final long serialVersionUID = 2653602867121104826L;
	
	private String username;
	private String password;

	public UserAuthenticationDetails(User user) {
		username = "test";//user.getUsername();
		password = "test";//user.getPassword();
	}

	@Override
	public GrantedAuthority[] getAuthorities() {
		Role[] r = new Role[1];
		r[0] = new Role();
		r[0].setName("ROLE_CUSTOMER");		
		return r;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
