package tmp;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class Course 
	extends PackageCollection implements Serializable
{
	
	private static final long serialVersionUID = 45;
	private String title;  //represents the title of the class
	private String meetDays; //represents the days the class meets
	private int pAbs; //represents max amount of days that can be missed
	private int abs = 0;  //represents days missed
	private int Fgrade;  //represents final grade
	private int creditHours;
	private String term;
	private int year;
	
	public Course()
	{
		super();
	}
	
	public Course(String title, String meetDays, int pAbs, int creditHours)
	{
		this.title = title;
		this.meetDays = meetDays;
		this.pAbs = pAbs;
		this.creditHours = creditHours;
	}
	
	public Course(String title, String meetDays, int pAbs, int creditHours, String term, int year)
	{
		this.title = title;
		this.meetDays = meetDays;
		this.pAbs = pAbs;
		this.creditHours = creditHours;
		this.term = term;
		this.year = year;
	}
	
	//Jake--- had to add this so we could store courses..
	@SuppressWarnings("unchecked")
	public void addCourse(Course c){
		super.addElement(c);
	}
	
	public String getCourseTitle()
	{
		return title;
	}
	
	//sets the name of the course
	public void setCourse(String c)
	{  
		title = c;
	}
	
	public int getCreditHours()
	{
		return creditHours;
	}
	
	public String getTerm()
	{
		return term;
	}
	
	public int getYear()
	{
		return year;
	}
	
	//Sets Days you meet for class
	public void setMD(String md)
	{ 
		meetDays = md;
	}
	
	public String getMd()
	{
		return meetDays;
	}
	
	//Max Days that can be missed
	public void setDays(int i)
	{  
		pAbs = i;
	}
	
	//skip class
	public void skipped()
	{   
		abs++;
	}
	
	public int getPabs()
	{
		return pAbs;
	}
	
	public int getDaysSkipped()
	{
		return abs;
	}
	
	//returns possible days skipped
	public int daysLeft()
	{   
		
		return pAbs - abs;
	}
	
	public int getAbs()
	{
		return abs;
	}
	
	public void errSkip()
	{
		abs--;
	}
	
	//lets you know your final grade for the course
	public void setFinalGrade(int g)
	{  
		Fgrade = g;
	}
	
	public int getFinalGrade()
	{
		return Fgrade;
	}
	

}