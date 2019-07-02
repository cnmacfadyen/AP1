import java.util.Random;

public class Train implements Runnable {
	private int number;
	private int speed;
	private RailwaySegment currentPosition;
	
	public Train(int number, RailwaySegment currentPosition) { 
		this.number=number;
		this.setRandomSpeed(); //randomly assign a speed to each train
		this.currentPosition=currentPosition;
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
	
	public int getSpeed() {
		return this.speed; 
	}
	
	public void setSpeed(int s) {
		this.speed = s;
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
	
	@Override
	public void run() {
		
		while(currentPosition.getNext()!=null) { //if the train is not at the end of the line
			if(currentPosition.hasSpace()) {
				currentPosition.enter(this); //enter the next station
				currentPosition.setCurrentTrain(this);
				int timeToSpend = currentPosition.timeSpentByTrain(this);
				try {
					Thread.sleep(timeToSpend*100); //sleep for the allotted waiting time
				}catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					currentPosition.leave(this);
				}					
			}while(currentPosition.isFull()) {
				try {
					Thread.sleep(1000); //sleep for a second if the segment is full
										//I think the locks and conditions should cover this but it seemed to help!
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}

			if(currentPosition.getNext() == null) { 
				currentPosition.enter(this); //enter the final segment (end of the line) and never leave
			}				
			currentPosition = currentPosition.getNext(); //set the next position for the train to enter
		}		
	}
	
	public String toString() {
		//this was used for testing the setRandomSpeed() method
		return "My name is " + this.getNumber() + " and I can travel at " + speed + "m/s";
	}

}
