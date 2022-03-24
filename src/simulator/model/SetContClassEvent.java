package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		if (cs != null) this.cs = cs;
		else throw new IllegalArgumentException("Weather cannot be a null value");
		}
		
	

	@Override
	void execute(RoadMap map) {
		//traverse list cs
		for (Pair<String,Integer> pair: cs) {
			if (map.getVehicle(pair.getFirst()).equals(null))
				throw new IllegalArgumentException ("The road does not exist in the road map (pair does not exist)");
			else {
				map.getVehicle(pair.getFirst()).setContClass(pair.getSecond());
			}
		}

	}


}
