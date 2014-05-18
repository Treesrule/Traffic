import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	        
	        
	        
	        JLabel label = new JLabel();
	        label.setOpaque(true);
	        label.setBackground(new Color(248, 213, 131));
	        label.setPreferredSize(new Dimension(200, 180));
	        Graphics label_graphics = label.getGraphics();
	        ImageIcon img = mkCarImage();
	        label_graphics.drawImage(img.getImage(), 100, 100, null);
	        contentPane.add(label,BorderLayout.SOUTH);
	        contentPane.add(button);
	        
	        frame.pack();
	        frame.setVisible(true);
	    }

	    private ImageIcon mkCarImage() {
		    try {
		    	BufferedImage image = ImageIO.read(new File("Car"));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "jpg", baos);
				byte[] img = baos.toByteArray();
				return new ImageIcon(img); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		  

		//Read more: http://mrbool.com/how-to-convert-image-to-byte-array-and-byte-array-to-image-in-java/25136#ixzz2xjVK2pGk

	    	// TODO Auto-generated method stub
			return null;
		}

		public static void main(String[] args){
	    	Display x = new Display();
	    }
}
