import java.util.Arrays;

public class RailwayLinePrinter implements Runnable {
	
	private RailwaySegment currentPos;
	
	@Override
	public void run() {
		RailwayLine r = new RailwayLine();
		int total = r.getTotalNumberOfSegments();
		RailwaySegment startPos = r.setupLinkedList();		
		currentPos = startPos;
		try {
			while(true) {
				for(int i=0;i<total-1;i++) { //print all but the last station
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
				Thread.sleep(1000);
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
//		RailwayLine r = new RailwayLine();
//		RailwaySegment [] railway = r.getRailwayLine();
//		
//		try {
//			while(true) {
//				for(int i=0;i<railway.length-1;i++) { //print all but the last station
//					System.out.print(railway[i].toString());					
//					if(i==railway.length-2) { //if we reach the end of the line... 
//						System.out.print("\n");
//						Thread.sleep(100);
//					}
//				}
//			}
//		}catch(InterruptedException e) {
//				e.printStackTrace();
//		}
	}
}
