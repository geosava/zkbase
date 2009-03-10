package org.zkbase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.jpa.AbstractJpaTests;
import org.zkbase.model.Role;
import org.zkbase.model.User;

public class RoleServiceTest extends AbstractJpaTests {

	public RoleServiceTest() {
		this
				.setAutowireMode(AbstractDependencyInjectionSpringContextTests.AUTOWIRE_BY_NAME);
	}

	public String[] getConfigLocations() {
		return new String[] { 
				"classpath:data-source-context.xml",
				"classpath:spring-context.xml",
				};
	}
	
	@Autowired
	RoleService roleService;		

	public void testConfig() throws Exception {
		assertNotNull(roleService);
	}
	
	private void insertRole(String name) {
		Role r = new Role();
		r.setName(name);
		r.setDescription("Description of role: " + name);
		roleService.persist(r);
	}
	
	public void testStoreRole() throws Exception {
		Role r = new Role();
		r.setName("ROLE_ONE");
		r.setDescription("Description of role: ROLE_ONE");
		roleService.persist(r);
		List<Role> rl = roleService.findAll();
		assertNotNull(rl);
		assertTrue(rl.size() == 1);
	}
	
	public void testStoreRoles() throws Exception {
		insertRole("ROLE_TWO");		
		insertRole("ROLE_THREE");
		insertRole("ROLE_FOUR");
		List<Role> rl = roleService.findAll();
		assertTrue(rl.size() > 1);
	}
	
}
