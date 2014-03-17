import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;

public class Display {
	    private JFrame frame;
	    
	    /**
	     * Create an ImageViewer show it on screen.
	     */
	    public Display()
	    {
	        makeFrame();
	    }
	    
	    // ---- swing stuff to build the frame and all its components ----
	    
	    /**
	     * Create the Swing frame and its content.
	     */
	    public void makeFrame()
	    {
	        frame = new JFrame("ImageViewer");        
	        Container contentPane = frame.getContentPane();
	        
	        JButton button = new JButton("I am a label. I can display some text.");
	        contentPane.add(button);

	        frame.pack();
	        frame.setVisible(true);
	    }
}
