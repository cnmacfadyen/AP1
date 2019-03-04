import java.util.ArrayList;

public class Track extends RailwaySegment { 
	private Train t;
	private String name;

	public Track(int length, int capacity) {
		super(length, capacity);
		this.setCapacity(capacity);
		this.setName("track"); 
	}
}
