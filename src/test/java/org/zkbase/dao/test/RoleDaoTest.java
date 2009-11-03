package org.zkbase.dao.test;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.jpa.AbstractJpaTests;
import org.zkbase.dao.RoleDao;
import org.zkbase.model.Role;

import com.trg.search.Filter;
import com.trg.search.MetadataUtil;
import com.trg.search.Search;
import com.trg.search.jpa.JPASearchProcessor;

public class RoleDaoTest extends AbstractJpaTests {

	public RoleDaoTest() {
		this
				.setAutowireMode(AbstractDependencyInjectionSpringContextTests.AUTOWIRE_BY_NAME);
	}

	public String[] getConfigLocations() {
		return new String[] { "classpath:data-source-context.xml",
				"classpath:spring-context.xml" };
	}

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	protected JPASearchProcessor searchProcessor;

	public JPASearchProcessor getSearchProcessor() {
		return searchProcessor;
	}

	public void setSearchProcessor(JPASearchProcessor searchProcessor) {
		this.searchProcessor = searchProcessor;
	}

	protected MetadataUtil metadataUtil;

	public MetadataUtil getMetadataUtil() {
		return metadataUtil;
	}

	public void setMetadataUtil(MetadataUtil metadataUtil) {
		this.metadataUtil = metadataUtil;
	}
	
	protected RoleDao roleDao;	
	
	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao; 
	}
	
	public Role newRole(String name, String description) {
		Role r = new Role();
		r.setName(name);
		r.setDescription(description);
		return r;
	}
	
	public void testConfig() {
		assertNotNull(dataSource);
		assertNotNull(metadataUtil);
		assertNotNull(entityManagerFactory);
		assertNotNull(searchProcessor);		
		assertNotNull(roleDao);
	}
	
	public void testDataAccess() {
		List<Role> roles = roleDao.findAll();
		assertNotNull(roles);
	}
	
	public void testCRUD() {
		Role r1 = newRole("name1", "desc1");
		Role r2 = newRole("name2", "desc2");
		roleDao.persist(r1, r2);		
		List<Role> roles = roleDao.findAll();		
		assertNotNull(roles);
		assertTrue(roles.size() == 2);
		assertTrue(roles.get(0).getName().equals("name1") && roles.get(0).getDescription().equals("desc1"));
		assertTrue(roles.get(1).getName().equals("name2") && roles.get(1).getDescription().equals("desc2"));
		assertTrue(r1.equals(roles.get(0)));
		assertTrue(r2.equals(roles.get(1)));
		
		Long r2id = r2.getId();
		r2.setName("name3");
		roleDao.merge(r2);
		r2 = roleDao.find(r2id);
		assertTrue(r2.getName().equals("name3"));
		
		roleDao.remove(r1, r2);
		roles = roleDao.findAll();		
		assertNotNull(roles);
		assertTrue(roles.size() == 0);
	}
	
	public void testSearch() {
		Role r1 = newRole("name1", "desc1");
		Role r2 = newRole("name2", "desc2");
		Role r3 = newRole("name3", "desc3");
		roleDao.persist(r1, r2, r3);
		
		Role r1ex = newRole("name1", "desc1");
		Filter f1 = roleDao.getFilterFromExample(r1ex);
		List<Role> l1 = roleDao.search(new Search().addFilter(f1));
		assertNotNull(l1);
		assertTrue(l1.size() == 1);
		r1 = l1.get(0);
		assertTrue(r1.getName().equals("name1"));
		assertTrue(r1.getDescription().equals("desc1"));
	}
}
