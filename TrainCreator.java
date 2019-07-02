import java.util.Random;

public class TrainCreator implements Runnable {
	private int trainNumber;
	
	@Override
	public void run() {
		while(true) {
			//infinitely generate new trains at random intervals between 1000 & 10000 ms
			//could be quicker but I got deadlocks and trains getting stuck when I generated them very quickly 
			Random r = new Random();		
			int i1= 1000;
			int i2 = 10000;
			int random = r.nextInt(i2-i1) + i1; 
			Train t = new Train(trainNumber, RailwayLine.glasgow); //assign a number and starting position
			trainNumber++;
			Thread train = new Thread(t);
			train.start();
			try {
				Thread.sleep(random); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
