
package tmp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;



public class CountBirthdays {
	
	public static int numOfBdays = 50;
	public static ArrayList<Contact[][]> Cmonths = new ArrayList<Contact[][]>();

//The exact thing as the count events except with birthdays... EVERYTHING!	
	//this method is used to count the bdays
		//it uses an arraylist of [][]arrays of bdays
		//uses the indexes of the arraylist for months (0-11)
		//uses the indexes of  the rows for days.. see startArray
		//uses the indexes of the columns for max assignments
@SuppressWarnings("unchecked")
public static void countBdays(){
		
		clearCmonthsArray();
	
		int count =0;
		int day1,month;
		String des;
		ArrayList<Contact> clone;
		
		
		PackageCollection a = new PackageCollection();
		
		a = ContactGui.addressbook;

		for(int i=0; i < a.getSize(); i++){
			Contact tmp = (Contact) a.getElement(i);
			day1 = tmp.getBirthday()-1;
			month = tmp.getBirthMonthNum()-1;
			des=tmp.getFirstName() + " " + tmp.getLastName();
			
			for(int x =0; x < Cmonths.get(month).length; x++){
				if(x == day1){
					
					if(Cmonths.get(month)[x][count] != null){
					
						while(Cmonths.get(month)[x][count] != null){
							count++;
						}
											
						Contact person = new Contact(tmp.getLastName(), tmp.getFirstName(), tmp.getBirthMonthNum(),tmp.getBirthYear(),
								tmp.getBirthday(), tmp.getPhoneNumber(), tmp.getAddress());
					
						Cmonths.get(month)[x][count] = person;
						count=0;
						
					}
					else{
						Contact person = new Contact(tmp.getLastName(), tmp.getFirstName(), tmp.getBirthMonthNum(),tmp.getBirthYear(),
								tmp.getBirthday(), tmp.getPhoneNumber(), tmp.getAddress());
						
						Cmonths.get(month)[x][count] = person; 
					}
				}
			  
			}
		}
		
		
		
	/*//prints the number of events on the days	
	for(int mon =0; mon < 12; mon++){	
		//System.out.println(months.get(mon).length+"printing length");
		for(int row = 0; row < Cmonths.get(mon).length; row++){
			 for(int col = 0; col < numOfBdays; col++){
				 //March[row][col] = col;
				 if(Cmonths.get(mon)[row][col] != null){
				//System.out.println(" Month: " + mon + " [" + row +"][" + col + "] "+Cmonths.get(mon)[row][col].getFirstName() );
				 }
			 }
		 }
		}

	*/
			
		
	}

@SuppressWarnings("deprecation")
public static  void startArray(){
	Date now = new Date();
	

	
	GregorianCalendar cal = new GregorianCalendar(now.getYear(),0,1);
    int NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

	
	Contact January[][] = new Contact[NumOfDays][numOfBdays];
	
	cal = new GregorianCalendar(now.getYear(),1,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
     
     
	Contact February[][] = new Contact[NumOfDays][numOfBdays];
	
	cal = new GregorianCalendar(now.getYear(),2,1);
    NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    
    
	 Contact March[][] = new Contact [NumOfDays][numOfBdays];
	 
	 cal = new GregorianCalendar(now.getYear(),3,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
     
     
     Contact April[][] = new Contact [NumOfDays][numOfBdays];
     
     cal = new GregorianCalendar(now.getYear(),4,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
     
     
     Contact May[][] = new Contact[NumOfDays][numOfBdays];
     
     cal = new GregorianCalendar(now.getYear(),5,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
     
     
     Contact June[][] = new Contact[NumOfDays][numOfBdays];
     
     cal = new GregorianCalendar(now.getYear(),6,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
     
     
     Contact July[][] = new Contact[NumOfDays][numOfBdays];
	
     cal = new GregorianCalendar(now.getYear(),7,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
     
     
     Contact August[][] = new Contact[NumOfDays][numOfBdays];
     
     cal = new GregorianCalendar(now.getYear(),8,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
     
     
     Contact September[][] = new Contact[NumOfDays][numOfBdays];
     
     cal = new GregorianCalendar(now.getYear(),9,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    
     
     Contact October[][] = new Contact[NumOfDays][numOfBdays];
     
     cal = new GregorianCalendar(now.getYear(),10,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
     
     
     Contact November[][] = new Contact[NumOfDays][numOfBdays];
     
     cal = new GregorianCalendar(now.getYear(),11,1);
     NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    
     
     Contact December[][] = new Contact[NumOfDays][numOfBdays];
		 
	 Cmonths.add(January);
	 Cmonths.add(February);
	 Cmonths.add(March);
	 Cmonths.add(April);
	 Cmonths.add(May);
	 Cmonths.add(June);
	 Cmonths.add(July);
	 Cmonths.add(August);
	 Cmonths.add(September);
	 Cmonths.add(October);
	 Cmonths.add(November);
	 Cmonths.add(December);
	 
	 
}

public static void clearCmonthsArray(){
	
	
	for(int mon=0; mon < 12; mon++){	
		for(int row =0; row < Cmonths.get(mon).length; row++){
			 for(int col = 0; col < numOfBdays; col++){
				 //March[row][col] = col;
				 if(Cmonths.get(mon)[row][col] != null){
						Cmonths.get(mon)[row][col] = null;
					}
				 }
		}
}
}

//counts bdays on a given day
public static int getCountBirthday(int month, int day){
	int num=0;
	
		
		for(int col =0; col < numOfBdays; col++){
			if(Cmonths.get(month)[day][col] != null){
				num++;
			}
		}
		
	
	
	return num; 
}

}