package tmp;
//Cory Wheeless
//3-18


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/*
 * Since many of the classes use some type of event to notify the user of something upcoming, i made a class. 
 * When an event is created, it stores the day of the event. Ideally, when the user starts up the application, some routine
 * would run similar to the check date below, but would check for all events.
 * */

public class Event2
	implements Serializable
{
	
	private static final long serialVersionUID = 45;
	private Calendar calendar;
	private Date current;
	private Date event;
	private int theYear;
	private int theMonth;
	private int theDay;
	private String theDescription;
	
	public Event2(int year, int month, int day, String description)
	{
		calendar = Calendar.getInstance();
		current = calendar.getTime();

		calendar.set(year, month, day);
		event = calendar.getTime();
		
		 theYear = year;
		 theMonth= month;
		 theDay= day;
		 theDescription=description;
	
	}
	
	
	@SuppressWarnings("deprecation")
	public void checkDate()
	{	
		Date temp = event;
		temp.setMonth(theMonth - 1);
		long millisecondsBetween = temp.getTime() - current.getTime();
		long fiveDays = 432000000;									//milliseconds in 5 days
		
		
		
		if(millisecondsBetween <= fiveDays && millisecondsBetween > 0)
		{
			System.out.println("Upcoming event in " + (((millisecondsBetween / 1000) / 60) / 60) / 24 + " days");
			System.out.println(theDescription + "\n");
		}
		else
		{
			System.out.println("No upcoming event");
		}
	}
	
	
	public int getYear(){
		return theYear;
	}
	
	public int getMonth(){
		return theMonth;
	}
	
	public int getDay(){
		return theDay;
	}
	
	public String getDescription(){
		return theDescription;
	}
	
	public String toString()
	{
		String result = "";
		result = theDescription + "\n" + theYear + "\n";
		return result;
	}
	
}