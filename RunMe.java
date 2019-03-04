import java.util.ArrayList;
import java.util.Arrays;

public class RunMe {

	protected static final int totalTracksAndStations = 8;
	protected static final Station glasgow = new Station("Glasgow", 4, 600); //name, capacity, length
	protected static final Track track1 = new Track(1000, 1); 
	protected static final Station dunkeld = new Station("Dunkeld & Birnam", 3, 200);
	protected static final Track track2 = new Track(2000, 1);
	protected static final Station kingussie = new Station("Kingussie", 2, 100);
	protected static final Track track3 = new Track(3000, 1);
	protected static final Station inverness = new Station("Inverness", 4, 200);
	protected static final EndOfTheLine end = new EndOfTheLine();
	
	public static void setup() { //method to set up the railway line
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
	}	

	
	public static void main(String[] args) {

		setup();
		
		TrainCreator tc = new TrainCreator(); 
		Thread trainGenerator = new Thread(tc);
		RailwayLinePrinter rlp= new RailwayLinePrinter();
		Thread printer = new Thread(rlp);
		trainGenerator.start();
		printer.start();
		
		ArrayList<Train>test = new ArrayList<Train>();
		for(int i=0;i<2;i++) {			
			Train t = new Train(i, glasgow);
			test.add(t);
			System.out.println(t);
			Thread trains = new Thread(t);
			//trains.start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		RailwayLine r = new RailwayLine();
//		int total = r.getTotalNumberOfSegments();
//		
//		RailwaySegment currentPos = RunMe.glasgow;
//		RunMe.setup();
//		try {
//			while(true) {
//				for(int i=0;i<total-1;i++) { //print all but the last station
//					if(currentPos.getNext()!=null) {	
//						if(currentPos!=null) { 
//							System.out.print(currentPos.toString());
//						}						
//						currentPos = currentPos.getNext();	
//					}else if(currentPos.getNext()==null) {
//						currentPos = RunMe.glasgow; //reset back to the starting position
//						System.out.print("\n");		
//						break;
//					}
//				}
//				Thread.sleep(1000);
//			}
//		}catch(InterruptedException e) {
//			e.printStackTrace();
//		}


	}
}