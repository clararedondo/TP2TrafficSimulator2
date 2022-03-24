package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather"); //type
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		JSONArray array = data.getJSONArray("info");
		List<Pair<String, Weather>> pair = new ArrayList<>();
		
		for (int i = 0; i < array.length(); ++i) {
			pair.add(new Pair<>(array.getJSONObject(i).getString("road"), Weather.valueOf(array.getJSONObject(i).getString("weather"))));
		}
		 return new SetWeatherEvent(data.getInt("time"), pair);
	}

}
