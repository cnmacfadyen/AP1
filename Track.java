import java.util.ArrayList;

public class Track extends RailwaySegment { 
	private Train t;
	private String name;

	public Track(int length) {
		super(length);
		this.setCapacity(1);
		this.setName("track"); 
	}
}
