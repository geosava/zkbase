package org.zkbase.webapp.composer;

import org.zkbase.model.User;
import org.zkbase.service.SearchableService;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class GenUserListComposer extends GenericListComposer<User> {
		

	public GenUserListComposer() {
		super();
	}

	@Override	
	protected User getSearchExample(String query) {
		User example = new User();
		example.setFirstName("%" + query + "%");
		example.setUsername("%" + query + "%");
		return example;
	}

	@Override
	public void render(Listitem listItem, Object data) throws Exception {
		User user = (User)data;
		new Listcell(user.getFirstName()).setParent(listItem);
		new Listcell(user.getLastName()).setParent(listItem);
	}

	@Override
	protected String getDetailsPage() {
		return "portal/user/user_details.zul";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected SearchableService<User> loadSearchableService() {		
		return (SearchableService<User>) SpringUtil.getBean("userService");
	}

}
