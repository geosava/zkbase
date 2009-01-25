package org.zkbase.webapp.controller;

import org.zkoss.zul.Textbox;

public class MyTextbox extends Textbox {
	
	private static int _instanceCount = 0;
	public MyTextbox() {
		_instanceCount++;
	}
	
	public void onCreate(){
		this.setValue("Textbox instance: " + _instanceCount);	
	}
}
