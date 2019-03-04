import java.util.Arrays;

public class RailwayLinePrinter implements Runnable {

	private RailwaySegment currentPos;
	private RailwaySegment startPos;
	
	@Override
	public void run() {
		
		currentPos = RunMe.glasgow;
		startPos = RunMe.glasgow;
		RunMe.setup();
	
		try {
			while(true) {
				for(int i=0;i<RunMe.totalTracksAndStations-1;i++) { //print all but the last station
					if(currentPos.getNext()!=null) {	
						if(currentPos!=null) { 
							System.out.print(currentPos.toString());
						}						
						currentPos = currentPos.getNext();	
					}else if(currentPos.getNext()==null) {
						currentPos = startPos; //reset back to the starting position
						System.out.print("\n");		
//						break;
					}
				}
				Thread.sleep(100); //sleep for half a second
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
