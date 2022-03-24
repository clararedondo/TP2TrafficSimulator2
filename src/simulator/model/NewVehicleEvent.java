package simulator.model;

import java.util.*;

public class NewVehicleEvent extends Event {

	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	private List<Junction> it;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed= maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
		it = new ArrayList<Junction>();
		
		}

	@Override
	void execute(RoadMap map) {
		for (String s: itinerary) {
			if (map.getJunction(s).equals(null)) throw new IllegalArgumentException ("The junction does not exist.");
			else {	
				it.add(map.getJunction(s));
			}
		}
			
		Vehicle v = new Vehicle(id, maxSpeed, contClass, it);
		
		map.addVehicle(v);
	//	map.getVehicle(id).moveToNextRoad();
		v.moveToNextRoad(); //new
	}

}
