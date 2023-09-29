package threads;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import entity.SystemScreenshots;
import utils.Constants;
import utils.HibernateUtility;


public class ScreenshotTask extends TimerTask {
	String basepath = "/home/dev/Downloads/javaapps/DeskTime";
	SimpleDateFormat d = new SimpleDateFormat("HH:mm;ss");
	String ScriptOrUrl =null;


	@Override
	public void run() {
		Random random = new Random();
		int value = random.ints(1, 3000).findFirst().getAsInt();
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture;
		try {
			try {				
				Random random2 = new Random();
				int Index = random.ints(1, 50).findFirst().getAsInt();
				capture = new Robot().createScreenCapture(screenRect);
				String path = getDumpPath();
				String file = path + File.separator + "screenshot_" + Index;
				String remoteFile = "screenshot_" + Index;
				ImageIO.write(capture, "bmp", new File(file));
				System.out.println("ss captured at :"+ d.format(new Date()));
			
				Session jschSession;
				try {
					JSch jsch = new JSch();
					//jsch.setKnownHosts("/home/dev/Downloads/javaapps/DeskTime/known_hosts");					
					jschSession = jsch.getSession("hp", "192.168.68.119");
					
					jschSession.setPassword("Sm@rtF0x");
					Properties config = new Properties();
					config.put("StrictHostKeyChecking", "no");
					jschSession.setConfig(config);					
					// 10 seconds session timeout
					jschSession.connect(3000);
					Channel sftp = jschSession.openChannel("sftp");
					// 5 seconds timeout
					sftp.connect(300);
					ChannelSftp channelSftp = (ChannelSftp) sftp;
					// transfer file from local to remote server
					channelSftp.put(file, remoteFile);
					// download file from remote server to local
					// channelSftp.get(remoteFile, localFile);
					channelSftp.exit();
				} catch (JSchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SftpException e) {
					e.printStackTrace();
				}																  
				System.out.println("Done");  
				String imgbase64 = base64Converter(file);				
				if(imgbase64!=null) {
					org.hibernate.Session session = HibernateUtility.getSessionFactory().getCurrentSession();
					session.beginTransaction();
					//System.out.println(imgbase64);
					SystemScreenshots ssDetails = new SystemScreenshots(Constants.DateWiseId,imgbase64 );
					session.persist(ssDetails);
					session.getTransaction().commit();
				}
				TimeUnit.SECONDS.sleep(value);
			} catch (AWTException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public String getDumpPath() {
		SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MM-yyyy");
		String path = basepath + File.separatorChar + dtFormat.format(new Date());
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}
	
	public static String base64Converter(String file) {
		File f = new File(file);
		FileInputStream fin;
		String imagetobase64 = null;
		try {
			fin = new FileInputStream(f);
			byte imagebytearray[] = new byte[(int) f.length()];
			fin.read(imagebytearray);
			imagetobase64 = Base64.encodeBase64String(imagebytearray);
			fin.close();
			return imagetobase64;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imagetobase64;

	}
	
	/*
	 * try {
			JSch jsch = new JSch();
			//jsch.setKnownHosts("/home/dev/Downloads/javaapps/DeskTime/known_hosts");
			jschSession = jsch.getSession(USERNAME, REMOTE_HOST);
			jschSession.setPassword(PASSWORD);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			jschSession.setConfig(config);
			
			// 10 seconds session timeout
			jschSession.connect(SESSION_TIMEOUT);
			Channel sftp = jschSession.openChannel("sftp");
			// 5 seconds timeout
			sftp.connect(CHANNEL_TIMEOUT);
			ChannelSftp channelSftp = (ChannelSftp) sftp;
			// transfer file from local to remote server
			channelSftp.put(file, remoteFile);
			// download file from remote server to local
			// channelSftp.get(remoteFile, localFile);
			channelSftp.exit();
		} catch (JSchException | SftpException e) {
			e.printStackTrace();
		} finally {
			if (jschSession != null) {
				jschSession.disconnect();
			} 
		}*/
	

	
	
	
}
