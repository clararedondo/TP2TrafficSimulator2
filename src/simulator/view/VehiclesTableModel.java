package simulator.view;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;


public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Vehicle> vehicles;
	private String[] columnLabels = {"Id", "Location/Status", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};
	
	public VehiclesTableModel (Controller control) {
		vehicles = new ArrayList<Vehicle>();
		control.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return vehicles.size();
	}

	@Override
	public int getColumnCount() {
		return columnLabels.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnLabels[columnIndex];
	}

	
	//ILLEGAL TO GETCLASS... Not sure how. Potentially a getter in every object type?
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnLabels[columnIndex].getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(rowIndex == 0) {
			return false;
		}
		else if(columnIndex == 0 || columnIndex == 2 || columnIndex == 4 || columnIndex == 7){
			return false;
		}
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valueAt = null;
		
		switch(columnIndex) {
		//ID
		case 0:
			valueAt = vehicles.get(rowIndex).getId();
			break;
		//Status
		case 1:
			valueAt = vehicles.get(rowIndex).getStatus().toString();
			//switch(STATUS TYPES DIFFERENT STRINGS){
			//}
			break;
		//Itinerary
		case 2:
			valueAt = vehicles.get(rowIndex).getItinerary();
			break;
		//CO2 class
		case 3:
			valueAt = vehicles.get(rowIndex).getContClass();
			break;
		//Max Speed
		case 4:
			valueAt = vehicles.get(rowIndex).getMaxSpeed();
			break;
		//Speed
		case 5:
			valueAt = vehicles.get(rowIndex).getSpeed();
			break;
		//Total CO2
		case 6:
			valueAt = vehicles.get(rowIndex).getTotalCO2();
			break;
		//Distance
		case 7:
			valueAt = vehicles.get(rowIndex).getDistanceTraveled();
			break;
		}
		return valueAt;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(isCellEditable(rowIndex, columnIndex)) {
			switch(columnIndex) {
			//Status
			case 1:
				vehicles.get(rowIndex).setStatus((VehicleStatus) aValue); //parse as a VehicleStatus
				break;
			//CO2 class
			case 3:
				vehicles.get(rowIndex).setContClass((int) aValue);
				break;
			//Speed
			case 5:
				vehicles.get(rowIndex).setSpeed((int) aValue);
				break;
			//Total CO2
			case 6:
				vehicles.get(rowIndex).setTotalCO2((int) aValue);
				break;
			}
		}
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.vehicles= map.getVehicles();
		this.fireTableDataChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.vehicles= map.getVehicles();
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.vehicles= map.getVehicles();
		this.fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.vehicles= map.getVehicles();
		this.fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.vehicles= map.getVehicles();
		this.fireTableDataChanged();
		
	}

	//maybe? 
	@Override
	public void onError(String err) {
		throw new IllegalArgumentException(err);
	}

}
