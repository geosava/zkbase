package org.zkbase.webapp.controller.test;

import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class McBandbox extends HtmlMacroComponent {

	@Override
	public void afterCompose() {		
		super.afterCompose();
		Textbox tb = (Textbox)this.getFellowIfAny("tb");
		if (tb != null)
			tb.setValue("sdf");		
	}
	
	public String getTest() {
		return "asdf";
	}
	
	public void setTest(Label value) {
		System.out.println(value.getValue());
	}
	
}
