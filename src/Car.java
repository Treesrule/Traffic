/* Benjamin Kadish
 * Car Simulation project - Car Class
 * Febuary 10, 2014
 * This Class is the representation of a car.
 * How do you make the car move by itself? 
 * 	
 */
import java.lang.Math;

public class Car {
	
	//Fields

	
	
	int id;
	int position;
	double velocity;
	double acceleration;
	double max_acceleration;
	double pref_speed; // Speed in "MPH " 
	final static double speed_limit = 40;           
	final double MILES_TO_SPOTS     = 1/0.003;
	final double SPOTS_TO_MILES     = 0.003;
	final double HOURS_TO_SECONDS   = 3600;
	final double SECONDS_TO_HOURS   = 1.0/3600;
	final double REACTION_TIME      = 3; //TODO Change Reaction time to a field
	final double MAX_SIGHT          = 10;
	// MPH -> M/S conversion Chart
	// 0   ->  0
	// 10  ->  4.47
	// 20  ->  8.94
	// 40  -> 17.88
	// 60  -> 26.82
	// Miles per hour to dots per step 
	
	// Have position and speed -> Want Next position and speed
	// Problem how to express a(t) and v(t)
	// For one step they can be treated as constants
	// ¶x = 1/2 a_i ¶t^2 + v_i ¶t
	// So the question remains what is the "best" course of action
	// The first course of action is a system where you naively try to reach the objective
	// I want to be going speed V_F (zero) at position X_F 
	// (some buffered space based on the current position)

	
	// In the real model multiple senses will need to occur in order to determine the relative speed
	// In the real simulation you will need to get the position 
	public Car(int position, int car_id){
		initalizeCar(speed_limit);
		this.position = position;
		id       = car_id;
	}
	
	public Car(int miles_per_hour,int position, int car_id){
		initalizeCar(miles_per_hour);
		this.position = position;
		id       = car_id;
	}
	
	private void initalizeCar(double miles_per_hour){
		max_acceleration = 10;
		pref_speed = miles_per_hour;
		velocity = 0;
		acceleration = Math.min((pref_speed - velocity)*SECONDS_TO_HOURS*5,max_acceleration);
		
	}
	
	
	//Moral We need a function which can tell the velocity of the car in front of them
	//It needs to be == 0 at a distance of "3 seconds"
	//That is at Speed (m/s) * 3 seconds 
	
	// 1 move spots is 0.003 miles  
	// Meaning v_f^2 = a_i * t + v_i^2
	// For small enough delta t you do not have to update acceleration
	// We will need to change this is future versions
	// Or we could swap at %1 effect (or some such number)
	public int move(double time_step){
	  // Update Acceleration HARD!!!!  
	  calculateNextAcceleration();
	  // Update Velocity
	  velocity += acceleration  * time_step;
	  // Update Position
	  double move_distance = velocity * time_step;
	  
	  position+= move_distance; // Why is this commented out?
	  // Morover why is this stored in two places
	  // Solution change the mehod to
	  int move_spots = (int) Math.floor(move_distance*MILES_TO_SPOTS);// Calculate to units of Car length 
	  
	  return move_spots;

	}
	
	private double senseNextRelativeVelocity(){
		//Get the velocity of the car in front of you
		double nextStats[] = Main.getNextStats(position);
		return nextStats[Main.VELOCITY_POS];
	}
	private int senseNextRelativePosition() {
		double nextStats[] = Main.getNextStats(position);
		return (int) nextStats[Main.LOC_POS];
	}
	
	//Depreciated function Break
	// Takes a real value between [0,1)	
	// Takes a fake value between [Ä,¨]
	/*private boolean brake(){
		return true;
	}*/
	
	
	// Edge cases 1) What if another car moves into the lane
	//            2) 
	
	// Find the next acceleration
	// Currently the next Acceleration is found by figureing out a safe slowing down distance to
	// The vehicle in front of you and calculating an acceleration to meet this requirement
	public void calculateNextAcceleration(){
		// How do we shortcut this step if its not needed?
		// First Check to see if your speed is the desired speed.
		double r_velocity = senseNextRelativeVelocity();
		int r_position    = senseNextRelativePosition();		
		double n_velocity = velocity - r_velocity;
		double distance   = n_velocity * REACTION_TIME; 
		if(r_velocity <= 0 || n_velocity * MAX_SIGHT * MILES_TO_SPOTS > r_position){
			if(velocity >= pref_speed) 
				acceleration = 0;
			else{
				acceleration = max_acceleration;
			}
			return; // Move to normal acceleration towards velocity
		}
		double d_position = r_position - distance > 0 ? r_position - distance : r_position;  // The amount of space you have to break in
		// Calculate weather you are "too close"		
		acceleration = -(Math.pow(r_velocity,2.0))/(2*d_position);
		// Change r_velocity to 0 by distance d
		
	}



	public double getSpeed(){
		return velocity;	
	}
	
	public void setPosition(int next_pos){
		position = next_pos;
	}
	
	public int getPosition(){
		return position;
	}
	public String toString(){
		return "Id: " + id + " Position: " + position + " Velocity: " + velocity + " Acceleration: " + acceleration;
	}
	
}
