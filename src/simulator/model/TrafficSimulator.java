package simulator.model;

import java.util.*;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	private RoadMap roadMap;
	private List<Event> eventList; 
	private int simTime;

	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>(); //sorted by time
		simTime = 0;
	}

	public void addEvent(Event e) {
	//trying things	
		if (e._time > simTime) {
		eventList.add(e);
		}
		else throw new IllegalArgumentException("Invalid execution time.");
	}

	public void advance() {
		//1 inc sim time
		simTime++;
		
		//2 execute events w current sim time & remove from list
//		for (Event e : eventList) {
//			if (e._time == simTime) {
//				e.execute(roadMap);
//				eventList.remove(e);
//			}
//		}
		for (int i = 0; i < eventList.size(); ++i) {
			if (!eventList.isEmpty()) {
				if (eventList.get(i)._time == simTime) {
					eventList.get(i).execute(roadMap);
					eventList.remove(i);
					i--;
				}
			}
			else {
				break;
			}
		}
		
		//3 calls advance method of all junctions
		for (Junction j: roadMap.getJunctions()) {
			j.advance(simTime);
		}
		
		//4 calls advance method of all roads
		for (Road r: roadMap.getRoads()) {
			r.advance(simTime);
		}
	}

	public void reset() { 
		roadMap.reset();
		eventList.clear();
		simTime = 0;
		
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("time", simTime);
		jo.put("state", roadMap.report());
		
		return jo;
	}
	

}
