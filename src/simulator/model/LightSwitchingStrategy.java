package simulator.model;

import java.util.List;

//interface uses two strategies, 'composition'

public interface LightSwitchingStrategy {
	int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime);
	
//	static LightSwitchingStrategy mostCrowdedStrategy() {
//		return ...
//	}
//	
//	static LightSwitchingStrategy roundRobinStrategy() {
//		return ...
//	}
	
}

