package org.zkbase.webapp.controller;

import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Textbox;

public class MyBandpopup extends Bandpopup {
	public void onCreate(){
		Textbox fillMe = (Textbox)this.getFellow("fillMe");
		fillMe.setValue("done");	
	}
}
