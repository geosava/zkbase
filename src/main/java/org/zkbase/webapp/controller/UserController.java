package org.zkbase.webapp.controller;

import java.util.List;

import org.zkbase.dao.BasicDao;
import org.zkbase.model.User;
import org.zkbase.service.UserService;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

public class UserController extends GenericForwardComposer {
	
	protected Textbox userName;
	protected Textbox firstName;
	protected Textbox lastName;
	protected Textbox password;
	protected Textbox email;
	
	UserService userService;		

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public List<User> getList() {
		return userService.findAll();
	}

	public void onClick$add(Event e) {
		BasicDao bd = userService.getBasicDao();
		
		User u = new User();
		u.setFirstName(firstName.getValue());
		u.setLastName(lastName.getValue());
		u.setPassword(password.getValue());
		u.setUserName(userName.getValue());		
		userService.persist(u);
	}
}
