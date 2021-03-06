package Simulations;

/* Benjamin Kadish
 * Car Simulation project - Main Class
 * May 13, 2014	
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

// This class runs the simulation



public class Main {
	
	static final int    NUM_CARS           = 10;
    static final int    MAX_STEPS     	   = 1000;
    static final double STEP_SIZE     	   = 0.1;
    static final double LOW_PREF_VELOCITY  = 5;
    static final double HIGH_PREF_VELOCITY = 10;
    static final double PREF_ACCELERATION  = 1; 
    static final int    LOW_RANGE_POS      = 100;
    static final int    HIGH_RANGE_POS     = 200;
    private static final Random random = new Random();
    
	public static void main(String[] args){
		
		ArrayList<CarNode> cars   = initalizeCars();
		ArrayList<String> datalog = new ArrayList<String>();
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		//printCarList(cars);
		String s;
		for(int iteration = 0; iteration < MAX_STEPS; iteration++){
			s = "";
			for(CarNode car: cars){
				car.move(STEP_SIZE);
				s = s + numberFormat.format(car.getPosition()) + ",";
			}
			s += "\n";
			datalog.add(s);
			for(CarNode car: cars){
				car.updateAcceleration(STEP_SIZE);
			}
			
		}
		writeCarsToFile(datalog);
		//Do some simulation
		//System.out.println("Random: " + Math.round(random.nextDouble()*5));
		//printCarList(cars);
	}

	
	//Print out your list of Cars
	private static void printCarList(ArrayList<CarNode> cars){
		int i = 1;
		String position,velocity, acceleration;
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		for(CarNode car: cars){
			position     = " Position: " + numberFormat.format(car.getPosition());
			velocity     = " Velocity: " + numberFormat.format(car.getPreferedVelocity());
			acceleration = " Acceleration : " + numberFormat.format(car.getAcceleration());
			//System.out.println("Car " + i + " " + position + velocity + acceleration);
			i++;
		}
	}
	
	public static void writeCarsToFile(ArrayList<String> strings){
		FileWriter file;
		try {
			file = new FileWriter("TestOutput.csv");
			BufferedWriter writer = new BufferedWriter(file);
			outputCars(writer,strings);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void outputCars(BufferedWriter writer,
			ArrayList<String> strings) throws IOException {
		for(String string: strings){
			writer.write(string);
		}
		
	}


	// Initalize a car List
	private static ArrayList<CarNode> initalizeCars() {
		ArrayList<CarNode> cars = new ArrayList<CarNode>();
		double  position = 0;
		CarNode prev =  new CarNode(position, generateVelocity(), PREF_ACCELERATION);
		CarNode current;
		cars.add(prev);
		for(int car_num = 1 ; car_num < NUM_CARS; car_num++){
			position = generatePosition(position);
			current = new CarNode(position, generateVelocity(), PREF_ACCELERATION);
			prev.setNext(current);
			current.setPrev(prev);
			cars.add(current);
			prev = current;
		}
		printCarList(cars);
		return cars;
	}


	private static double generatePosition(double position) {
		int range = HIGH_RANGE_POS - LOW_RANGE_POS;
		return random.nextInt(range) + LOW_RANGE_POS + position;
	}


	private static double generateVelocity() {
		double range = HIGH_PREF_VELOCITY -LOW_PREF_VELOCITY;
		return random.nextDouble()*range + LOW_PREF_VELOCITY;
	}
	
}
