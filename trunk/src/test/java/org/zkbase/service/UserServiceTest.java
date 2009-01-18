package org.zkbase.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.jpa.AbstractJpaTests;
import org.zkbase.model.Role;
import org.zkbase.model.User;


public class UserServiceTest extends AbstractJpaTests {

	public UserServiceTest() {
		this
				.setAutowireMode(AbstractDependencyInjectionSpringContextTests.AUTOWIRE_BY_NAME);
	}

	public String[] getConfigLocations() {
		return new String[] { 
				"classpath:data-source-context.xml",
				"classpath:spring-context.xml",
				//"classpath:spring-security.xml"
				};
	}
	
	@Autowired
	UserService userService;		
	
//	@Autowired
//	UserAuthenticationDetailsService userAuthenticationDetailsService;

	public void testConfig() throws Exception {
		assertNotNull(userService);
//		assertNotNull(userAuthenticationDetailsService);
	}
	
	private void insertUser(Integer index) {
		User u = new User();
		u.setFirstName("firstName" + index);
		u.setLastName("lastName" + index);
		u.setPassword("password" + index);
		u.setUsername("username" + index);
		u.setEmail("username" + index + "@zkbase.org");
		userService.persist(u);
	}
	
	private void insertUsers() {
		insertUser(1);
		insertUser(2);
		insertUser(3);
		insertUser(4);
		insertUser(5);
		insertUser(6);		
	}
	
	public void testStoreUserSimple() throws Exception {
		User u = new User();
		u.setFirstName("firstNameS");
		u.setLastName("lastNameS");
		u.setPassword("passwordS");
		u.setUsername("usernameS");
		userService.persist(u);
		List<User> ul = userService.findAll();
		assertNotNull(ul);
		assertTrue(ul.size() == 1);
	}
	
	public void testFindByUserName() throws Exception {
		insertUsers();		
		List<User> ul = userService.findAll();
		User u = userService.findByUserName("username3");
		assertNotNull(u);
		assertTrue(u.getUsername().equals("username3"));
	}	
	
	public void testFindByUserNameLike() throws Exception {
		insertUsers();		
		List<User> ul = userService.findByUserNameLike("username", 0, 3);
		assertTrue(ul.size() > 1);
	}
	
	public void testCount() throws Exception {
		insertUsers();
		Long c = userService.count();
		assertTrue(c == 6);
	}
	
	public void testStoreUserComplex() throws Exception {
		User u1 = new User();
		u1.setFirstName("firstNameC");
		u1.setLastName("lastNameC");
		u1.setPassword("passwordC");
		u1.setUsername("usernameC");
		u1.setEmail("emailC@zkbase.org");
		u1.setAccountExpired(false);
		u1.setAccountLocked(false);
		u1.setCredentialsExpired(false);
		u1.setEnabled(true);		
		Set<Role> roles = new HashSet<Role>();
		Role r1 = new Role();
		r1.setName("ROLE_ADMIN");
		Role r2 = new Role();
		r2.setName("ROLE_USER");
		roles.add(r1);
		roles.add(r2);
		u1.setRoles(roles);
		userService.persist(u1);
		User u2 = userService.findByUserName("usernameC");
		assertNotNull(u2);
		Set<Role> roles2 = u2.getRoles();
		assertTrue(roles2.contains(r1));
		assertTrue(roles2.contains(r2));
		assertNotNull(roles2);
		assertEquals(u1, u2);
	}	
	
	public void testFindByExample() throws Exception {
		insertUsers();		
		User user = new User();
		user.setUsername("user");
		List<User> ul = userService.findByExample(user, 0, 100);
		assertTrue(ul.size() > 1);
		
		user.setFirstName("firstName");
		ul = userService.findByExample(user, 0, 100);
		assertTrue(ul.size() > 1);
	}
	
}
