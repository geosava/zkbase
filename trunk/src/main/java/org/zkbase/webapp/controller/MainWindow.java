package org.zkbase.webapp.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Window;

public class MainWindow extends Window {

	private static final long serialVersionUID = -2091092165757421532L;
	


	public void about() {
		Component menu = this.getFellow("content");
		Components.removeAllChildren(menu);		
		Window win = (Window)Executions.createComponents("about.zul", menu, null);
		win.setClosable(true);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
