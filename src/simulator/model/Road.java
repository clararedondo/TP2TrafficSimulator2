package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	private Junction srcJunc;
	private Junction destJunc;
	private int length; //meters
	private int maxSpeed; //permitted for road
	private int currSpeedLimit; //initially equal to maxSpeed
	private int contLimit; //limits applied to reduce contamination
	private Weather weather;
	private int totalCont; //accumulated 
	private List <Vehicle> vehicles;
    private Comparator<Vehicle> cmp;

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id);
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.currSpeedLimit = maxSpeed; //initially equal to maxSpeed
		this.contLimit = contLimit;
		this.weather = weather;
		//c = new Comparator<Vehicle>();
		
		if (id == null || id == "" || maxSpeed<=0 || contLimit<=0 || length <=0 || srcJunc == null || destJunc == null || weather == null) {
			throw new IllegalArgumentException("Incorrect parameters in Vehicle \n");
		}
		
		vehicles = new ArrayList<Vehicle>();
		getDest().addIncomingRoad(this);
		getSrc().addOutgoingRoad(this);
		
		cmp = new Comparator<Vehicle>() {

			@Override
			public int compare(Vehicle v1, Vehicle v2) {
				return v2.compareTo(v1);
			}};
	}
	
//methods
	
	public void enter(Vehicle v){ //throws exception
		if(v.getSpeed()!=0||v.getLocation()!=0) {
			throw new IllegalArgumentException("Vehicle's arguments are not correct to be in this method.");
		}
		else {
			vehicles.add(v);
		}
	}

	public void exit(Vehicle v){
		removeVehicles(v);
	}

	public void addContamination(int c){ //throws exception
		//adds c units to totalCont
		//check c non-neg
		if(c<0) {
			throw new IllegalArgumentException("Contamination value must be a positive value.");
		}
		else setTotalCO2(getTotalCO2() + c);
	}

	public abstract void reduceTotalContamination();
	public abstract void updateSpeedLimit();
	public abstract int calculateVehicleSpeed(Vehicle v);
	
	@Override
	public void advance(int time){
		reduceTotalContamination(); //implementation in roadTypes
		updateSpeedLimit();
		
		
		
		for(Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		vehicles.sort(cmp);
	
	
	}
	
	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();

		jo.put("id", this._id);
		jo.put("speedlimit", currSpeedLimit);
		jo.put("co2", totalCont);
		jo.put("weather", weather.toString());
		
		
		JSONArray v = new JSONArray();
		for (int i = 0; i < getVehicles().size(); ++i) {
			v.put(getVehicles().get(i).getId());
		}
		
		jo.put("vehicles", v);
	
		return jo;
	}
	
	public int getLength(){
		return length;
	}

	public Junction getDest(){
		return destJunc;
	}

	public Junction getSrc(){
		return srcJunc;
	}

	public Weather getWeather(){
		return weather;
	}
	
	public  void setWeather(Weather w){
		if(w == null) {
			throw new IllegalArgumentException("Weather cannot be a null value.");
		}
		else weather = w;
	}

	public int getContLimit(){
		return contLimit;
	}

	public int getMaxSpeed(){
		return maxSpeed;
	}
	
	public void setMaxSpeed(int i) {
		maxSpeed =+ i;
	}
	
	public int getCurrSpeedLimit() {
		return currSpeedLimit;
	}

	public void setCurrSpeedLimit(int i) {
		currSpeedLimit = i;
	}

	public int getTotalCO2(){
		return totalCont;
	}
	
	public void setTotalCO2(int i) {
		totalCont = i;
		
	}

	public int getSpeedLimit(){
		return currSpeedLimit;
	}
	
	public void appendVehicles(Vehicle v){
		vehicles.add(v);
	}
	
	public void removeVehicles(Vehicle v){
		vehicles.remove(v);
	}


	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(new ArrayList<>(vehicles));

	}
}