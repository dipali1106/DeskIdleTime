package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import entity.DateWiseSystemLog;
import entity.SysemAllDayLogDetails;
import entity.SystemScreenshots;

public class HibernateUtility {

	 private static SessionFactory factory;

	    private HibernateUtility() {
	    }

	    public synchronized static SessionFactory getSessionFactory() {
	        if (factory == null) {
	            factory = new Configuration().configure("hibernate.cfg.xml")
	            		.addAnnotatedClass(DateWiseSystemLog.class).addAnnotatedClass(SysemAllDayLogDetails.class).addAnnotatedClass(SystemScreenshots.class).buildSessionFactory();
	        }
	        return factory;
	    }

	    @Override
	    protected Object clone() throws CloneNotSupportedException {
	        return new RuntimeException("Clone not Supported");
	    }
}
