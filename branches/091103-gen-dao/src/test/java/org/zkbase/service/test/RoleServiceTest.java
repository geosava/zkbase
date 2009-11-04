package org.zkbase.service.test;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.jpa.AbstractJpaTests;
import org.zkbase.dao.RoleDao;
import org.zkbase.model.Role;
import org.zkbase.service.RoleService;

import com.trg.search.Filter;
import com.trg.search.MetadataUtil;
import com.trg.search.Search;
import com.trg.search.jpa.JPASearchProcessor;

public class RoleServiceTest extends AbstractJpaTests {

	public RoleServiceTest() {
		this
				.setAutowireMode(AbstractDependencyInjectionSpringContextTests.AUTOWIRE_BY_NAME);
	}

	public String[] getConfigLocations() {
		return new String[] { "classpath:data-source-context.xml",
				"classpath:spring-context.xml" };
	}

	protected RoleService roleService;

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Role newRole(String name, String description) {
		Role r = new Role();
		r.setName(name);
		r.setDescription(description);
		return r;
	}

	public void testConfig() {
		assertNotNull(roleService);
	}

	public void testDataAccess() {
		List<Role> roles = roleService.findAll();
		assertNotNull(roles);
	}

	public void testCRUD() {
		Role r1 = newRole("name1", "desc1");
		Role r2 = newRole("name2", "desc2");
		roleService.persist(r1);
		roleService.persist(r2);
		List<Role> roles = roleService.findAll();
		assertNotNull(roles);
		assertTrue(roles.size() == 2);
		assertTrue(roles.get(0).getName().equals("name1")
				&& roles.get(0).getDescription().equals("desc1"));
		assertTrue(roles.get(1).getName().equals("name2")
				&& roles.get(1).getDescription().equals("desc2"));
		assertTrue(r1.equals(roles.get(0)));
		assertTrue(r2.equals(roles.get(1)));

		Long r2id = r2.getId();
		r2.setName("name3");
		roleService.merge(r2);
		r2 = roleService.findById(r2id);
		assertTrue(r2.getName().equals("name3"));

		boolean ret = roleService.delete(r1.getId());
		assertTrue(ret);
		ret = roleService.delete(r2.getId());
		assertTrue(ret);
		roles = roleService.findAll();
		assertNotNull(roles);
		assertTrue(roles.size() == 0);
	}

	public void testCount() {
		int i = roleService.count();
		assertTrue(i == 0);
		Role r1 = this.newRole("name1", "description1");
		roleService.persist(r1);
		i = roleService.count();
		assertTrue(i == 1);
		roleService.delete(r1.getId());
		i = roleService.count();
		assertTrue(i == 0);
	}

	public void testSearch() {
		Role r1 = newRole("name1", "desc1");
		Role r2 = newRole("name2", "desc2");
		Role r3 = newRole("xxx", "yyy");
		roleService.persist(r1);
		roleService.persist(r2);
		roleService.persist(r3);

		Role ex1 = newRole("xxx", "yyy");
		Role ex2 = newRole("name", null);
		Role ex3 = newRole(null, "desc");

		List<Role> restultList = roleService.findByExample(ex1);
		assertTrue(restultList.size() == 1);
		restultList = roleService.findByExample(ex2);
		assertTrue(restultList.size() == 2);
		restultList = roleService.findByExample(ex3);
		assertTrue(restultList.size() == 2);
	}

}
