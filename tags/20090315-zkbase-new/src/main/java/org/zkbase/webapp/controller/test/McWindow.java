package org.zkbase.webapp.controller.test;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Window;

public class McWindow extends Window {
	public void testMc() {
		System.out.println("in test MC");
		Component c = this.getFellow("first");
		MyBandbox b = (MyBandbox)c.getFellow("bb");
		System.out.println(b.getTest());
	}
}
