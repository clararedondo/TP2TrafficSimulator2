package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder() {
		super("new_vehicle"); //type
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		JSONArray array = data.getJSONArray("itinerary");
		List<String> itineraryList = new ArrayList<>();
		
		
		for (int i = 0; i < array.length(); ++i) {
			itineraryList.add(array.getString(i));
		}
		
		
		return new NewVehicleEvent(data.getInt("time"),data.getString("id"),data.getInt("maxspeed"),data.getInt("class"),itineraryList);
	}

}
