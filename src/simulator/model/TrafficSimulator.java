package simulator.model;

import java.util.*;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	private RoadMap roadMap;
	private List<Event> eventList; 
	private List<TrafficSimObserver> obs;
	private int simTime;

	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>(); //sorted by time
		simTime = 0;
		//Pract2
		this.obs = new ArrayList<TrafficSimObserver>();
	}

	public void addEvent(Event e) {
	//trying things	
		if (e._time > simTime) {
		eventList.add(e);
		notifyEventAdded(e);
		}
		else notifyError("Invalid execution time.");
	}

	

	public void advance() {
		simTime++;
		notifyAdvanceStart();
		
		try {
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
		}catch(IllegalArgumentException e) {
				notifyError("A problem ocurred while playing the simulation.");
			//missing a throw?
		}
		
		notifyAdvanceEnd();
	}

	public void reset() { 
		roadMap.reset();
		eventList.clear();
		simTime = 0;
		notifyReset();
		
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("time", simTime);
		jo.put("state", roadMap.report());
		
		return jo;
	}

	
	//NEW
	@Override
	public void addObserver(TrafficSimObserver o) {
		if((o != null) && (!this.obs.contains(o))){
		this.obs.add(o);
		notifyRegister(o); //check which o we should pass it
		}
	}

	

	@Override
	public void removeObserver(TrafficSimObserver o) {
		if((o != null) && (this.obs.contains(o))){
		obs.remove(o);
		}
		
	}
	
	private void notifyAdvanceStart() {
		// TODO Auto-generated method stub
		for(TrafficSimObserver o: obs) {
			o.onAdvanceStart(roadMap, eventList, simTime);
		}
	}

	private void notifyAdvanceEnd() {
		for(TrafficSimObserver o: obs) {
			o.onAdvanceEnd(roadMap, eventList, simTime);
		
		}
	}
	
	private void notifyEventAdded(Event e) {
		for(TrafficSimObserver o: obs) {
			o.onEventAdded(roadMap, eventList, e, simTime);
		
		}
	}
	
	private void notifyReset() {
		for(TrafficSimObserver o: obs) {
			o.onReset(roadMap, eventList, simTime);
		
		}
	}
	
	//not sure where it goes
	//only new obs is notified
	private void notifyRegister(TrafficSimObserver o) {
			o.onRegister(roadMap, eventList, simTime);
		
	}
	
	private void notifyError(String string) {
		throw new IllegalArgumentException(string);
		
	}
}
