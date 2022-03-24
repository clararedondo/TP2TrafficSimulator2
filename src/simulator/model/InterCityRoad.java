package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		// TODO Auto-generated method stub
		int x = 0; 
		
		switch (getWeather()) {
		case SUNNY: x = 2;
		break;
		
		case CLOUDY: x = 3;
		break;
		
		case RAINY: x = 10;
		break;
		
		case WINDY: x = 15;
		break;
		
//		case STORM: x = 20;
//		break;
//		
		default:
			x = 20;
		}
		int newC = (int)(((100.0-x) / 100.0) * getTotalCO2());
		setTotalCO2(newC);
		
	}

	
	@Override
	public void updateSpeedLimit() { //sets speed to 50%
		// TODO Auto-generated method stub
		if (getTotalCO2() > getContLimit()) {
			setCurrSpeedLimit(getMaxSpeed() / 2);
		}
		else { setCurrSpeedLimit(getMaxSpeed()); 
		}
	}


	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub

		int speed;
		if( getWeather() == Weather.STORM) {
		speed = (int) (getCurrSpeedLimit()*0.8);
		
		}
		else speed = getCurrSpeedLimit();
		return speed;
	}
	

}
