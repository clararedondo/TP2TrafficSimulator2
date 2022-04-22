package simulator.view;

import java.util.ArrayList;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Junction> junctions;
	private String[] columnLabels = {"Id", "Green", "Queues"};
	private RoadMap roadMap;
	
	public JunctionsTableModel (Controller control) {
		junctions = new ArrayList<Junction>();
		control.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return junctions.size();
	}

	@Override
	public int getColumnCount() {
		return columnLabels.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnLabels[columnIndex];
	}

//	@Override
//	public Class<?> getColumnClass(int columnIndex) {
//		return columnLabels[columnIndex].getClass(); //illegal :(
//	}


	
//  @Override
//	public boolean isCellEditable(int rowIndex, int columnIndex) {
//		if(rowIndex == 0) {
//			return false;
//		}
//		//uneditable id
//		if (columnIndex == 0){
//			return false;
//		}
//		else return true;
//		
//	}

	@SuppressWarnings("null")
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valueAt = "";
		
		switch(columnIndex) {
		//ID
		case 0:
			valueAt = junctions.get(rowIndex).getId();
			break;
		//id of incoming road with green light (none if all red)
		case 1:
			int g = junctions.get(rowIndex).getGreenLightIndex();
			if(g >= 0) {
				valueAt = junctions.get(rowIndex).getInRoads().get(g).getId();
			}
			else { 
				valueAt = "None";
			}
			break;
		//queues
		case 2:
			//Object j = null;
			for (int i = 0; i < junctions.get(rowIndex).getInRoads().size(); i++) {
			//	String id= junctions.get(rowIndex).getInRoads().get(i).getId();
				//j = id + ":" + "[" +  roadMap.getJunction(junctions.get(rowIndex).getId()).getInRoads().get(Integer.valueOf(id)).getVehicles().toString() + "]";
				valueAt += junctions.get(rowIndex).getInRoads().get(i).getId() + ":" + junctions.get(rowIndex).getRoadMap().get(junctions.get(rowIndex).getInRoads().get(i)).toString() + " ";
				//most definitely wrong but ive been trying too long and now i cant think of other ways
				//the goal is to run through all inc roads of junctions.get(rowIndex) and then 
				//display r1: [v1,v2,v3]  --> so name of that road and its queue
				//sos
			}
			break;
		}
			
		return valueAt;
	}

//	@Override
//	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//		// TODO Auto-generated method stub
//		//dont think we need this
//	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		this.fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		this.fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.junctions = map.getJunctions();
		this.fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		this.fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		this.fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
