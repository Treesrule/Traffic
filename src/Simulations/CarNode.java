package Simulations;


/* Benjamin Kadish
 * Car Simulation project - Car Node Class
 * April 17, 2014	
 */

// A Car Node is like a car except in knows the next and previous car in its lane
// I think this should solve some of the current data management problems 
// Should we decouple the calculation of the movement and the movement?
// 

// Need to define units of the simulation 


// TODO Generate a new control system which does not need to force the zero velocity
// TODO Create variability in Thresholds, Reactions and vision this should be easy for the class 
// but slightly harder for the generating class
// TODO Add the ability to have multiple lanes
// TODO Add visuals 

public class CarNode{
	
	private CarNode prev, next;
	private double position, velocity, acceleration;
	private double pref_velocity, pref_acceleration;
	
	private static final double REACTION_TIME      = 3.00; 
	private static final double MAX_SIGHT          = 10.0;
	private static final double POSITION_THRESHOLD = 0.03;
	private static final double VELOCITY_THRESHOLD = 0.05;
	
	public CarNode(double position, double pref_velocity, double pref_acceleration) {
		this.position      = position;
		this.pref_velocity = pref_velocity;
		this.pref_acceleration = pref_acceleration;
		this.velocity = 0;
		this.acceleration = 0;
	}
	
	// Moves the car one time step 
	// Note: If we want to change the simulation to be parallel
	// we can decouple calculateNextAcceleration from the rest of this method
	// This would probably work better in C++ where you could explicitly manage the threads ect.
	
	public void move(double time_step){

		position += velocity      * time_step;
		velocity += acceleration  * time_step;
		
		/*double dx         = find_dx();
		double dv         = find_dv();
		acceleration = calculateNextAcceleration(dx,dv,times_step);*/
		
	}
	
	
	// The goal would be to have two behaviours the default behaviour and the 
	// behaviour when a car is in front of them 
	// @Param dx distance between this car and the next one
	// @Param dv velocity difference between the current car and the next car
	public double calculateNextAcceleration(double dx, double dv,double time_step){
		if(reactionNeeded(dx,dv)) return calculateReaction(dx,dv,time_step);
		
		return freeAcceleration();	
		
	}

	//After the car has moved update the acceleration
	public void updateAcceleration(double time_step){
		double dx         = find_dx();
		double dv         = find_dv();
		if(reactionNeeded(dx,dv)) acceleration = calculateReaction(dx,dv,time_step);
		else acceleration = freeAcceleration();	
	}
	
	// Find the acceleration if there is no car in front of you
	private double freeAcceleration() {
		
		if(pref_velocity - velocity > VELOCITY_THRESHOLD) 
			return pref_acceleration;
		
		if(velocity - pref_velocity > VELOCITY_THRESHOLD)  
			return -pref_acceleration;
		
		return 0;
	}

	// Calculate how fast you should break if you are close to 	the car in front of you
	// TODO Handle the case where the car has an error
	private double calculateReaction(double dx, double dv,double time_step) {
		double goal_position = dx - velocity*REACTION_TIME;
		if(goal_position < POSITION_THRESHOLD){
			// Clamp
			// the current problem with this solution is that its not a directive to the car 
			// such is life 
			velocity = next.getVelocity();
			return 0;
		}
		if( goal_position < 0) return 0;
		return constant_accleration(dv,goal_position);
	}

	// Calculates if the car needs to react to its surroundings 
	private boolean reactionNeeded(double dx, double dv) {		
		return (dx < MAX_SIGHT);
	}

	//(Mostly) Constant Acceleration
	// At some point we should clamp the acceleration 
	// Either if Acceleration is too high or if Position is close to good
	public double constant_accleration(double dv, double goal_position){
		return -(Math.pow(dv,2.0))/(2*goal_position);
	}
	
	// Find cars Accleration
	public double getAcceleration(){
		return acceleration;
	}
	
	//Find Cars Speed 
	public double getVelocity(){
		return velocity;	
	}
	
	//Find Cars position
	public double getPosition(){
		return position;
	}

	public double getPreferedVelocity(){
		return pref_velocity;
	}
	
	public double getPreferedAcceleration(){
		return pref_acceleration;
	}
	
	// Set the Next car field
	public void setNext(CarNode car){
		next = car;
	}
	
	//The following methods are for debugging
	public void setPosition(double position){
		this.position = position;
	}
	
	public void setVeloctiy(double velocity){
		this.velocity = velocity;
	}
	
	public void setAcceration(double acceleration){
		this.acceleration = acceleration;
	}
	//Set the previous car field
	public void setPrev(CarNode car){
		prev = car;
	}
	
	//Get the previous car
	public CarNode getNext(){
		return next;
	}
	
	//Set the previous car field
	public CarNode getPrev(){
		return prev;
	}
	
	//Find the next cars position
	private double find_dx() {
		if(next == null) return Integer.MAX_VALUE;
		return next.getPosition() - position;
	}
	//Find the Next Cars Velocity
	private double find_dv() {
		if(next == null) return 0;
		return next.getVelocity() - velocity;
	}
}
