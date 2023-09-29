package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

import entity.DateWiseSystemLog;
import entity.SysemAllDayLogDetails;

public class Helper {
	static SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
	public static void addLoginEntry( ) {
		
		Session session = HibernateUtility.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<DateWiseSystemLog> q = session.createQuery("from DateWiseSystemLog where SystemIp = :ip"+" AND WorkDate = :workdate", DateWiseSystemLog.class);
			q.setParameter("ip", Constants.SystemIp);
			q.setParameter("workdate", dtFormat.format(new Date()));
			List<DateWiseSystemLog> sessions = q.getResultList();
					
			if(sessions.size()==0 ) {
				DateWiseSystemLog loginEntry = new DateWiseSystemLog(Constants.SystemIp, dtFormat.format(new Date()),timeFormat.format(new Date()),timeFormat.format(new Date()),0 );							
				
				session.persist(loginEntry);
				session.getTransaction().commit();
				Constants.DateWiseId = loginEntry.getId();
				System.out.println("Row Added..."+Constants.DateWiseId);
				
			}
			else {
				//if(Constants.gapDuration/60>0) {
					DateWiseSystemLog dwslOb = sessions.get(0);
					Constants.DateWiseId = dwslOb.getId();
					long diff = getDiffDuration(dwslOb.getLastLogin());
					SysemAllDayLogDetails logDetails = new SysemAllDayLogDetails(dwslOb.getId(), timeFormat.format(new Date()), diff);
					session.persist(logDetails);
					session.getTransaction().commit();
					System.out.println("Log Row Added");
//				}
//				else {
					System.out.println("Here...");
//				}
			}
		}finally {
			session.close();
		}
	}
	
	public static long getDiffDuration(String lastlogin) {
		   Date d0;
		try {
			d0 = timeFormat.parse(lastlogin);
			 Date d1 = new Date();
			 long Time_difference  = d1.getTime() - d0.getTime();  
			 long Seconds_difference = (Time_difference/1000) % 60; 
			 return Seconds_difference;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} 
		catch(Exception e) {
			return 0;
		}
          
  
           
           
	}
	
	
	public static void updateLogEntry() {
		Session session = HibernateUtility.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();			
			if(Constants.DateWiseId!=0) {
				Query<SysemAllDayLogDetails> q2 = session.createQuery("from SysemAllDayLogDetails where SystemDayId = :id"+"  Order By Time Desc", SysemAllDayLogDetails.class)
						.setParameter("id", Constants.DateWiseId).setMaxResults(1);										
				SysemAllDayLogDetails allLog = q2.getSingleResult();
				System.out.println(allLog.getId()+"   "+allLog.getTime());
				//SysemAllDayLogDetails systemLog = session.find(SysemAllDayLogDetails.class, allLog.getId());
				//systemLog.setTime(timeFormat.format(new Date()));
				allLog.setDuration((int) Constants.gapDuration);
				//session.getTransaction().commit();
				//session.beginTransaction();	
				DateWiseSystemLog dateLog = session.find(DateWiseSystemLog.class, Constants.DateWiseId);
				dateLog.setLastLogin(timeFormat.format(new Date()));
				session.getTransaction().commit();
				System.out.println("SysemAllDayLogDetails Row Updated");				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}

}
