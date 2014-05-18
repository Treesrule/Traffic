/* Benjamin Kadish
 * Car Simulation project - Main Class
 * Febuary 10, 2014
 * This classs is meant to run the whole system. 
 * 
 */
import java.util.ArrayList;
import java.util.Random;

public class OldMain {
	
	static final int NUM_CARS     = 2;
    static final int MAX_STEPS    = 100;
    static final double STEP_SIZE = 0.1;
	static final int ARRAY_SIZE   = 1000;
	static final int LOC_POS      = 0;
	static final int VELOCITY_POS = 1;
	static ArrayList<Car> cars;
	static int[] highway;
	public static void main(String[] args){
		
		cars = new ArrayList<Car>(); // Create array of Cars 
		int step = 0; // Initalize the steps
		highway = new int[ARRAY_SIZE];
		//Initialize the car to a random spot on the array 
		// 
		Random rng = new Random(0);
		for(int i = 1; i <= NUM_CARS; i++){	
			int car_pos = rng.nextInt(ARRAY_SIZE);
			Car test;// = new Car(car_pos,i);
			if(i == 1)	test = new Car(car_pos,i);
			else test = new Car(car_pos,i);
			//System.out.println("Id: " + i + " Pos: " + car_pos);
			highway[car_pos] = test.id; // First car
			cars.add(test);
			
		}

		// Now we need to add collision avoidance
		
		// Cars have a width of 1 or 16 feet or 15/5280 miles or 0.003 miles 
		// A "step is 0.1 seconds"
		// The Question now is how do you store the cars 

		while(step < MAX_STEPS){
			// Move Each Car 
			//Update the car array
			//Need to switch this to a method 
			for(Car car: cars){
				int cur_pos = car.getPosition();	
				car.setPosition((car.move(STEP_SIZE) + cur_pos) % ARRAY_SIZE);
				highway[cur_pos] = 0;
				highway[car.getPosition()] = car.id;				
			}
			for(int i = 0; i < ARRAY_SIZE; i++ ){
				if(highway[i] != 0) System.out.print(highway[i] + "\t" + i + "\t");
			}
			System.out.println();
			step++;
			
		}
		//Array car id#  space 
		//Have the current location  

		
		// Start the simulation 
		
		// Need an array of all of the cars
		// Display display = new Display();
	}
	// Perhaps we should have a class for returning X,V,A? -> They sometimes call this an array of
	// doubles 
	// Now
	// Need to find the vehicle in front of you
	
	// Finds the relative Kinimatics of the car in front of you
	// Currently does not implement Acceleration 
	public static double[] getNextStats(int position){
		int initial = position;
		do{
			 position=(position + 1)%highway.length;	
		}while(highway[position] == 0);
		double X = deltaX(initial,position);
	    double V = deltaV(initial,position);
		double stats[] = {X,V};
		return stats;	
	}
	
	// Finds the relative velocity car in front of you 
	public static double getNextVelocity(int position){
		int init_v = position;
		do{
			 position=(position + 1)%highway.length;	
		}while(highway[position] == 0);
		return deltaV(init_v,position);
	}
	
	// Finds the relative position car in front of you 
	public static int getNextPosition(int position){
		int init_x = position;
		do{
			 position=(position + 1)%highway.length;	
		}while(highway[position] == 0);
		return (int) deltaX(init_x,position);
	}

	// Gives you position difference between the car at @param initial 
	// and @param next
	public static double deltaX(int initial,int next){
		return (initial - next)%highway.length;
	}
	
	// Gives you speed difference between the car at @param initial 
	// and @param next
	public static double deltaV(int initial, int next){
		//System.out.println("Initial: " + initial + " Next: " + next);
		//printHighway();
		//for(Car car: cars) System.out.println(car);
		return cars.get(highway[initial]-1).getSpeed() - cars.get(highway[next]-1).getSpeed();
	}
	
	// Prints out the parts of the Highway with cars on them
	public static void printHighway(){
		for(int i = 0; i< highway.length; i++){
			if(highway[i] != 0) System.out.print(i + " " + highway[i] + " ");
		}
		System.out.println();
	}
}
