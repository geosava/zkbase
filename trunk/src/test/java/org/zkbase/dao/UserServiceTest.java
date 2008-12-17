package org.zkbase.dao;

import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.jpa.AbstractJpaTests;
import org.zkbase.model.User;
import org.zkbase.service.UserService;

public class UserServiceTest extends AbstractJpaTests {

	public UserServiceTest() {
		this
				.setAutowireMode(AbstractDependencyInjectionSpringContextTests.AUTOWIRE_BY_NAME);
	}

	public String[] getConfigLocations() {
		return new String[] { "classpath:dataSourceContext.xml",
				"classpath:spring-context.xml" };
	}
	
	UserService userService;		

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void testConfig() throws Exception {
		assertNotNull(userService);
		assertNotNull(userService.getBasicDao());
	}
	
	public void testStoreUser() throws Exception {
		User u = new User();
		u.setFirstName("firstName");
		u.setLastName("lastName");
		u.setPassword("password");
		u.setUserName("username");
		userService.persist(u);
		List<User> ul = userService.findAll();
		assertNotNull(ul);
		assertTrue(ul.size() == 1);
	}
}
