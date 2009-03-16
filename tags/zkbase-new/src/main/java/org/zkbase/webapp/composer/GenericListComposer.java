package org.zkbase.webapp.composer;

import java.util.List;

import org.zkbase.service.SearchableService;
import org.zkbase.webapp.component.MainWindow;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

public abstract class GenericListComposer<T> extends GenericForwardComposer
		implements ListitemRenderer {

	protected Textbox glc_searchField;
	protected Paging glc_page;
	protected Listbox glc_listbox;
	protected ListModelList listModelList;

	protected SearchableService<T> service;
	protected T example;
	protected T selectedObject;

	@SuppressWarnings("unchecked")
	public GenericListComposer() {
		super();
	}

	public void onClick$search(Event e) {
		example = getSearchExample(glc_searchField.getValue());
		Long size = getModelSize();
		glc_page.setTotalSize(size.intValue());
		glc_page.setActivePage(0);
		buildList(0);
	}

	public void onClick$insert(Event e) {
		this.loadDetailPage(e, true);
	}

	public void onClick$edit(Event e) {
		this.loadDetailPage(e, false);
	}

	@SuppressWarnings("unchecked")
	public void onSelect$glc_listbox(Event e) {
		int index = this.glc_listbox.getSelectedIndex();
		selectedObject = (T) this.glc_listbox.getModel().getElementAt(index);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		this.service = this.loadSearchableService();

		if (this.service == null) {
			System.out.println("Searchable service could not be loaded");
		}

		listModelList = new ListModelList();

		final Long count = this.getModelSize();
		glc_page.setTotalSize(count.intValue());
		glc_page.setActivePage(0);
		glc_page.addEventListener("onPaging", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pgno = pe.getActivePage();
				int firstResult = pgno * glc_page.getPageSize();
				buildList(firstResult);
			}
		});

		glc_listbox.setItemRenderer(this);
		buildList(0);
	}
	
	private void loadDetailPage(Event e, boolean clear) {
		Page p = e.getPage();
		Desktop desktop = p.getDesktop();
		MainWindow mw = (MainWindow) desktop.getAttribute("mainWin");
		if (!clear)
			desktop.setAttribute("SELECTED_OBJECT", this.selectedObject);
		Window win = (Window) Executions.createComponents(getDetailsPage(), mw,
				null);
		win.setClosable(true);

		win.addEventListener("onClose", new EventListener() { 
			@Override
			public void onEvent(Event arg0) throws Exception {
				buildList(0);
			}
		});
		try {
			win.doModal();
		} catch (SuspendNotAllowedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	protected Long getModelSize() {
		if (example == null) {
			return service.count();
		} else {
			return service.countByExample(example);
		}
	}

	protected List<T> getModel(int firstResult) {
		List<T> list = null;
		if (example == null) {
			list = service.findAll(firstResult, glc_page.getPageSize());
		} else {
			list = service.findByExample(example, firstResult, glc_page
					.getPageSize());
		}
		return list;
	}

	private void buildList(int firstResult) {
		List<T> list = this.getModel(firstResult);
		listModelList.clear();
		listModelList.addAll(list);
		glc_listbox.setModel(listModelList);
	}

	abstract protected SearchableService<T> loadSearchableService();

	abstract protected T getSearchExample(String query);

	abstract protected String getDetailsPage();

	abstract public void render(Listitem listItem, Object data)
			throws Exception;
}
