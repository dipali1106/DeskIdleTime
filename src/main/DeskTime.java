package main;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import threads.LastLoginUpdateThread;
import utils.Constants;
import utils.Helper;

public class DeskTime {
	static LastLoginUpdateThread lastLoginUpdateThread =null;
	Robot robot;
    JLabel label;
    GeneralPath gp;
    static long time = System.currentTimeMillis();
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    boolean flag =false;
   boolean ActiveFlag =false;
    
    DeskTime() throws AWTException {
        //robot = new Robot();
        label = new JLabel();
        gp = new GeneralPath();
        Point p = MouseInfo.getPointerInfo().getLocation();
        gp.moveTo(p.x, p.y);  
       
        ActionListener al = new ActionListener() {
        Point lastPoint;
        
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();                      
                if (!p.equals(lastPoint)) {
                	time = System.currentTimeMillis();
                    gp.lineTo(p.x, p.y);
                    if(flag) {
                    	Helper.updateLogEntry();
                    	flag = false;
                    }
                }
                lastPoint = p;
                //System.out.println("checking...."+Constants.gapDuration);
                Constants.gapDuration = (System.currentTimeMillis()-time)/1000;
                if( (Constants.gapDuration/60) >=2 && flag==false) {
                	System.out.println("System is inactive for "+Constants.gapDuration/60+" minutes");
                	Helper.addLoginEntry();
                	flag = true;
                }
               
            }
        };
        Timer timer = new Timer(400, al);
        timer.start();
       
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Runnable r = new Runnable() {
	            @Override
	            public void run() {
	                JPanel ui = new JPanel(new BorderLayout(2, 2));
	                ui.setBorder(new EmptyBorder(4, 4, 4, 4));
	                ui.setBounds(100, 100, 650, 580);
	                ui.setVisible(true);
	                try {
	                	DeskTime desktObj = new DeskTime();
//	                   / ui.add(desktObj.getUI());
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
	                f.setBounds(100, 100, 500, 480);
	                f.setContentPane(ui);
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
	        Helper.addLoginEntry();
	        lastLoginUpdateThread = new LastLoginUpdateThread();
	        lastLoginUpdateThread.start();
	    }
	
	 public JComponent getUI() {
	        return label;
	    }
	
	 static void displayInterfaceInformation(NetworkInterface netint) throws SocketException{     
	        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
	        // Output System IP
	        for (InetAddress inetAddress :Collections.list(inetAddresses)) {
	        	Constants.SystemIp = String.valueOf(inetAddress);
	        	//break;
	        }
	        System.out.println(" System Ip is  :  "+Constants.SystemIp);
	        System.out.printf("\n");
	    }


}
