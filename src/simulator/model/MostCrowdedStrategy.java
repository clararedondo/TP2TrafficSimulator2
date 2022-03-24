package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int timeSlot;
	int next = 0;
			
	public MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		
		
		if(roads.isEmpty()) {
			next = -1;
		}
		else if(currGreen == -1) {
			for (int i = 1; i < qs.size(); ++i) {
				if (qs.get(i - 1).size() < qs.get(i).size()) {
					next = i;
				}
			}
			
		}
		else if((currTime-lastSwitchingTime)<timeSlot) {
			next = currGreen;
		}
		
		else {
			
			next = (currGreen + 1) % qs.size();
			for (int i = 0; i < qs.size(); i++) {
				if (qs.get(i).size() < qs.get((currGreen+i+2) % qs.size()).size()) {
					next = (currGreen+i+2) % qs.size();
				
				}
			}
			
//			for (int i = currGreen; i < qs.size() - 1;  i = (i +1)%qs.size()) {
//				if (qs.get(i).size() > qs.get((i +1)%qs.size()).size()) {
//					next = i;
//				}
//			}
		}

		return next;
	}

}
