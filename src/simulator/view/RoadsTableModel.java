package simulator.view;

import java.util.ArrayList;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Road> roads;
	private String[] columnLabels = {"Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	
	public RoadsTableModel (Controller control) {
		roads = new ArrayList<Road>();
		control.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return roads.size();
	}

	@Override
	public int getColumnCount() {
		return columnLabels.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnLabels[columnIndex];
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnLabels[columnIndex].getClass(); //illegal
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) { //what is this?
		
		if(rowIndex == 0) {
			return false;
		}
		//uneditable id, length, c02limit
		if (columnIndex == 0 | columnIndex == 1 | columnIndex == 6){
			return false;
		}
		else return true;
		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valueAt = null;
		
		switch(columnIndex) { //shouldnt id be column 1? column 0 is the actual road? 
		//ID
		case 0:
			valueAt = roads.get(rowIndex).getId();
			break;
		//Length
		case 1:
			valueAt = roads.get(rowIndex).getLength();
			break;
		//Weather
		case 2:
			valueAt = roads.get(rowIndex).getWeather();
			break;
		//Max speed
		case 3:
			valueAt = roads.get(rowIndex).getMaxSpeed();
			break;
		//Current speed limit
		case 4:
			valueAt = roads.get(rowIndex).getCurrSpeedLimit();
			break;
		//Total CO2
		case 5:
			valueAt = roads.get(rowIndex).getTotalCO2();
			break;
		//C02 Limit
		case 6:
			valueAt = roads.get(rowIndex).getContLimit();
			break;
		}
		return valueAt;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (isCellEditable(rowIndex,columnIndex)){
			switch(columnIndex) {
			//Weather
			case 2: roads.get(columnIndex).setWeather((Weather) aValue); //parse
				break;
			//Max speed
			case 3: roads.get(columnIndex).setMaxSpeed((int) aValue);  //parse
				break;
			//Current speed limit
			case 4: roads.get(columnIndex).setCurrSpeedLimit((int) aValue); //parse
				break;
			//Total CO2
			case 5: roads.get(columnIndex).setTotalCO2((int) aValue); //parse 
			}
		}
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		this.fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		this.fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.roads = map.getRoads();
		this.fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		this.fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		this.fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
