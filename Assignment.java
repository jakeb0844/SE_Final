package tmp;

import java.io.Serializable;

public class Assignment 
	extends PackageCollection implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 45;

	//Array of month names. The constructor takes in month number and uses this to store month string
	private static String[] months= new String[] {"January", "February", "March", "April", "May", "June", "July",
												  "August", "September", "October", "November", "December"};

	private String title, month, course; 
	private int  day, year, grade, monthNum;
	
	
	//to do create object of type event ========================================
	public Assignment(String title, int month, int day, int year, String courseTitle) 
	{
		//super();
		this.title = title;
		this.monthNum=month;
		this.month =  monthNumberToString(month);
		this.day = day;
		this.year = year;
		this.course=courseTitle;
	}

	public Assignment()
	{
		super();
	}

	
	public String getAssignment()
	{
		return title;
	}
	
	public String getDueDate()
	{
		String r = month + "/" + day + "/" + year;
		return r;
	}

	public void setDueDate(int m, int d, int y)

	{
		month = monthNumberToString(m);
		day = d; 
		year = y;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getMonth()
	{
		return month;
	}
	
	public int getMonthNum(){
		return monthNum;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void setGrade(int g)
	{
		grade = g;
	}
	
	public int getGrade()
	{
		return grade;
	}
	
	//called by constructor to store month string instead of month number
	private String monthNumberToString(int num)
	{
		return months[num - 1];
	}
	
	public String getCourse(){
		return course;
	}
	
	public void addAssignment(Assignment a){
		super.addElement(a);
		CountAssignments.clearArray();
		CountAssignments.countAssignments();
	}
	
	public String toString()
	{
		String result = "";
		result += this.getAssignment() + "\n" + this.getDueDate();
		return result;
	}
}