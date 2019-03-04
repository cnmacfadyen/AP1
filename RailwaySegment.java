import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class RailwaySegment {
	private String name;
	private int capacity;
	private int length;
	private String status;
	private RailwaySegment previousSegment, nextSegment;
	private Train current;
	private ArrayList<Train> currentTrains = new ArrayList<Train>();;
	private final ReentrantLock segmentLock = new ReentrantLock();
	private final Condition condition = segmentLock.newCondition();
	
	public RailwaySegment(int length, int capacity) { 
		this.length=length;
		this.capacity=capacity;		
	}

	public RailwaySegment() { //for EndOfTheLine

	}
	
	/////////////////////////// GETTERS AND SETTERS //////////////////////////

	public int getLength() {
		return this.length;
	}
	
	public void setLength(int l) {
		this.length=l;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public void setCapacity(int c) {
		this.capacity=c;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setStatus(String s) {
		this.status=s;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public RailwaySegment getPrevious() {
		return this.previousSegment;
	}
	
	public void setPrevious(RailwaySegment prev) {
		this.previousSegment=prev;
	}
	
	public RailwaySegment getNext() {
		return this.nextSegment;
	}
	
	public void setNext(RailwaySegment next) {
		this.nextSegment=next;
	}
	
	public ArrayList<Train> getCurrentTrains() {
		return this.currentTrains;
	}
	
	public void setCurrentTrains(ArrayList<Train> currentTrains) {
		this.currentTrains=currentTrains;
	}
	
	public void setCurrentTrain(Train train) {
		this.current=train;
	}
	
	public Train getCurrentTrain() {
		return this.current;
	}

	/////////////////////////// OTHER METHODS //////////////////////////
	
	public int timeSpentByTrain(Train t) {
		int timeSpent = this.length/t.getSpeed();
		return timeSpent;
	}
	
	public void addToCurrentTrains(Train t, RailwaySegment r) {
		t.setCurrentPosition(r);
		this.currentTrains.add(t);
	}
			
	public boolean isFull() {
		if(currentTrains.size()==this.capacity) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean hasSpace() {
		if(currentTrains.size()<this.capacity) {
			return true;
		}else {
			return false;
		}
	}
	
	public void enter(Train t) {
		segmentLock.lock();
		this.current=t;
		this.currentTrains.add(current);
	//	System.out.println(t.getNumber() + " has locked enter\n");
	//	System.out.println(this.getName() + " : " + currentTrains);
		RailwaySegment next = this.getNext();

		//System.out.println(this.getName() + " has " + numTrains + " trains");	
		try {
			if(this.isFull()) {
				//System.out.println(this.getName() + " is full");
			}
			while(next!=null && next.isFull()) { // if the segment is at capacity and we are not at the last station
				//System.out.println(t.getNumber() + " is waiting..."); 
				condition.await();				
			}			
			t.setCurrentPosition(this);
		
		//	System.out.println(t.getNumber() + " has entered..." + this.getName() + " currently holding : " + currentTrains.size());
			//System.out.println(currentTrains);

		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			segmentLock.unlock();
		//	System.out.println(t.getNumber() + " has unlocked enter\n");
		}	
	}
	
	public void leave(Train t) {
		segmentLock.lock();
		this.currentTrains.remove(current);
		this.current = null;
		//System.out.println(t.getNumber() + " has locked leave\n");
		//System.out.println(this.getName() + " : " + currentTrains);
		try {
			RailwaySegment next = this.getNext();
		//System.out.println(currentTrains);
		//System.out.println(t.getNumber() + " has left..." + this.getName() + " currently holding: " + currentTrains.size()); 
		//	if(!this.isFull()) { //if there is space
			if(next.hasSpace()) {
				condition.signal();
				//System.out.println(t.getNumber() + " is signalling...");
			}
				
			//System.out.println(t.getNumber() + " is signalling...");
		//}
//		if(!next.isFull()) {
//			next.enter(t);
//		}
//		if(next!=null) {
//			next.enter(t);
//		}else {
//			//do nothing
//		}
		} finally {
			segmentLock.unlock();
			//System.out.println(t.getNumber() + " has unlocked leave\n");
		}

	}
		
	public String toString() {
		if(this.getCurrentTrains().isEmpty()) { //if there are no trains on the segment
			return "|--" + this.getName() + ",--|";
		}else {
			String stat = "|--" + this.getName() + "--";
			for (int i=0;i<this.getCurrentTrains().size();i++) { //loop over the array of current trains
				stat += this.getCurrentTrains().get(i).getNumber() + ",";
			}
		return stat += "--|";
		}
	}
}
