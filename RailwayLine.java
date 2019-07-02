
public class RailwayLine {
	protected static final int totalTracksAndStations = 8;
	protected static final Station glasgow = new Station("Glasgow", 4, 600); //name, capacity, length
	protected static final Track track1 = new Track(1000, 1); 
	protected static final Station dunkeld = new Station("Dunkeld & Birnam", 3, 200);
	protected static final Track track2 = new Track(2000, 1);
	protected static final Station kingussie = new Station("Kingussie", 2, 100);
	protected static final Track track3 = new Track(3000, 1);
	protected static final Station inverness = new Station("Inverness", 4, 200);
	protected static final EndOfTheLine end = new EndOfTheLine();
	
	public static void setup() { 
		
		//set up the railway line with next and previous segments
		
		glasgow.setNext(track1);
		glasgow.setPrevious(null); //this is the starting position, so doesn't have a previous segment
		
		track1.setNext(dunkeld);
		track1.setPrevious(glasgow);
		
		dunkeld.setNext(track2);
		dunkeld.setPrevious(track1);
		
		track2.setNext(kingussie);
		track2.setPrevious(dunkeld);
		
		kingussie.setNext(track3);
		kingussie.setPrevious(track2);
		
		track3.setNext(inverness);
		track3.setPrevious(kingussie);
		
		inverness.setNext(end);
		inverness.setPrevious(track3);
		
		end.setPrevious(inverness); //next is set to null in this segment's constructor
	}	
}
