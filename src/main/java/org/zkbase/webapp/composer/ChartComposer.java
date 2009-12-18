package org.zkbase.webapp.composer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.SimpleXYModel;

public class ChartComposer extends GenericForwardComposer {

	//Flashchart chartXY;
	Flashchart chartPie;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		SimplePieModel modelPie = new SimplePieModel();
		
		modelPie.setValue( "Java", new Integer(500));
		modelPie.setValue( "PHP", new Integer(600));
		modelPie.setValue( "ASP", new Integer(400));
		modelPie.setValue( "C++", new Integer(1200));
		chartPie.setModel(modelPie);
		
//		SimpleXYModel modelXY = new SimpleXYModel();
//		modelXY.addValue("Java",new Integer(2004), new Integer(500));
//		modelXY.addValue("PHP",new Integer(2005), new Integer(600));
//		modelXY.addValue("ASP",new Integer(2006), new Integer(400));
//		modelXY.addValue("C++",new Integer(2007), new Integer(1200));
//		chartXY.setModel(modelXY);
		
	}

}
