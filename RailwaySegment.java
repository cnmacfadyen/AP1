import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class RailwaySegment {
	private String name;
	private int capacity;
	private int length;
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

	public int timeSpentByTrain(Train t) {
		int timeSpent = this.length/t.getSpeed();
		return timeSpent;
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
		RailwaySegment next = this.getNext();
		
		try {
			if(this.isFull()) {
			}
			while(next!=null && next.isFull()) { // if the segment is at capacity and we are not at the last station
				condition.await();				
			}			
			t.setCurrentPosition(this);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			segmentLock.unlock();
		}	
	}
	
	public void leave(Train t) {
		segmentLock.lock();
		this.currentTrains.remove(current);
		this.current = null;
		try {
			RailwaySegment next = this.getNext();
			if(next.hasSpace() || this.hasSpace()) {
				condition.signalAll();
			}
		} finally {
			segmentLock.unlock();
		}
	}
		
	public String toString() {
		if(this.getCurrentTrains().isEmpty()) { //if there are no trains on the segment
			return "|--" + this.getName() + "--|";
		}else {
			String stat = "|--" + this.getName() + "--";
			for (int i=0;i<this.getCurrentTrains().size();i++) { //loop over the array of current trains
				stat += this.getCurrentTrains().get(i).getNumber() + ",";
			}
		return stat += "--|";
		}
	}
}
