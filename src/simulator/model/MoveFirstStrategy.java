package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> newL = new ArrayList<Vehicle>();
		if (!q.isEmpty()) {
			newL.add(q.get(0)); 
		}
		return newL;
	}
}
