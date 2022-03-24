package simulator.model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> jList;
	private List<Road> rList;
	private List<Vehicle> vList;
	private Map<String,Junction> jMap;
	private Map<String,Road> rMap;
	private Map<String,Vehicle> vMap; 
	
	protected RoadMap() {
		jList = new ArrayList<Junction>();
		rList = new ArrayList<Road>();
		vList = new ArrayList<Vehicle>();
		jMap = new HashMap<String,Junction>();
		rMap = new HashMap<String,Road>();
		vMap = new HashMap<String,Vehicle>();
	}
	
	public void addJunction(Junction j) {
		if (!jMap.containsKey(j.getId())) {
			jList.add(j);
			jMap.put(j.getId(), j);
		}
		else throw new IllegalArgumentException("There exists a junction with the same identifier");
	}
	
	public void addRoad(Road r) {
		if (rMap.containsKey(r.getId())) throw new IllegalArgumentException("There exists a road with the same identifier");
		if (!jMap.containsKey(r.getSrc().getId()) || !jMap.containsKey(r.getDest().getId())) throw new IllegalArgumentException ("The junction that you are looking for does not exist");
		else {
			rList.add(r);
			rMap.put(r.getId(), r);
		}
	}
	
	public void addVehicle(Vehicle v) {
		boolean ok = false;
		
		
		if (vMap.containsKey(v.getId())) throw new IllegalArgumentException("There exists a vehicle with the same identifier");
		//check if itinerary is valid
		for (int i = 0; i < v.getItinerary().size() - 1; i++) {
//			if (v.getItinerary().get(i) != null) {
				ok = rMap.containsKey(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)).getId());
//			}
		}
		if(ok) {
			vList.add(v);
			vMap.put(v.getId(), v);
		}
		else throw new IllegalArgumentException("The itinerary is invalid");
	}
	
	public Junction getJunction(String id) {
		if (jMap.containsKey(id)) {
			return jMap.get(id);
		}
		else {
			return null;
		}		
	}
	
	public Road getRoad(String id) {
		if (rMap.containsKey(id)) {
			return rMap.get(id);
		}
		else {
			return null;
		}
	}
	
	public Vehicle getVehicle(String id) {
		if (vMap.containsKey(id)) {
			return vMap.get(id);
		}
		else {
			return null;
		}
	}
	

	public List<Junction>getJunctions(){
		
		return Collections.unmodifiableList(new ArrayList<>(jList));
		
	}
	
	
	public List<Road>getRoads(){
		
		return Collections.unmodifiableList(new ArrayList<>(rList));
		
	}
	
	
	public List<Vehicle>getVehicles(){
		
		return Collections.unmodifiableList(new ArrayList<>(vList));
		
	}
	
	public void reset() {
		jList.clear();
		rList.clear();
		vList.clear();
		jMap.clear();
		rMap.clear();
		vMap.clear();
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		
		JSONArray j = new JSONArray();
		
		for (int i = 0; i < this.getJunctions().size(); ++i) {
		//	jo.put("junctions", j.put(jList.get(i).report()));
			j.put(this.getJunctions().get(i).report());
		}
		
		jo.put("junctions", j);
		
		JSONArray r = new JSONArray();
		
		for (int i = 0; i < this.getRoads().size(); ++i) {
//			jo.put("roads", r.put(rList.get(i).report()));
			r.put(this.getRoads().get(i).report());
		}
		
		jo.put("roads", r);
	
		
		JSONArray v = new JSONArray();
		
		for (int i = 0; i < this.getVehicles().size(); ++i) {
//			jo.put("vehicles", v.put(vList.get(i).report()));
			v.put(this.getVehicles().get(i).report());
		
		}	
		
		jo.put("vehicles", v);
			
		
		return jo;
		
	}
	
}
