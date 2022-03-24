package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss"); //type
		}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeslot = 1;
		
		if (data.has("timeslot")) {
			timeslot = data.getInt("timeslot");
		}
		
		return new RoundRobinStrategy(timeslot);
		
		
	
	}

}
