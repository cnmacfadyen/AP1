
public class Station extends RailwaySegment{
	private Train current;
	
	public Station(String name, int capacity, int length) {
		super(length, capacity);
		this.setName(name);
		this.setCapacity(capacity);
		this.current = this.getCurrentTrain();
	}
	
	public int timeSpentByTrain(Train t) {
		int timeSpent = (getLength()/t.getSpeed() + 5);
		return timeSpent;
	}
}
