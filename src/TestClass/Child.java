package TestClass;

public class Child extends Parent {

	public void B(){
		System.out.println("Child");
	}
	
	public static void main(String[] args){
		
		Child one    = new Child();
	    Parent two   = new Child();
	    Parent three = new Parent();
	    one.A();
	    two.A();
	    three.A();
	    
	}
}
