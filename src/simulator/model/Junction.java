package simulator.model;

import simulator.model.DequeuingStrategy;
import simulator.model.Junction;
import simulator.model.LightSwitchingStrategy;

import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Road;
import simulator.model.Vehicle;

public class Junction extends SimulatedObject{
	private List<Road> incRoads;
	private Map<Junction, Road> outRoads;
	private List<List<Vehicle>> queues;
	private Map<Road, List<Vehicle>> rqMap;
	private int greenLightInd;
	private int lsTime;
	private LightSwitchingStrategy lsStrat;
	private DequeuingStrategy dqStrat;
	private int x;
	private int y;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int x, int y) {
		super(id);
		this.lsStrat = lsStrategy;
		this.dqStrat = dqStrategy;
		this.x = x;
		this.y = y;
		
		if (id == null || id.isEmpty()) {throw new IllegalArgumentException("ERROR: id mustn't be empty.");}
		if (lsStrategy == null) throw new IllegalArgumentException ("ERROR: lsStrat value is null\n");
		if (dqStrategy == null) throw new IllegalArgumentException ("ERROR: dqStrat value is null\n");
		if (x < 0) throw new IllegalArgumentException ("ERROR: x-coordinate of junction must be positive\n");
		if (y < 0) throw new IllegalArgumentException ("ERROR: y-coordinate of junction must be positive\n");

		incRoads = new ArrayList<Road>();
		queues = new ArrayList<List<Vehicle>>();
		rqMap = new HashMap<Road,List<Vehicle>>();
		outRoads = new HashMap<Junction,Road>();
		greenLightInd = -1;
	}

	protected void addIncomingRoad(Road r){
		if(r.getDest()==this) {
			incRoads.add(r);
			LinkedList<Vehicle> rQueue = new LinkedList<Vehicle>();
			queues.add(rQueue);//add this road queue to the list of queues
			rqMap.put(r, rQueue);
		}
		else {
			throw new IllegalArgumentException("Wrong incoming road for this junction.");
		}
	}
	
	protected void addOutgoingRoad(Road r){
		if(r.getSrc()._id == this._id ) {
			outRoads.put(r.getDest(), r);//unsure
		}
		else {
			throw new IllegalArgumentException("Wrong outgoing road for this junction.");
		}
	}

	protected void enter(Vehicle v){
//		Road r = v.getRoad();
//		List<Vehicle> q = r.getVehicles();
//		q.add(v);
		rqMap.get(v.getRoad()).add(v);
	}

	protected Road roadTo(Junction j){
		return outRoads.get(j);
	}

	public JSONObject report(){
		JSONObject jo = new JSONObject();
	
		jo.put("id", getId());
		if(greenLightInd >= 0) {
		jo.put("green", incRoads.get(greenLightInd).getId());
		}
		else { jo.put("green", "none"); }
		
		//queues
		JSONArray qs = new JSONArray();
		
		
	
		//for every road put in road list
		for (int i = 0; i < incRoads.size(); i++) {
			
			JSONArray vs = new JSONArray(); 	 //vehicles in queues
			JSONObject rs = new JSONObject(); //roads of these queues
			
			for (int j = 0; j < rqMap.get(incRoads.get(i)).size(); j++) {
				vs.put(rqMap.get(incRoads.get(i)).get(j).getId());
			}
			
			
			rs.put("road", incRoads.get(i).getId());
			rs.put("vehicles", vs);
			qs.put(rs);
		
		}
		jo.put("queues", qs);
	
		return jo;
			
	}

	@Override
	protected void advance(int time) {
		//throw new Illegal
	
		if(greenLightInd != -1 && !queues.isEmpty()) {
			List<Vehicle> q = rqMap.get(incRoads.get(greenLightInd));
			
			if (!q.isEmpty()) {
				List<Vehicle> l = dqStrat.dequeue(q); //list of vehicles that should adv
				
				for(Vehicle v : l) {
					v.moveToNextRoad();
					q.remove(v);
					rqMap.get(incRoads.get(greenLightInd)).remove(v); //new
					}
			}
		}
	
		int ind = lsStrat.chooseNextGreen(incRoads, queues, greenLightInd, lsTime, time);
		if(ind != greenLightInd) {
			greenLightInd = ind;
			lsTime = time;
		}
	
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getGreenLightIndex() {
		return greenLightInd;
	}

	public List<Road> getInRoads() {
		return incRoads;
	}
	
	
	
}
