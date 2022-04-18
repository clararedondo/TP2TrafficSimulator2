package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
		private List<Junction> itinerary;
	private int maxSpeed; 
	private int currentSpeed;
	private Road road;
	private int location; //on road distancia desde inicio de carretera
	private int contClass; //from 0 to 10
	private int totalCont;
	private int totalTraveledDistance;
	private VehicleStatus status;
//	private int lastJuncInd;
	private int itineraryIdx;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction>
	itinerary) {
		super(id);
		if (id == null || id.isEmpty() || maxSpeed<=0 || contClass<0 || contClass>10 || itinerary.size()<2) {
			throw new IllegalArgumentException("Incorrect parameters in Vehicle \n");
		}
		setSpeed(0);
		setStatus(VehicleStatus.PENDING);
		this.maxSpeed = maxSpeed; // passed in with the id?

		totalCont = 0;
	//	lastJuncInd = 0;
		//new
		itineraryIdx = 0; 
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
	}
	
	private void setLocation(int i) {
		location = i;
	}


	public void setSpeed(int d) { //changed to public idk if thats ok
		//fill
		if(d<0) {
			throw new IllegalArgumentException("Speed cannot be negative.");
		}
		if(this.getStatus()==VehicleStatus.TRAVELING) {
			if(d>maxSpeed){
				currentSpeed = maxSpeed;
			}
			else{
				currentSpeed = d;
			}
		}
//		if(d==0) {
//			setStatus(VehicleStatus.TRAVELING);
//		}
		
	}
	
	public void setContClass(int c) {
		if(c<0 || c>10) {
			throw new IllegalArgumentException("Contamination class value must be between 0 and 10 inclusive.");
		}
		contClass = c;
	}
	
	public void setTotalCO2(int c) {
		totalCont = c;
	}
	
	public void setStatus(VehicleStatus vs) {
		status = vs;
	}

	@Override
	void advance(int time) {
		
		if(status == VehicleStatus.TRAVELING) {
			int prevLocation = getLocation(); 

			//update loc to current+speed
			if(getLocation()+getSpeed() < road.getLength()) {
				setLocation(getLocation()+getSpeed());
				
				//calculate dist travelled
				int d = getLocation() - prevLocation;
				int c = d * getContClass();
				setTotalCO2(getTotalCO2() + c);
				
				this.totalTraveledDistance += d;
				road.addContamination(c);
			}
			else {  //location >= road.getLength()
				setLocation(road.getLength());
				
				//calculate dist travelled
				int d = getLocation() - prevLocation;
				int c = d * getContClass();
				setTotalCO2(getTotalCO2() + c);
				
				this.totalTraveledDistance += d;
				road.addContamination(c);
				
				road.getDest().enter(this);
				setSpeed(0);
				setStatus(VehicleStatus.WAITING);
			}
		}

	}
	
	void moveToNextRoad() {
		if(status != VehicleStatus.PENDING && status != VehicleStatus.WAITING) {
			throw new IllegalArgumentException("Method moveToNextRoad should not be activated with this status.");
		}
		
		else if(status == VehicleStatus.WAITING) {
			
			//exit previous roa
			road.exit(this);
			setLocation(0);
			//check if end of itinerary
			if ( itineraryIdx + 1 == getItinerary().size()) {
				setStatus(VehicleStatus.ARRIVED);
			}
			else { 
			//get next road
			this.road = itinerary.get(itineraryIdx).roadTo(itinerary.get(itineraryIdx +1));
			setStatus(VehicleStatus.TRAVELING);

//			setSpeed(0);
			road.enter(this);
			itineraryIdx++;

			
			}
		}
		
		//if it hasnt entered any road, itineraryIdx is 0 
		else if(status == VehicleStatus.PENDING) { 
			this.road = itinerary.get(itineraryIdx).roadTo(itinerary.get(itineraryIdx +1));
			road.enter(this);
			setStatus(VehicleStatus.TRAVELING);
			itineraryIdx++; 
		}

	}


	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", _id);
		jo.put("distance", totalTraveledDistance);
		jo.put("co2", totalCont);
		jo.put("class", contClass);
		jo.put("speed", currentSpeed);
		jo.put("status", status.toString()); //NEWWWWW
		
		if (status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
			jo.put("road", road.getId());
			jo.put("location", location);
		}
	
		return jo;
	}
	
	public int getLocation(){
		return location;
	}
	
	public int getSpeed(){
		return currentSpeed;
	}
		
	public int getMaxSpeed(){
		return maxSpeed;
	}
	
	public int getContClass(){
		return contClass;
	}
	
	public VehicleStatus getStatus(){
		return status;
	}
	
	public int getTotalCO2(){
		return totalCont;
	}
	
	public int getDistanceTraveled() {
		return totalTraveledDistance;
	}
	
	public List<Junction> getItinerary(){
		return itinerary;
	}
	
	public Road getRoad(){
		return road;
	}

	public int compareTo(Vehicle o1) {
			return this.getLocation() - o1.getLocation();
		
	}


	

}
