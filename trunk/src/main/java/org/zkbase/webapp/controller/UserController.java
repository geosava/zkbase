package org.zkbase.webapp.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkbase.model.Role;
import org.zkbase.model.User;
import org.zkbase.service.RoleService;
import org.zkbase.service.UserService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.event.PagingEvent;

public class UserController extends GenericForwardComposer implements
		ListitemRenderer {

	protected Textbox username;
	protected Textbox firstName;
	protected Textbox lastName;
	protected Textbox password;
	protected Textbox email;
	protected Paging pageUsers;
	protected Listbox userListAll;
	protected ListModelList listModelList;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	public void onClick$init(Event e) {

		// insert roles:
		Role r1 = new Role();
		r1.setName("ROLE_ADMIN");
		r1.setDescription("Administrator priviledges");
		Role r2 = new Role();
		r2.setName("ROLE_USER");
		r2.setDescription("Common user priviledges");
		roleService.persist(r1);
		roleService.persist(r2);

		// set up admin account:
		User u = new User();
		u.setFirstName("admin");
		u.setLastName("admin");
		u.setPassword("admin");
		u.setUsername("admin");
		u.setEmail("admin@zkbase.org");
		u.setAccountExpired(false);
		u.setAccountLocked(false);
		u.setCredentialsExpired(false);
		u.setEnabled(true);

		Set<Role> roles = new HashSet<Role>();
		roles.add(r1);
		roles.add(r2);
		u.setRoles(roles);

		// insert admin account:
		userService.persist(u);
		
		buildUserList();
	}

	public List<User> getList() {
		return userService.findAll();
	}

	public void onClick$add(Event e) {

		User u = new User();
		u.setFirstName(firstName.getValue());
		u.setLastName(lastName.getValue());
		u.setPassword(password.getValue());
		u.setUsername(username.getValue());
		u.setEmail(email.getValue());

		// u.setRoles(roles);

		userService.persist(u);

		buildUserList();
	}
	
	private void buildUserList() {
		final Long userCount = userService.count();
		final int pageSize = pageUsers.getPageSize();
		pageUsers.setTotalSize(userCount.intValue());
		pageUsers.setActivePage(0);		

		List<User> users = userService.findAll(0, pageSize);
		users = userService.findAll(0, pageSize);
		listModelList.clear();
		listModelList.addAll(users);
		userListAll.setItemRenderer(this);
		userListAll.setModel(listModelList);

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		listModelList = new ListModelList();
		
		buildUserList();
		final int pageSize = pageUsers.getPageSize();
		pageUsers.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event event) {
				
				PagingEvent pe = (PagingEvent) event;
				int pgno = pe.getActivePage();
				int firstResult = pgno * pageSize;
				listModelList.clear();
				List<User> users = userService.findAll(firstResult, pageSize);
				listModelList.addAll(users);
				userListAll.setModel(listModelList);
			}
		});
	}
	
	protected String getUserRolesString(User user) {
		Iterator<Role> iter = user.getRoles().iterator();
		String userRolesString = "";
		while (iter.hasNext()) {
			userRolesString += iter.next().getName();
			if (iter.hasNext())
				userRolesString += ", ";
		}
		return userRolesString;
	}
	@Override
	public void render(Listitem listItem, Object data) throws Exception {
		User user = (User) data;
		new Listcell(user.getUsername() + "").setParent(listItem);
		new Listcell(user.getFirstName() + "").setParent(listItem);
		new Listcell(user.getLastName() + "").setParent(listItem);
		new Listcell(user.getEmail() + "").setParent(listItem);
		new Listcell(this.getUserRolesString(user)).setParent(listItem);
	}
}
