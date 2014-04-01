

// A Car Node is like a car except in knows the next and prev car in its lane
// I think this should solve some of the current data management problems 

public class CarNode extends Car{

	CarNode next;
	CarNode prev;
	
	
	public CarNode(int position, int car_id) {
		super(position, car_id);
		// TODO Auto-generated constructor stub
	}
	
	@Override 
	public int move(double time_step){
		velocity += acceleration  * time_step;
		  // Update Position
		double move_distance = velocity * time_step;
		
		return 0;
	}
	private double senseNextRelativeVelocity(){
		return next.getSpeed() - velocity;
	}
	
	private double senseNextRelativePosition() {
		return next.getPosition() - position;
	}
}
