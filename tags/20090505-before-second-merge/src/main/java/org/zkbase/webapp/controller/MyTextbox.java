package org.zkbase.webapp.controller;

import org.zkoss.zul.Textbox;

public class MyTextbox extends Textbox {
	
	private static int _instanceCount = 0;
	
	private int selected = 0;
	
	public MyTextbox() {
		_instanceCount++;
	}
	
	public void setSelected(int selected) {
		this.selected = selected;
		System.out.println("set:" + selected);
	}
	
	public int getSelected() {
		System.out.println("get: " + selected);
		return selected;
	}
	
	public void onCreate(){
		this.setValue("Textbox instance: " + _instanceCount);	
	}
}
