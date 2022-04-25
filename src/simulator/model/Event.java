package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int _time;
	protected String desc;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	public int getTime() {
		return _time;
	}
	
	public void setTime(int t) {
		_time = t;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String s) {
		desc = toString(s);
	}

	@Override
	public int compareTo(Event o) {
		int r = 0;
//		return one of {@code -1},
//			     * {@code 0}, or {@code 1} according to whether the value of
//			     * <i>expression</i> is negative, zero, or positive, respectively.	
		
		if (this._time < o._time ) {
			r = -1; 
		}
		else if (this._time == o._time) {
			r = 0;
		}
		else  r = 1;// this > o
		return r;
	}

	abstract void execute(RoadMap map);
	
	public String toString(String s){
		return ("CHANGE " + s + ": [");
	}
}
