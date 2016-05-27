package com.ff7damage;

import java.util.Observable;
import java.util.Observer;

import com.ff7damage.view.CenterPanel;
import com.ff7damage.view.RightPanel;
import com.ff7damage.view.View;

public class Controller extends Observable implements Observer {

	private byte[] centerPanelData;
	private byte[] rightPanelData;
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Event){
			Event e = (Event) arg1;
			
			if(arg0 instanceof View || arg0 instanceof CenterPanel || arg0 instanceof RightPanel) {
				manageViewEvent(e);
			}
			else if(arg0 instanceof Model) {
				manageModelEvent(e);
			}
		}
	}

	private void manageModelEvent(Event e) {
		switch(e.getCode()) {
		case RESULT_FOR_CONTROLLER:
		{
			Event newEvent = new Event(Events.RESULT_FOR_VIEW, e.getParams()[0]);
			
			setChanged();
			System.out.println("Controller: sending results to View...");
			notifyObservers(newEvent);
			break;
		}
		default:
		{
			break;
		}
		}
		
	}

	private void manageViewEvent(Event e) {
		switch(e.getCode()) {
		case CHARACTER_CHANGED:
		{
			setChanged();
			System.out.println("Controller: requesting CenterPanel to update itself due to a character change in the left panel...");
			notifyObservers(new Event(Events.UPDATE_CENTER_PANEL, e.getParams()[0]));
			break;
		}
		case SENDING_CENTER_PANEL_DATA:
		{
			this.centerPanelData = (byte[]) (e.getParams()[0]);
			setChanged();
			System.out.println("Controller: requesting data from RightPanel...");
			notifyObservers(new Event(Events.PLEASE_SEND_RIGHT_PANEL_DATA, e.getParams()[0]));
			break;
		}
		case SENDING_RIGHT_PANEL_DATA:
		{
			this.rightPanelData = (byte[]) (e.getParams()[0]);
			sendDataToModel();
			break;
		}
		default:
		{
			break;
		}
		}
	}

	private void sendDataToModel() {
		Event e = new Event(Events.DATA_FOR_MODEL, this.centerPanelData, this.rightPanelData);
		
		setChanged();
		System.out.println("Controller: sending data to Model ...");
		notifyObservers(e);
	}
}