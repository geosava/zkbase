package org.zkbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkbase.dao.RoleDao;
import org.zkbase.model.Role;

public class RoleService extends GenericService<Role> {
	@Autowired
	public RoleService(RoleDao roleDao) {
		super(roleDao);
	}
	public RoleService() {
		// needed for aop cglib 
	}
}
