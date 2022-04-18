package simulator.view;

import java.util.ArrayList;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Event> events;
	private String[] columnLabels = {"Time", "Desc."};

	public EventsTableModel(Controller control) {
		events = new ArrayList<Event>();
		control.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return events.size();
	}

	@Override
	public int getColumnCount() {
		return columnLabels.length; //2
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnLabels[columnIndex];
	}

//	@Override
//	public Class<?> getColumnClass(int columnIndex) {
//		return columnLabels[columnIndex].getClass();
//	}
//
//	@Override
//	public boolean isCellEditable(int rowIndex, int columnIndex) {
//		return false;
//	} //can i delete this method?

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valueAt = null;
		switch(columnIndex) {
		case 0:
			valueAt = this.events.get(rowIndex).getTime();
			break;
		case 1:
			valueAt = this.events.get(rowIndex).toString();
			break;
		}
		return valueAt;
	}



	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.events = events;
		this.fireTableStructureChanged();
				
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.events = events;
		this.fireTableStructureChanged();
				
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.events = events;
		this.fireTableStructureChanged();
				
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.events = events;
		this.fireTableStructureChanged();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.events = events;
		this.fireTableStructureChanged();
		
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
