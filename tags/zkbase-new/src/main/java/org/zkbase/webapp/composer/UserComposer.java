package org.zkbase.webapp.composer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkbase.dao.EntityNotFoundException;
import org.zkbase.model.Role;
import org.zkbase.model.User;
import org.zkbase.service.RoleService;
import org.zkbase.service.UserService;
import org.zkbase.webapp.component.MainWindow;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class UserComposer extends GenericForwardComposer {
    private final Log log = LogFactory.getLog(getClass());

    protected Window userDetailsWin;
	protected Textbox username;
	protected Textbox firstName;
	protected Textbox lastName;
	protected Textbox password;
	protected Textbox email;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;
	
	User selectedObject;

	public void createDemoAccount(String name, Set<Role> roles) {
		log.debug("Creating demo account for user " + name);
		User u = new User();
		u.setFirstName(name);
		u.setLastName("name");
		u.setPassword(name);
		u.setUsername(name);
		u.setEmail(name + "@zkbase.org");
		u.setAccountExpired(false);
		u.setAccountLocked(false);
		u.setCredentialsExpired(false);
		u.setEnabled(true);

		u.setRoles(roles);

		userService.persist(u);
	}

	public void onClick$init(Event e) {
		log.info("onClick: init");
		// insert roles:
		Role r1 = new Role();
		r1.setName("ROLE_USER");
		r1.setDescription("Common user privileges");
		Role r2 = new Role();
		r2.setName("ROLE_ADMIN");
		r2.setDescription("Administrator privileges");

		roleService.persist(r1);
		roleService.persist(r2);

		Set<Role> roles = new HashSet<Role>();
		roles.add(r1);
		// set up users accounts

		for (int i = 1; i <= 100; ++i)
			this.createDemoAccount("user" + i, roles);

		roles.add(r2);
		// set up admin account:
		this.createDemoAccount("admin", roles);

	}

	public void onChange$username(Event e) {
		log.info("onChange: username");
		User user = userService.findByUserName(username.getValue());
		if (user != null)
			throw new WrongValueException(username, "username already exists");
	}

	public void onClick$save(Event e) {
		log.info("onClick: save");

		if (this.selectedObject == null)
			this.selectedObject = new User();
	
		this.selectedObject.setFirstName(firstName.getValue());
		this.selectedObject.setLastName(lastName.getValue());
		this.selectedObject.setPassword(password.getValue());
		this.selectedObject.setUsername(username.getValue());
		this.selectedObject.setEmail(email.getValue());

		// u.setRoles(roles);

		try {
			userService.merge(this.selectedObject);
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		userDetailsWin.onClose();
		e.stopPropagation();
	}


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		this.selectedObject = (User) comp.getDesktop().getAttribute("SELECTED_OBJECT");
		
		if (this.selectedObject != null) {
			this.username.setValue(this.selectedObject.getUsername());
			this.firstName.setValue(this.selectedObject.getFirstName());
			this.lastName.setValue(this.selectedObject.getLastName());
			this.password.setValue(this.selectedObject.getPassword());
			this.email.setValue(this.selectedObject.getEmail());
		}
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
}


