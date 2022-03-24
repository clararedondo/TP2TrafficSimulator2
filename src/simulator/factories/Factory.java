package simulator.factories;

import org.json.JSONObject;

public interface Factory<T> {
	public T createInstance(JSONObject info); //Json struct must include two keys: type and data
}
