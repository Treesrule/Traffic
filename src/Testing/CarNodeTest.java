package Testing;

import java.text.DecimalFormat;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import Simulations.CarNode;




@RunWith(JUnit4.class)
public class CarNodeTest {
	static final double STEP_SIZE = 0.1;
	
	@Test
    public void test1() {
		CarNode car1 = new CarNode(0,1,1);
		CarNode car2 = new CarNode(100,0,0);
		double time_step = 0.1;
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		car1.setNext(car2);
		for(int i = 0; i < 1300; i++){
			car1.move(time_step);
			car1.updateAcceleration(time_step);
			assert(car1.getPosition() < car2.getPosition());
			System.out.println(numberFormat.format(car1.getPosition()));
		}
	
		

    }
	@Test
	public void test2(){
		
	}
}
