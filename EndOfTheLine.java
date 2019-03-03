
public class EndOfTheLine extends RailwaySegment{

	public EndOfTheLine() {
		super();
		this.setName("End of the Line");
		this.setNext(null);
		this.setCapacity(-1); //capacity of -1 so that it will never be at capacity (threads can always enter)
	}

}
