package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class"); //type
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		JSONArray array = data.getJSONArray("info");
		List<Pair<String, Integer>> pair = new ArrayList<>();
		
		for (int i = 0; i < array.length(); ++i) {
			pair.add(new Pair<>(array.getJSONObject(i).getString("vehicle"),array.getJSONObject(i).getInt("class")));
		}
		 return new SetContClassEvent(data.getInt("time"), pair);
	}

}
