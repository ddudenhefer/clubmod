package utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
	
	private final static long ONCE_PER_DAY = 1000*60*60*24;
	private final static int ONE_DAY = 1;
	private final static int ONE_AM = 1;
	private final static int ZERO_MINUTES = 0;

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		// Your code here
		System.out.println("AppContextListener Listener has been shutdown");

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		// Your code here
		System.out.println("AppContextListener Listener initialized.");

		TimerTask updatePointsTask = new UpdatePointsTask();
		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(updatePointsTask, getTomorrowMorning1am(), ONCE_PER_DAY);
	}
	
	private static Date getTomorrowMorning1am(){
		Calendar tomorrow = Calendar.getInstance();
	    tomorrow.add(Calendar.DATE, ONE_DAY);
	    Calendar result = new GregorianCalendar(
	    		tomorrow.get(Calendar.YEAR),
	    		tomorrow.get(Calendar.MONTH),
	    		tomorrow.get(Calendar.DATE),
	    		ONE_AM,
	    		ZERO_MINUTES
	    );
	    return result.getTime();
	  }
	

	class UpdatePointsTask extends TimerTask {
		
		@Override
		public void run() {
			System.out.println("UpdatePointsTask " + new Date().toString());
		}
	}

}