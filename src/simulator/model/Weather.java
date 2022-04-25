package simulator.model;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;

	public static Weather parse(String selectedItem) {
		return Weather.valueOf(selectedItem);
	}
}
