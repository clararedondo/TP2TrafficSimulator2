package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event>{

	public NewCityRoadEventBuilder() {
		super("new_city_road"); //type
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		return new NewCityRoadEvent(data.getInt("time"), data.getString("id"), data.getString("src"), data.getString("dest"), 
		data.getInt("length"), data.getInt("co2limit"), data.getInt("maxspeed"), Weather.valueOf(data.getString("weather").toUpperCase()));

	}

}
