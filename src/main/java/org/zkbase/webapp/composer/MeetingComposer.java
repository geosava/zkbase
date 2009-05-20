package org.zkbase.webapp.composer;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkbase.dao.EntityNotFoundException;
import org.zkbase.model.Meeting;
import org.zkbase.model.User;
import org.zkbase.webapp.model.MeetingModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;

public class MeetingComposer extends GenericForwardComposer {
	
	private Window meetingWin;

	@Autowired
	private MeetingModel meetingModel;
	AnnotateDataBinder binder;
	
	public void setNewMeeting() {
		Meeting meeting = new Meeting();
		meeting.setId(null);
		User user = new User();
		user.setId(null);
		meeting.setLeader(user);
		this.meetingModel.setSelected(meeting);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);		
		binder = new AnnotateDataBinder(meetingWin);
		this.setNewMeeting();
		binder.loadAll();
	}
	
	public void onClick$add(Event e) throws EntityNotFoundException {
		this.meetingModel.persist();
		this.setNewMeeting();
		binder.loadAll();
	}

	public void onClick$update(Event e) throws EntityNotFoundException {
		this.meetingModel.merge();
		binder.loadAll();
	}
	
	public void onClick$del(Event e) throws EntityNotFoundException {
		this.meetingModel.delete();
		this.setNewMeeting();
		binder.loadAll();
	}
	
	public void onClick$cancel(Event e) throws EntityNotFoundException {
		this.setNewMeeting();
		binder.loadAll();
	}
}