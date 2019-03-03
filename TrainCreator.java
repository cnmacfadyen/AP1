
public class TrainCreator implements Runnable {
	
	private int trainNumber;
	
	public int getNumTrains() {
	return trainNumber;
	}
	
	@Override
	public void run() {
		while(true) {
			Train t = new Train(trainNumber); 
			trainNumber++;
			Thread train = new Thread(t);
			train.start();
			try {
				Thread.sleep(1000); //add a new train every second (change to random)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
