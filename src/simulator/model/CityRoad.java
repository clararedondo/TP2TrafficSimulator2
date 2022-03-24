package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		// TODO Auto-generated method stub
		int x = 0;
		int tc = 0;
		
		switch(getWeather()) {
		
		case WINDY: x = 10;
		break;
		
		case STORM: x = 10;
		break;
		
		default: x = 2;
			break;
		
		}
	
		tc = getTotalCO2() - x;
		if (tc < 0) { // throw new IllegalArgumentException("Contamination limit must be a positive number"); }
		 tc = 0;
		}
		setTotalCO2(tc);
		
	}

	@Override
	public void updateSpeedLimit() {
		// TODO Auto-generated method stub
		setCurrSpeedLimit(getMaxSpeed());
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub
		return (int)(((11.0-v.getContClass())/11.0)*this.getSpeedLimit());
	}

}
