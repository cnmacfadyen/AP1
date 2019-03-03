
public class RailwayLine {
	//this class sets up the railway line
	
	private final int totalTracksAndStations = 8;

	private RailwaySegment[] railway = new RailwaySegment[totalTracksAndStations];
	
	private final Station glasgow = new Station("Glasgow", 4, 600);
	private final Track track1 = new Track(1000); 
	private final Station dunkeld = new Station("Dunkeld & Birnam", 3, 200);
	private final Track track2 = new Track(2000);
	private final Station kingussie = new Station("Kingussie", 2, 100);
	private final Track track3 = new Track(3000);
	private final Station inverness = new Station("Inverness", 4, 200);
	private final EndOfTheLine end = new EndOfTheLine();
	
	public int getTotalNumberOfSegments() {
		return totalTracksAndStations;
	} 
	
	public RailwaySegment setupLinkedList() {
		glasgow.setNext(track1);
		glasgow.setPrevious(null);
		
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
		
		end.setPrevious(inverness);
		
		return glasgow;
	}
	
	public void setupLine() {
		railway[0] = glasgow;
		railway[1] = track1;
		railway[2] = dunkeld;
		railway[3] = track2;
		railway[4] = kingussie;
		railway[5] = track3;
		railway[6] = inverness;
		railway[7] = end;
		
		for(int i=0;i<railway.length;i++) {
			if(i == railway.length-1) { //if it is the last segment on the line
				railway[i].setNext(null);
			}else {
				railway[i].setNext(railway[i+1]);
			}
		}
	}
	
	public RailwaySegment[] getRailwayLine() {
		this.setupLine();
		return railway;
	}
}
