package simulator.model;

import java.util.*;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String, Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if (ws != null) this.ws = ws;
		else throw new IllegalArgumentException("Weather cannot be a null value");
		}
		
	

	@Override
	void execute(RoadMap map) {
		//traverse list ws
		for (Pair<String,Weather> pair: ws) {
			if (map.getRoad(pair.getFirst()).equals(null))
				throw new IllegalArgumentException ("The road does not exist in the road map (pair does not exist)");
			else {
				map.getRoad(pair.getFirst()).setWeather(pair.getSecond());
			}
		}

	}



	@Override
	public String toString() { //TO DO
		return null;
	}

}
