import java.util.ArrayList;
import java.util.Arrays;

public class RunMe {
	public static void main(String[] args) {

		//Track test = new Track();
		//ArrayList<Train> currentTrains = test.getCurrentTrains();

		TrainCreator tc = new TrainCreator(); 
		Thread trainGenerator = new Thread(tc);
		RailwayLinePrinter rlp= new RailwayLinePrinter();
		Thread printer = new Thread(rlp);
		//trainGenerator.start();
		printer.start();
		
		ArrayList<Train>test = new ArrayList<Train>();
		for(int i=0;i<2;i++) {			
			Train t = new Train(i);
			test.add(t);
			System.out.println(t);
			Thread trains = new Thread(t);
			trains.start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


	}
}