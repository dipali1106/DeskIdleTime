package threads;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import javax.imageio.ImageIO;

import org.hibernate.Session;

import entity.DateWiseSystemLog;
import utils.Constants;
import utils.HibernateUtility;

public class LastLoginUpdateThread extends Thread {
	static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	int max= 2000;
	int min= 0;
	//long timestamp = System.currentTimeMillis();
	public boolean isRunning =true;
	int flag =0;
	public void run() {
		new Timer().schedule(new ScreenshotTask(), 0, 10000);
		while(isRunning) {
			try {
				
				Thread.sleep(30000);
				if(Constants.DateWiseId>0) {
					//System.out.println("here");
					Session session = HibernateUtility.getSessionFactory().openSession();
					session.beginTransaction();	
					DateWiseSystemLog dateLog = session.find(DateWiseSystemLog.class, Constants.DateWiseId);
					dateLog.setLastLogin(timeFormat.format(new Date()));
					session.getTransaction().commit();
					System.out.println("DateWiseSystemLog Row Updated");
					
				}
				
				
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
}
