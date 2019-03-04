import java.util.Random;

public class Train implements Runnable {
	public int number;
	private int speed;
	private String name;
	private String s;
	private RailwaySegment currentPosition;
	
	public Train(int number, RailwaySegment currentPosition) { 
		this.number=number;
		this.setRandomSpeed();
		this.currentPosition=currentPosition;
	}
	
	public int getSpeed() {
		return this.speed; 
	}
	
	public void setSpeed(int s) {
		this.speed = s;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public RailwaySegment getCurrentPosition() {
		return this.currentPosition;
	}
	
	public void setCurrentPosition(RailwaySegment r) {
		this.currentPosition=r;
	}
	
	public boolean isExpress() {
		if(speed>10) {
			return true;
		} else {
			return false;	
		}		
	}
	
	public void setRandomSpeed() {
		Random r = new Random();
		int rand = r.nextInt(2)+ 1; //randomly choose 1 or 2
		if(rand == 1) {
			this.setSpeed(10); //slow train
		}else {
			this.setSpeed(500); //fast train
		}		
	}
	
	public String printTrain(Track tr, Station s) {
		double trackTime = tr.timeSpentByTrain(this);
		double stationTime = s.timeSpentByTrain(this);
		return "I spend " + trackTime + " seconds on each track and " + stationTime + " seconds at each station.";
	}
	
	public String toString() {
		return "My name is " + this.getNumber() + " and I can travel at " + speed + "m/s";
	}

	@Override
	public void run() {

		RailwaySegment startPos = this.getCurrentPosition();

			
			while(currentPosition.getNext()!=null) {	
				if(currentPosition.hasSpace()) {
					currentPosition.enter(this); //enter the next station
					currentPosition.setCurrentTrain(this);
					int timeToSpend = currentPosition.timeSpentByTrain(this);
					try {
						Thread.sleep(timeToSpend*100);
					}catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						currentPosition.leave(this);
					}					
				}while(currentPosition.isFull()) {
					try {
						Thread.sleep(1000); //sleep for a second if the segment is full
											//I realise the locks and conditions should cover this but it seemed to help!
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}

				if(currentPosition.getNext() == null) { 
					currentPosition.enter(this); //enter the final segment (end of the line) and never leave
				}				
				currentPosition = currentPosition.getNext(); //set the next position for the train to enter
			}
			
			
//			if(currentPosition.getNext() == null) {
//				break;
//			}
		
//		startPos.enter(this);
//		while(currentPosition.getNext()!=null) {
////			int timeToSpend = currentPosition.timeSpentByTrain(this);
////			try {
////				Thread.sleep(timeToSpend);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//			currentPosition.leave(this);

		//}
//		System.out.println("Current position: " + currentPosition);
//		startPos.leave(this);
//		System.out.println("Current position: " + currentPosition);
	}
//	public void run() {
//		// need to use methods to tell trains when to enter and leave stations/tracks (take/release locks of shared objects) 
//		// need to do enter, sleep(timeSpent), leave 
//		
//		RailwayLine r = new RailwayLine();
//		RailwaySegment[] railway = r.getRailwayLine(); //don't think we need to use an array
////		System.out.println(this.toString());
//
//		if(!railway[0].isFull()) {
//			railway[0].enter(this);	//enter the first station
//			try {
//				int timeToSpend = railway[0].timeSpentByTrain(this);
//				Thread.sleep(timeToSpend);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		if(railway[0].getNext()!=null) {	
//			railway[0].leave(this);
//		}
//
//		for(int i=1;i<railway.length;i++) {
//			//System.out.println(railway[i].getName() + " " + railway[i].isFull());
//			if(!railway[i].isFull()) { //if there is space on the segment
//				//System.out.println(railway[i].getCurrentTrains());
//				railway[i].enter(this);
//				try {
//					int timeToSpend = railway[i].timeSpentByTrain(this);
//					Thread.sleep(timeToSpend*100);
//					System.out.println("sleeping for " + timeToSpend);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
////			if(railway[i].getNext()!=null) { //if the train is not at the end of the line
////				railway[i].leave(this);
////			}			
//		}
//	}
}
