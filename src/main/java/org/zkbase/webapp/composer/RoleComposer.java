package org.zkbase.webapp.composer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkbase.model.Role;
import org.zkbase.service.RoleService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;

public class RoleComposer extends GenericForwardComposer {

	private Textbox name;
	private Textbox description;
	private Textbox searchQuery;
	
	Listbox roleList;

	@Autowired
	RoleService roleService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		this.roleList.setItemRenderer(new ListitemRenderer(){

			@Override
			public void render(Listitem item, Object obj) throws Exception {
				Role role = (Role) obj;
				new Listcell(role.getName()).setParent(item);
				new Listcell(role.getDescription()).setParent(item);
			}});
		
	    this.roleList.setModel(new SimpleListModel(this.roleService.findAll()));		
	}

	public void onClick$add(Event e) {
		Role role = new Role();
		role.setName(name.getValue());
		role.setDescription(description.getValue());
		name.setValue("");
		description.setValue("");
		roleService.persist(role);
		
		this.roleList.setModel(new SimpleListModel(this.roleService.findAll()));
	}
	
	public void onClick$search(Event e) {
		Role example = new Role();
		example.setName(searchQuery.getValue());
		example.setDescription(searchQuery.getValue());		
		this.roleList.setModel(new SimpleListModel(this.roleService.findByExample(example)));
	}
}
