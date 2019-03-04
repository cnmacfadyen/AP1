import java.util.Random;

public class TrainCreator implements Runnable {
	
	private int trainNumber;
	
	public int getNumTrains() {
	return trainNumber;
	}
	
	@Override
	public void run() {
		while(true) {
			Random r = new Random();
			int i1= 1000;
			int i2 = 10000;
			int random = r.nextInt(i2-i1) + i1;
			Train t = new Train(trainNumber, RunMe.glasgow); 
			trainNumber++;
			Thread train = new Thread(t);
			train.start();
			try {
				Thread.sleep(random); //generate a new train at random intervals between 1000 & 10000 ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
