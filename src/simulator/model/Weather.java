package simulator.model;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;

	public static Weather parse(Object selectedItem) {
		return Weather.class.cast(selectedItem);
	}
}
