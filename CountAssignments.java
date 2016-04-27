package tmp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class CountAssignments{
	
	public static ArrayList<Assignment[][]> months = new ArrayList<Assignment[][]>();
	public static final int numAssignments=50;

	//this method is used to count the assignments
	//it uses an arraylist of [][]arrays of assignments
	//uses the indexes of the arraylist for months (0-11)
	//uses the indexes of  the rows for days.. see startArray
	//uses the indexes of the columns for max assignments
	public static void countAssignments(){
		
	//starts off by clearing the arraylists of arrays of months
	clearArray();	
	
	int count =0;
	int day1,month;
	String des,course;
	
	
	PackageCollection counAssignmentsCollection = Window.assignments;
	
	//public Assignment(String title, int month, int day, int year, String courseTitle) 
	//Time to start counting the assignments
	//outer for gets the attributes for the assignments
	for(int i=0; i < counAssignmentsCollection.getSize(); i++){
		Assignment temp = (Assignment)counAssignmentsCollection.getElement(i);
		day1 = temp.getDay()-1;
		month = temp.getMonthNum();
		des=temp.getTitle();
		course=temp.getCourse();
		//inner for counts the assignments on that specific day
		//
		for(int x =0; x < months.get(month).length; x++){
	
		
			if(x == day1){
				//if the array index for the month ISNT empty then go to while loop
				if(months.get(month)[x][count] != null){
					
					//while the array index for the month isnt null then keep incrementing the count
					while(months.get(month)[x][count] != null){
						
						count++;
					}
					//Basically making a dublicate of the event in the event class					
					Assignment a = new Assignment(des,month,day1,2016,course);
					
					//adds it to the arraylist of arrays of months
					months.get(month)[x][count] = a;
					//set count to 0
					count=0;
					
				}//end if for months.get(month)
				//This else is for when the array index IS empty
				else{
					Assignment a = new Assignment(des,month,day1,2016,course);
										
					months.get(month)[x][count] = a; 
				}//end else
			}//end if x == day1
		  
		}//end innner
	}//end outter
	
	
	
//prints the number of assignments on the days
//Dont need but i liked it for debugging
/*	
for(int mon =0; mon < 12; mon++){	
	//.out.println(months.get(mon).length+"printing length");
	for(int row = 0; row < months.get(mon).length; row++){
		 for(int col = 0; col < numAssignments; col++){
			 //March[row][col] = col;
			 if(months.get(mon)[row][col] != null){
			.out.println(" Month: " + mon + " [" + row +"][" + col + "] "+months.get(mon)[row][col].getTitle() );
			 }
		 }
	 }
	}
*/
	
}//end countem
	
	
	//creates the array of months.. prolly redundant but only way i knew of
	//prolly could have been done differently
	public static  void startArray(){
		Date now = new Date();
		
			
		GregorianCalendar cal = new GregorianCalendar(now.getYear(),0,1);
        int NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        
        //Gets the number of days in each month. Sets that as the length or num of rows 
		
		Assignment January[][] = new Assignment[NumOfDays][numAssignments];
		
		cal = new GregorianCalendar(now.getYear(),1,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
		Assignment February[][] = new Assignment[NumOfDays][numAssignments];
		
		cal = new GregorianCalendar(now.getYear(),2,1);
        NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        
		 Assignment March[][] = new Assignment [NumOfDays][numAssignments];
		 
		 cal = new GregorianCalendar(now.getYear(),3,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment April[][] = new Assignment [NumOfDays][numAssignments];
         
         cal = new GregorianCalendar(now.getYear(),4,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment May[][] = new Assignment[NumOfDays][numAssignments];
         
         cal = new GregorianCalendar(now.getYear(),5,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment June[][] = new Assignment[NumOfDays][numAssignments];
         
         cal = new GregorianCalendar(now.getYear(),6,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment July[][] = new Assignment[NumOfDays][numAssignments];
		
         cal = new GregorianCalendar(now.getYear(),7,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment August[][] = new Assignment[NumOfDays][numAssignments];
         
         cal = new GregorianCalendar(now.getYear(),8,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment September[][] = new Assignment[NumOfDays][numAssignments];
         
         cal = new GregorianCalendar(now.getYear(),9,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment October[][] = new Assignment[NumOfDays][numAssignments];
         
         cal = new GregorianCalendar(now.getYear(),10,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment November[][] = new Assignment[NumOfDays][numAssignments];
         
         cal = new GregorianCalendar(now.getYear(),11,1);
         NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
         
         Assignment December[][] = new Assignment[NumOfDays][numAssignments];
		
         //adds to the arraylist
		 months.add(January);
		 months.add(February);
		 months.add(March);
		 months.add(April);
		 months.add(May);
		 months.add(June);
		 months.add(July);
		 months.add(August);
		 months.add(September);
		 months.add(October);
		 months.add(November);
		 months.add(December);
		 
		 
	}
	
	//clears the arraylist of arrays
	public static void clearArray(){
		
		for(int mon =0; mon < 12; mon++){	
			for(int row = 0; row < months.get(mon).length; row++){
				 for(int col = 0; col < numAssignments; col++){
					 if(months.get(mon)[row][col] != null){
						 months.get(mon)[row][col]=null;
					 }
					 
				 }
			 }
		}
	}//end clear array	
		
	
	
	//gets the number of assignments on that day
	public static int getCountAssignments(int month, int day){
		int num=0;
			
			for(int col =0; col < numAssignments; col++){
				if(months.get(month)[day-1][col] != null){
					num++;
				}
			}
			
			
		return num; 
		
	}//end getCountEvents
	

	
}//end count assignments class