package main;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MouseMoveOnScreen  {

    Robot robot;
    JLabel label;
    GeneralPath gp;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    static long time = System.currentTimeMillis();
   static  long gapDuration =0;
   static String SystemIp = "";
    MouseMoveOnScreen() throws AWTException {
        robot = new Robot();
        label = new JLabel();
        gp = new GeneralPath();
        Point p = MouseInfo.getPointerInfo().getLocation();
        gp.moveTo(p.x, p.y);
        drawLatestMouseMovement();
   
        ActionListener al = new ActionListener() {
        Point lastPoint;
        
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();                      
                if (!p.equals(lastPoint)) {
                	time = System.currentTimeMillis();
                    gp.lineTo(p.x, p.y);
                    drawLatestMouseMovement();
                }
                lastPoint = p;
               // System.out.println("checking...."+gapDuration);
                gapDuration = (System.currentTimeMillis()-time)/(1000*60);
                if(gapDuration>=2) {
                	System.out.println("Max Time Reached...."+gapDuration);
                	Runtime runtime = Runtime.getRuntime();
                    try
                    {
                       System.out.println("Shutting down the PC after 5 seconds.");
                      // runtime.exec("shutdown -s ");
                       Process processing = runtime.exec("systemctl hybrid-sleep" );    
                       processing.waitFor();
	                   // Shutting Down the System
	                   //System.exit(0);
                    }
                    catch(IOException ie)
                    {
                       System.out.println("Exception: " +ie);
                    } catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        };
        Timer timer = new Timer(400, al);
        timer.start();
       
    }
    
   

    public void drawLatestMouseMovement() {
        BufferedImage biOrig = robot.createScreenCapture(
                new Rectangle(0, 0, d.width, d.height));
        BufferedImage small = new BufferedImage(
                biOrig.getWidth() / 4,
                biOrig.getHeight() / 4,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = small.createGraphics();
        g.scale(.25, .25);
        g.drawImage(biOrig, 0, 0, label);
        g.setStroke(new BasicStroke(8));
        g.setColor(Color.RED);
        g.draw(gp);
        g.dispose();
        label.setIcon(new ImageIcon(small));
    }

    public JComponent getUI() {
        return label;
    }

    public static void main(String[] args) throws Exception , SocketException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                JPanel ui = new JPanel(new BorderLayout(2, 2));
                ui.setBorder(new EmptyBorder(4, 4, 4, 4));

                try {
                    MouseMoveOnScreen mmos = new MouseMoveOnScreen();
                    ui.add(mmos.getUI());
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }

                JFrame f = new JFrame("Track Mouse On Screen");
                f.addKeyListener(new KeyAdapter() {
                	public void keyPressed(KeyEvent e) {
    	    		    int keyCode = e.getKeyCode();
    	    		    System.out.println(keyCode+" is pressed!");
    	                time = System.currentTimeMillis();
    	    		   }
				});
                // quick hack to end the frame and timer
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setContentPane(ui);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
                // fetching network interface              
                
                Enumeration<NetworkInterface> nets;
				try {
					nets = NetworkInterface.getNetworkInterfaces();					
					for (NetworkInterface netint :Collections.list(nets)) {						 
	                    displayInterfaceInformation(netint);
	                    break;
					 }
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}                       
            }
        };
        SwingUtilities.invokeLater(r);
    }
    
 // Display Internet Information method
    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException{     
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        // Output System IP
        for (InetAddress inetAddress :Collections.list(inetAddresses)) {
        	SystemIp = String.valueOf(inetAddress);
        	//break;
        }
        System.out.println(" System Ip is  :  "+SystemIp);
        System.out.printf("\n");
    }


}