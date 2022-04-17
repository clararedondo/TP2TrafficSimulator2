package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	//reads events from given InputStream and adds to simulator
	//exec simulagtor x number of steps and prints diff states to given OutputStream
	private TrafficSimulator trafficSim;
	private Factory<Event> evFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {

		if (sim == null || eventsFactory == null) {
			throw new IllegalArgumentException("Incorrect parameters in Controller \n");
		}
		else {
			this.trafficSim = sim;
			this.evFactory = eventsFactory;
		}
	}
	
	public void loadEvents(InputStream in) throws IOException { //in includes text of a json struct, gotta conver it into jsonObj
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray e = jo.getJSONArray("events");
		Event ev;
		
		for (int i = 0; i < e.length(); i++) {
			ev = evFactory.createInstance(e.optJSONObject(i));
			trafficSim.addEvent(ev);
			//this.trafficSim.addEvent(this.evFactory.createInstance(e.optJSONObject(i)));
		}
		
	}
	
	public void loadEvents2(InputStream in, JSONArray events) {
		for (int i = 0; i < events.length(); i++) {
			trafficSim.addEvent(evFactory.createInstance(events.getJSONObject(i)));
		}
	}
	
	public void run(int n, OutputStream out) throws IOException {

		PrintStream p = new PrintStream(out);
		p.println("{\"states\": [");
		
		this.trafficSim.advance();

		
		for (int i = 0; i < n - 1; i++) {
			p.println(this.trafficSim.report() + ", ");
			this.trafficSim.advance();

		}
		
		p.println(this.trafficSim.report());
		p.println("]");
		p.println("}");
	}
	
	//PRACTICE 2 NEW METHODS
	
	//run method with only 1 arg
	public void run(int n) { //should probably throw exceptions
		try {
			for (int i = 0; i< n; ++i) {
				trafficSim.advance();
			}
		}
		catch(Exception e){
			throw e; ///??? parse
		}
	}
	
	public void reset() {
		this.trafficSim.reset();
	}
	
	public void addObserver(TrafficSimObserver o) {
		trafficSim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		trafficSim.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		trafficSim.addEvent(e);
	}
}
