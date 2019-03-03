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
	private int numTrains;
	private ArrayList<Train> currentTrains = new ArrayList<Train>();;
	private final ReentrantLock segmentLock = new ReentrantLock();
	private final Condition condition = segmentLock.newCondition();
	
	public RailwaySegment(int length, int capacity) { 
		this.length=length;
		this.capacity=capacity;
		this.setCurrentTrains(currentTrains);		
	}
	
	public RailwaySegment(int length) { // didn't put capacity in the constructor because it's always the same for tracks
		this.length=length;
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
	
	public void setNumTrains(int n) {
		this.numTrains=n;
	}
	
	public int getNumTrains() {
		return this.numTrains;
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
		if(currentTrains.size()==capacity) {
			return true;
		}else {
			return false;
		}
	}
	
	public void enter(Train t) {
		segmentLock.lock();
		this.current=t;
		this.currentTrains.add(t);
		System.out.println(t.getNumber() + " has locked enter\n");
		System.out.println(this.getName() + " : " + currentTrains);
		RailwaySegment next = this.getNext();

		//System.out.println(this.getName() + " has " + numTrains + " trains");	
		try {
			while(next!=null && next.isFull()) { // if the segment is at capacity and we are not at the last station
				System.out.println(t.getNumber() + " is waiting..."); 
				condition.await();				
			}			
			t.setCurrentPosition(this);
		
			System.out.println(t.getNumber() + " has entered..." + this.getName() + " currently holding : " + currentTrains.size());
			//System.out.println(currentTrains);
			this.status = "|----" + this.getName() + "," + this.currentTrains.toString() +"----|";
			this.setStatus(status);	
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			segmentLock.unlock();
			System.out.println(t.getNumber() + " has unlocked enter\n");
		}	
	}
	
	public void leave(Train t) {
		segmentLock.lock();
		this.currentTrains.remove(t);
		System.out.println(t.getNumber() + " has locked leave\n");
		System.out.println(this.getName() + " : " + currentTrains);
		status = "|----" + this.getName() + "," + this.currentTrains.size() +"----|";
		this.setStatus(status);
		try {
		if(numTrains>0) { //number of trains cannot go below 0
			numTrains--;
		}
		RailwaySegment next = this.getNext();

		//System.out.println(currentTrains);
		//this.current=null;
		System.out.println(t.getNumber() + " has left..." + this.getName() + " currently holding: " + currentTrains.size()); 
		if(!this.isFull()) { //if there is space
			condition.signalAll();
			System.out.println(t.getNumber() + " is signalling...");
		}
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
			System.out.println(t.getNumber() + " has unlocked leave\n");
		}

	}
		
	public String toString() {
//		if(!currentTrains.isEmpty()) {
//			return "|----" + this.getName() + "," + currentTrains.get(0).getNumber() +"----|";
//		}else {
//			return "|----" + this.getName() + "," + this.getNumTrains() +"----|";
//		this.setNumTrains(1);
////		if(this.current!=null) {
//			this.setStatus("|----" + this.getName()  + "--" + this.getNumTrains() + ",--|");	
//		}else {
//			this.setStatus("|--" + this.getName() + "--|");
//		}
		//status = "|--" + this.getName() + "--" + this.currentTrains.toString() + ",--|";
//		if(this.getStatus() != null) {
		//	return this.getStatus();
//		}else {

		return ("|--" + this.getName() + "--" + this.currentTrains.toString() + ",--|");
	//	return status;
		
		

		//		}else {
//			return "|--" + this.getName() + "--|";
//		}
	}
}