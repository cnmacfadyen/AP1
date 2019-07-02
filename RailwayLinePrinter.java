
public class RailwayLinePrinter implements Runnable {
	private RailwaySegment currentPos;
	private RailwaySegment startPos;
	
	@Override
	public void run() {
		
		currentPos = RailwayLine.glasgow;
		startPos = RailwayLine.glasgow;
		RailwayLine.setup();
	
		try {
			while(true) {
				for(int i=0;i<RailwayLine.totalTracksAndStations-1;i++) { //print all but the last station
					if(currentPos.getNext()!=null) {	
						if(currentPos!=null) { 
							System.out.print(currentPos.toString());
						}						
						currentPos = currentPos.getNext();	
					}else if(currentPos.getNext()==null) {
						currentPos = startPos; //reset back to the starting position
						System.out.print("\n");	
						break;
					}
				}
				Thread.sleep(100);
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
