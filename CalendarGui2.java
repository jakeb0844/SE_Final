
package tmp;

//3/29/16
//added the feature to display the description of the event when the day is selected
//Fixed problem with the birthday selection that was rewriting to the previous event by making a new renderclass
//3/30/16
//fixed problem with the color when a event and birthday are on the same class
//3/31/16
//added the feature to display multiple events on same day.
//4/2/16
//added the feature to display multiple birthdays on same day. Also multiple birthdays and events on the same day
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class CalendarGui2 {

	static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    public static JPanel pnlCalendar;
    public static int realYear, realMonth, realDay, currentYear;
	public static int currentMonth;
	public static JComboBox comboBox;
    public static JScrollPane scrollPane;
    public static JTextArea textArea;
    
   static EventCollection eventCollection2 = new EventCollection();
    static AddressBook addressbook2 = new AddressBook();
    static ArrayList<Event2> oldEvents = new ArrayList<Event2>();
    
    public CalendarGui2(){
    	
    	run();
    }
    
    
    public static void run(){
    	 //Look and feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
        
        //Create controls
        lblMonth = new JLabel ("January");
        lblMonth.setBounds(146, 25, 180, 25);
        lblYear = new JLabel ("Change year:");
        btnPrev = new JButton ("Previous");
        btnPrev.setBounds(10, 25, 75, 25);
        btnNext = new JButton ("Next");
        btnNext.setBounds(235, 25, 75, 25);
        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);
        stblCalendar.setBounds(10, 50, 300, 250);
        pnlCalendar = new JPanel(null);
        textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		Font f = new Font(Font.SANS_SERIF, 10, 11);
		textArea.setFont(f);

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 310, 530, 205);
      
       
        
        //Set border
        pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
        
        
        //Register action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        
        //Add controls to pane
      
        pnlCalendar.setLayout(null);
       
        pnlCalendar.add(lblMonth);
       
       // pnlCalendar.add(describePanel);
        pnlCalendar.add(btnPrev);
        pnlCalendar.add(btnNext);
        pnlCalendar.add(stblCalendar);
        pnlCalendar.add(scrollPane);
        //Set bounds
        //pnlCalendar.setBounds(0, 0, 320, 335);
        pnlCalendar.setBounds(0,0, 300, 310);
        
        
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        
        //Add headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            mtblCalendar.addColumn(headers[i]);
        }
        
        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
        
        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        
        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Set row/column count
        tblCalendar.setRowHeight(38);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);
            
        //Refresh calendar
        refreshCalendar (realMonth, realYear); //Refresh calendar
    }
    
    public static void refreshCalendar(int month, int year){
    	
    	
		        //Variables
		        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		        int NumOfDays, StartOfMonth; //Number Of Days, Start Of Month
		        
		        //Allow/disallow buttons
		        btnPrev.setEnabled(true);
		        btnNext.setEnabled(true);
		        if (month == 0 && year <= realYear){btnPrev.setEnabled(false);} //Too early
		        if (month == 11 && year >= realYear){btnNext.setEnabled(false);} //Too late
		        lblMonth.setText(months[month]);
		        
		        //Clear table
		        for (int i=0; i<6; i++){
		            for (int j=0; j<7; j++){
		                mtblCalendar.setValueAt(null, i, j);
		            }
		        }
		        
		        //Get first day of month and number of days
		        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		        NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		        StartOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
		        
		        int row=0;
		        int column=StartOfMonth-1;
		        
		      //draw the calendar 
		        for(int i=1; i <= NumOfDays; i++){
		             		
		             	mtblCalendar.setValueAt(i, row, column);
		             
		             if(column<=5){
		             	column++;
		             }
		             
		           else{
		           	column=0;
		             row++;
		            }
		         }//end for loop
		        
		      
		        //takes the events from the eventcollection and puts them in the oldEvents arraylist
		        for(int i=0; i < NewTabbedCalendar.eventCollection.getSize(); i++){
		        	Event2 e = (Event2) NewTabbedCalendar.eventCollection.getElement(i);
		        	
		        	if(e.getMonth() <= currentMonth+1 && e.getDay()+1 <= realDay){
		        		oldEvents.add(e);
		        		NewTabbedCalendar.eventCollection.removeElement(i);
		        	}
		        }
		        
		        
		        
		        eventCollection2 = NewTabbedCalendar.eventCollection;
		        addressbook2=ContactGui.addressbook;
		        
		        //Apply renderers
		        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
		        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRendererBirth());
		        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRendererAssignment());
		        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRendererBoth());
		        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRendererAssinandEvent());
		        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRendererAssinandBdays());
		        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRendererALL());
		        
    }
    
    static class tblCalendarRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
           
            try{
            
		           int day=0;
		           int month=0;
		           int year=0;
		           int numOfEvents=0;
		            
		           
		          setBackground(new Color(255,255,255));
		           
		           //gives the date at the top of textArea
		           if(selected){
		        	  
		        	   if(value != null){
		        	 
		        		   textArea.setText(null);
		        		   textArea.append(currentMonth+1 + "/" + value + "/" + currentYear);
		        		   
		        	   }
		           }
		           
		           
		         //today		           
		           if (value != null){
		               if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
		                   setBackground(new Color(220, 220, 255));
		                }
		           
		           }
		           //prints today
		              if(selected){
		               	if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
		               		textArea.setText(null);
		   					textArea.append((currentMonth+1) + "/" + value + "/" + currentYear + "\n");
		   					textArea.append("Today");
		               	}
		              }
		              
		           
		           			
		          for(int i=0; i < eventCollection2.getSize(); i++){
		        	  Event2 tmp = (Event2)eventCollection2.getElement(i);
		        	   day=tmp.getDay();
		        	   month= tmp.getMonth()-1;
		        	   year= tmp.getYear();
		        	   			//makes sure there are no bdays or assignments on this day
		        	           if(CountBirthdays.getCountBirthday(month, day-1) <= 0 && CountAssignments.getCountAssignments(month+1, day) <= 0){
					    //paints 
					        if(currentYear == year ){	   
					        	if(currentMonth == month){   
					        		
					        	   if(value != null){
					        		   
					                   if (value.equals(day)){ //Week-end
					                      setBackground(new Color(255, 220, 220));
					                   }//end value == day if
					                  
					        	   }//end value != null if
					        	}//end currentmonth == month if
					        }//end currentYear == year if
		             
					   	  //appends the event description in the textarea.. only if there is an event on that day 
					      if(selected){
					    	  
					       	if(currentYear == year){
					    		if(currentMonth == month){
					        	
					    			if(value.equals(day)){
					    				
					    				numOfEvents=CountEvents.getCountEvents(month, (int) value-1);
					    				
					    				
					    				if(numOfEvents >= 1){
					    					//clears the textArea for the events
					    					textArea.setText(null);
					    					textArea.append((currentMonth+1) + "/" + day + "/" + currentYear + "\n");
					    					
					    					if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
					    						textArea.append("Today\n");
					    					}
						    				
					    					for(int multi = 0; multi < numOfEvents; multi++){
					    						
					    						textArea.append(CountEvents.months.get(month)[day-1][multi].getDescription()+"\n");
					    					}//end for loop
					    				}//end if num of events
					    				
					    			}//end value == day if
					    		}//end currentMonth if
					       	}//end currentYear if
					        
					      }//end selected if
		          }//end for
		       }
		              
		         
      }//try
            catch(NullPointerException e){
    	  
      }
            
          //today		           
	           if (value != null){
	               if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
	                   setBackground(new Color(220, 220, 255));
	                }
	           
	           }
            
            
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
    //This class is for the calendar renderer for the contacts birthdays
    static class tblCalendarRendererBirth extends tblCalendarRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            
            try{
            
			            int birthDay=0;
			            int birthMonth=0;
			            int numOfBDays=0;
			            
			  
					     for(int x=0; x < addressbook2.getSize(); x++){
					    	 Contact tmp = (Contact)addressbook2.getElement(x);
					  	   birthDay=tmp.getBirthday();
					  	   birthMonth=tmp.getBirthMonthNum()-1;
					  	
					  	   //makes sure there are no events or assignments on this day
					  	   if(CountEvents.getCountEvents(birthMonth, birthDay-1) <= 0 && CountAssignments.getCountAssignments(birthMonth+1, birthDay) <= 0){
					  	   
					  	   if(currentMonth==birthMonth){
					      	   if(value!=null){
					      		   if(value.equals(birthDay)){
					      			   
					      			   setBackground(Color.MAGENTA);
					      		   }
					      	   }
					         }
					     
			    
			  	         if(selected){
			  		   
					    		if(currentMonth == birthMonth){
					    			 
					    			if(value.equals(birthDay)){
					    				
					    				numOfBDays=CountBirthdays.getCountBirthday(birthMonth, (int)value-1);
					    				
					    				if(numOfBDays >= 1){
					    					
					    					textArea.setText(null);
					    					textArea.append((currentMonth+1) + "/" + birthDay + "/" + currentYear+"\n");
					    					
					    					if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
					    						textArea.append("Today\n");
					    					}
						    			
					    				for(int multi = 0; multi < numOfBDays; multi++){
					    					
					    					textArea.append((CountBirthdays.Cmonths.get(birthMonth)[birthDay-1][multi].getFirstName() + " "
					    						+ CountBirthdays.Cmonths.get(birthMonth)[birthDay-1][multi].getLastName()+ "'s Birthday\n"));
					    					
					    					}//end for loop
					    				}//end numOfBDays if
					    				
					    			}//end value == day if
					    		}//end currentMonth if
					       
					      }//end selected if
					     }
			      }		     
  
            }//try
            catch(NullPointerException e){
            	
            }
            
            //today		           
	           if (value != null){
	               if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
	                   setBackground(new Color(220, 220, 255));
	                }
	           
	           }
     
	return this; 
     
 } 
}
    static class tblCalendarRendererAssignment extends tblCalendarRendererBirth{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            
            //public Assignment(String title, int month, int day, int year, String courseTitle)
            try{          
			            int day=0;
			            int month=0;
			            int numOfAssignments=0;
			            int year=0;
			            
			            
			            
						     for(int x=0; x < Window.assignments.getSize(); x++){
						    	 Assignment tmp = (Assignment)Window.assignments.getElement(x);
						  	   day=tmp.getDay();
						  	   
						  	   //have to be month-1 because the months start at 0 not 1
						  	   month=tmp.getMonthNum()-1;
						  	   year=tmp.getYear();
						  	
						  	  //makes sure there are no events or bdays on this day 
						  	 if(CountEvents.getCountEvents(month, day-1) <= 0  && CountBirthdays.getCountBirthday(month, day-1) <=0){  
						  	   
						  	   if(currentMonth==month){
						      	   if(value!=null){
						      		   if(value.equals(day)){
						      			   
						      			   setBackground(Color.YELLOW);
						      		   }
						      	   }
						         }
						     
			     
			     
					     if(selected){
							  
					    if(currentYear==year){
					    	 
					 		if(currentMonth == month){
					 			
					 			if(value.equals(day)){
					 				
					 				numOfAssignments= CountAssignments.getCountAssignments(month+1, day);
					 				
					 				if(numOfAssignments >= 1){
					 					
					 					textArea.setText(null);
					 					textArea.append((currentMonth+1) + "/" + day + "/" + currentYear+"\n");
					 					
					 					if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
					 						textArea.append("Today\n");
					 					}
						    			
					 				for(int multi = 0; multi < numOfAssignments; multi++){
					 					
					 					textArea.append((CountAssignments.months.get(month+1)[day-1][multi].getCourse() + ": "+
					 					CountAssignments.months.get(month+1)[day-1][multi].getAssignment() + "\n"));
					 					
					 					}//end for loop
					 				}//end numOfAssignments if
					 				
					 			}//end value == day if
					 		}//end currentMonth if
					    
					   }//end selected if
					 }    
    		      }
				}		  	 
            }//try
            catch(NullPointerException e){
            	
            }
            
            //today		           
	           if (value != null){
	               if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
	                   setBackground(new Color(220, 220, 255));
	                }
	           
	           }
            
            return this;
            
            
        }
        
        
        
        
    }
    
    //For when an event andd birthday fall on the same day
    static class tblCalendarRendererBoth extends tblCalendarRendererAssignment{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            
            try{
           
			            int day=0;
			            int month=0;
			            int year=0;
			            int birthDay=0;
			            int birthMonth=0;
			            int numOfEvents=0;
			            int numOfBdays=0;
			            String name="";
			           
			          if(addressbook2.getSize() > 0 && eventCollection2.getSize() > 0){
			        	  
			               for(int i =0; i < addressbook2.getSize(); i++){
			            	   Contact tmp = (Contact)addressbook2.getElement(i);
			              	   name = tmp.getFirstName() + " " + tmp.getLastName();
			            	   birthDay=tmp.getBirthday();
			            	   birthMonth=tmp.getBirthMonthNum()-1;
			            	   
			            	   		//makes sure there are no assignments on this day
			            	   		if(CountAssignments.getCountAssignments(birthMonth+1, birthDay) <=0){
			            	   
			            	   		for(int x =0; x < eventCollection2.getSize(); x++){
			            	   			Event2 tmpEvent = (Event2)eventCollection2.getElement(x);
			            	   			day=tmpEvent.getDay();
			            	        	month= tmpEvent.getMonth()-1;
			            	        	year= tmpEvent.getYear();
			            	        	  
			            	        	
			            	        	   if(currentMonth == birthMonth && currentMonth == month){
			            	        		  
			            	        		   if(birthDay == day){
			            	        			   
			            	        			  if(value != null){
			            	        				  
			            	        			   if(value.equals(birthDay) && value.equals(day)){
			            	        			   
			            	        			   setBackground(Color.orange);
			            	        		   }
			            	        			   
			            	        	   }
			            	   		}
			            	     }
			            	        	   
			            	   
			            	 if(selected){
			  		   
					    		if(currentMonth == birthMonth && currentMonth == month){
					        	
					    			if(value.equals(birthDay) && value.equals(day)){
					    				
					    				numOfEvents=CountEvents.getCountEvents(month, (int) value-1);
					    				numOfBdays=CountBirthdays.getCountBirthday(birthMonth, (int)value-1);
					    				
					    				if(numOfEvents >= 1){
					    					textArea.setText(null);
					    					textArea.append((currentMonth+1) + "/" + day + "/" + currentYear+"\n");
					    					
					    					if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
					    						textArea.append("Today\n");
					    					}
						    				
					    					for(int multi = 0; multi < numOfEvents; multi++){
					    						textArea.append(CountEvents.months.get(month)[day-1][multi].getDescription()+"\n");
					    					}
					    				}
					    				
					    				if(numOfBdays >= 1){
					    					
					    					for(int multi = 0; multi < numOfBdays; multi++){
					    						textArea.append((CountBirthdays.Cmonths.get(birthMonth)[birthDay-1][multi].getFirstName() + " "
							    						+ CountBirthdays.Cmonths.get(birthMonth)[birthDay-1][multi].getLastName()+ "'s Birthday\n"));
					    					}
					    				}
					    				
					    			}//end value == day if
					    		}//end currentMonth if
					        
					      }//end selected if
			  	  
			   	   	  }//end in for
			        }//end out for
			       }
			     }//end the length if
          	
            }//try
            catch(NullPointerException e){
            	
            }
            
            //today		           
	           if (value != null){
	               if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
	                   setBackground(new Color(220, 220, 255));
	                }
	           
	           }
	return this; 
     
 } 
}
    
    static class tblCalendarRendererAssinandEvent extends tblCalendarRendererBoth{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
           
            try{
            
		           int day=0;
		           int month=0;
		           int year=0;
		           int numOfEvents=0;
		           int dayA=0;
		           int monthA=0;
		           int yearA=0;
		           int numOfAssignments=0;
		            
		          // setBackground(new Color(255,255,255));
		          
		    if(eventCollection2.getSize() > 0 && Window.assignments.getSize() > 0){     
		    	
		          for(int i=0; i < eventCollection2.getSize(); i++){
		        	  Event2 tmp = (Event2)eventCollection2.getElement(i);
		        	   day=tmp.getDay();
		        	   month= tmp.getMonth()-1;
		        	   year= tmp.getYear();

		        	   //makes sure there are no bdays on this day
		        	   if(CountBirthdays.getCountBirthday(month-1, day) <= 0){
		        	   
		        	   		for(int x =0; x < Window.assignments.getSize(); x++){
		        	   			Assignment a = (Assignment) Window.assignments.getElement(x);
		        	   			dayA = a.getDay();
		        	   			monthA=a.getMonthNum()-1;
		        	   			yearA=a.getYear();
		        	   			
		        	           
					    //paints 
					        if(currentYear == year && currentYear == yearA){	   
					        	if(currentMonth == month && currentMonth == monthA){   
					        		
					        	   if(value != null){
					        		   
					                   if (value.equals(day) && value.equals(dayA)){ //Week-end
					                      setBackground(Color.RED);
					                   }//end value == day if
					                  
					        	   }//end value != null if
					        	}//end currentmonth == month if
					        }//end currentYear == year if
		             
					   	  //appends the event description in the textarea.. only if there is an event on that day 
					      if(selected){
					    	  
					       	if(currentYear == year && currentYear == yearA){
					       		
					    		if(currentMonth == month && currentMonth == monthA){
					    			
					    			if(value.equals(day) && value.equals(dayA)){
					    				
					    				numOfEvents=CountEvents.getCountEvents(monthA, (int) value-1);
					    				
					    				
					    				if(numOfEvents >= 1){
					    					//clears the textArea for the events
					    					textArea.setText(null);
					    					textArea.append((currentMonth+1) + "/" + day + "/" + currentYear + "\n");
					    					
					    					if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
					    						textArea.append("Today\n");
					    					}
						    				
					    					for(int multi = 0; multi < numOfEvents; multi++){
					    						
					    						textArea.append(CountEvents.months.get(month)[day-1][multi].getDescription()+"\n");
					    					}//end for loop
					    				}//end if num of events
					    				
					    				numOfAssignments=CountAssignments.getCountAssignments(monthA+1, day);
					    				
					    				
					    				if(numOfAssignments >= 1){
					    					
					    					for(int multi=0; multi < numOfAssignments; multi++){
					    						
					    						textArea.append(CountAssignments.months.get(monthA+1)[day-1][multi].getCourse()+": "+
					    						CountAssignments.months.get(monthA+1)[day-1][multi].getAssignment()+"\n");
					    					}
					    					
					    				}
					    				
					    				
					    				
					    			}//end value == day if
					    		}//end currentMonth if
					       	}//end currentYear if
					        
					      }//end selected if
		          }//end assignments for
		         }
		       }//end events for
		    }//end big if    
      }//try
            catch(NullPointerException e){
    	  
      }
            //today		           
	           if (value != null){
	               if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
	                   setBackground(new Color(220, 220, 255));
	                }
	           
	           }
            return this;
        }
    }
    
    
    static class tblCalendarRendererAssinandBdays extends tblCalendarRendererAssinandEvent{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
           
            try{
            
		           int Bday=0;
		           int Bmonth=0;
		           int numOfBdays=0;
		           int dayA=0;
		           int monthA=0;
		           int yearA=0;
		           int numOfAssignments=0;
		            
		          // setBackground(new Color(255,255,255));
		          
		    if(addressbook2.getSize() > 0 && Window.assignments.getSize() > 0){     
		    	
		          for(int i=0; i < addressbook2.getSize(); i++){
		        	  Contact tmp = (Contact)addressbook2.getElement(i);
		        	   Bday=tmp.getBirthday();
		        	   Bmonth= tmp.getBirthMonthNum();
		        	   
		        	   //makes sure there are no events on this day
		        	   	if(CountEvents.getCountEvents(Bmonth-1, Bday-1) <=0){
		        	   
		        	   		for(int x =0; x < Window.assignments.getSize(); x++){
		        	   			Assignment a = (Assignment) Window.assignments.getElement(x);
		        	   			dayA = a.getDay();
		        	   			monthA=a.getMonthNum()-1;
		        	   			yearA=a.getYear();
		        	   			
		        	   					        	           
					    //paints 
					        if(currentYear == yearA){	   
					        	if(currentMonth == Bmonth-1 && currentMonth == monthA){   
					        		
					        	   if(value != null){
					        		   
					                   if (value.equals(Bday) && value.equals(dayA)){ //Week-end
					                      setBackground(Color.BLUE);
					                   }//end value == day if
					                  
					        	   }//end value != null if
					        	}//end currentmonth == month if
					        }//end currentYear == year if
		             
					   	  //appends the event description in the textarea.. only if there is an event on that day 
					      if(selected){
					    	  
					       	if(currentYear == yearA){
					       		
					    		if(currentMonth == Bmonth-1 && currentMonth == monthA){
					    			
					    			if(value.equals(Bday) && value.equals(dayA)){
					    			
						    					
					    						numOfBdays=CountBirthdays.getCountBirthday(Bmonth-1, (int)value-1);
							    				
							    				
							    				if(numOfBdays >= 1){
							    					
							    					textArea.setText(null);
							    					textArea.append((currentMonth+1) + "/" + Bday + "/" + currentYear+"\n");
							    					
							    					if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
							    						textArea.append("Today\n");
							    					}
								    			
							    				for(int multi = 0; multi < numOfBdays; multi++){
							    					
							    					textArea.append((CountBirthdays.Cmonths.get(Bmonth-1)[Bday-1][multi].getFirstName() + " "
							    						+ CountBirthdays.Cmonths.get(Bmonth-1)[Bday-1][multi].getLastName()+ "'s Birthday\n"));
							    					
							    					}//end for loop
							    				}//end numOfBDays if
					    				
					    				
					    				numOfAssignments=CountAssignments.getCountAssignments(monthA+1, dayA);
					    				
					    				
					    				if(numOfAssignments >= 1){
					    					
					    					for(int multi=0; multi < numOfAssignments; multi++){
					    						
					    						textArea.append(CountAssignments.months.get(monthA+1)[dayA-1][multi].getCourse()+": "+
					    						CountAssignments.months.get(monthA+1)[dayA-1][multi].getAssignment()+"\n");
					    					}
					    					
					    				}
					    				
					    				
					    				
					    			}//end value == day if
					    		}//end currentMonth if
					      		
					       	}//end currentYear if
					        
					      }//end selected if
		          }//end assignments for
		       }//end events for
		      }//if for count birthdays
		    }//end big if    
      }//try
            catch(NullPointerException e){
    	  
      }
            //today		           
	           if (value != null){
	               if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
	                   setBackground(new Color(220, 220, 255));
	                }
	           
	           }
            return this;
        }
    }
    
    
    static class tblCalendarRendererALL extends tblCalendarRendererAssinandBdays{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
           
            try{
            
		           int Bday=0;
		           int Bmonth=0;
		           int numOfBdays=0;
		           int dayA=0;
		           int monthA=0;
		           int yearA=0;
		           int numOfAssignments=0;
		           int dayE=0;
		           int monthE=0;
		           int yearE=0;
		           int numOfEvents=0;
		            
		          
		    if(addressbook2.getSize() > 0 && Window.assignments.getSize() > 0  && eventCollection2.getSize() > 0 ){     
		    	
		          for(int i=0; i < addressbook2.getSize(); i++){
		        	  Contact tmp = (Contact)addressbook2.getElement(i);
		        	   Bday=tmp.getBirthday();
		        	   Bmonth= tmp.getBirthMonthNum();
		        	   
		        	   //makes sure there ARE bdays, events, and assignments on the same day
		        	   	if(CountBirthdays.getCountBirthday(Bmonth-1, Bday-1) >=1 && CountEvents.getCountEvents(Bmonth-1, Bday-1) >=1
		        	   			&& CountAssignments.getCountAssignments(Bmonth, Bday) >=1){
		        	   		
		        	   		for(int x =0; x < Window.assignments.getSize(); x++){
		        	   			Assignment a = (Assignment) Window.assignments.getElement(x);
		        	   			dayA = a.getDay();
		        	   			monthA=a.getMonthNum()-1;
		        	   			yearA=a.getYear();
		        	   					        	           
			        	   			for(int y=0; y < eventCollection2.getSize(); y++){
			      		        	  Event2 e = (Event2)eventCollection2.getElement(y);
			      		        	   dayE=e.getDay();
			      		        	   monthE= e.getMonth()-1;
			      		        	   yearE= e.getYear();
		        	   			
					    //paints 
					        if(currentYear == yearA && currentYear == yearE){	   
					        	if(currentMonth == Bmonth-1 && currentMonth == monthA && currentMonth == monthE){   
					        		
					        	   if(value != null){
					        		   
					                   if (value.equals(Bday) && value.equals(dayA) && value.equals(dayE)){ //Week-end
					                      setBackground(Color.pink);
					                   }//end value == day if
					                  
					        	   }//end value != null if
					        	}//end currentmonth == month if
					        }//end currentYear == year if
		             
					   	  //appends the event description in the textarea.. only if there is an event on that day 
					     if(selected){
					    	  
					       	if(currentYear == yearA && currentYear == yearE){
					       		
					    		if(currentMonth == Bmonth-1 && currentMonth == monthA && currentMonth == monthE){
					    			
					    			if(value.equals(Bday) && value.equals(dayA) && value.equals(dayE)){
					    									    					
						    					
					    						numOfBdays=CountBirthdays.getCountBirthday(Bmonth-1, (int)value-1);
					    						
							    				if(numOfBdays >= 1){
							    					
							    					textArea.setText(null);
							    					textArea.append((currentMonth+1) + "/" + Bday + "/" + currentYear+"\n");
							    					
							    					if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){
							    						textArea.append("Today\n");
							    					}
								    			
							    				for(int multi = 0; multi < numOfBdays; multi++){
							    					
							    					textArea.append((CountBirthdays.Cmonths.get(Bmonth-1)[Bday-1][multi].getFirstName() + " "
							    						+ CountBirthdays.Cmonths.get(Bmonth-1)[Bday-1][multi].getLastName()+ "'s Birthday\n"));
							    					
							    					}//end for loop
							    				}//end numOfBDays if
					    				
					    					
					    					
					    				//counts and appends the assignments
					    				numOfAssignments=CountAssignments.getCountAssignments(monthA+1, dayA);
					    				
					    				
					    				if(numOfAssignments >= 1){
					    					
					    					for(int multi=0; multi < numOfAssignments; multi++){
					    						
					    						textArea.append(CountAssignments.months.get(monthA+1)[dayA-1][multi].getCourse()+": "+
					    						CountAssignments.months.get(monthA+1)[dayA-1][multi].getAssignment()+"\n");
					    					}
					    					
					    				}
					    				
					    				
					    				numOfEvents=CountEvents.getCountEvents(monthE, (int) value-1);
					    				
					    				if(numOfEvents >= 1){
					    								    				
					    					for(int multi = 0; multi < numOfEvents; multi++){
					    						
					    						textArea.append(CountEvents.months.get(monthE)[dayE-1][multi].getDescription()+"\n");
					    					}//end for loop
					    				}//end if num of events
					    				
					    				
					    				
					    			}//end value == day if
					    		}//end currentMonth if
					       	}//end currentYear if
					        
					      }//end selected if
			        }//end events for
		          }//end assignments for
		        	   	}
		       }//end contacts for
		          
		    }//end big if    
      }//try
            catch(NullPointerException e){
    	  
      }
            //today		           
	           if (value != null){
	               if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
	                   setBackground(new Color(220, 220, 255));
	                }
	           
	           }
            return this;
        }
    }
   
    
    static class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            
                currentMonth -= 1;
                
            
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
           
                currentMonth += 1;
                
            
            refreshCalendar(currentMonth, currentYear);
        }
    }
    
  }