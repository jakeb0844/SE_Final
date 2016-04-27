package tmp;
//3-23-16
//Modified by Jake to incorporate the new event class also added the textfields, buttons, and labels to the CalendarGui2 pane
//3-29-16
//Modified by Jake to incorporate the contact class and the contact gui
//4/3/16
//added the feature to remove an event
//4/7/16
//fixed the problem with the remove an event
//add check if the user enters a duplicate event on same day
//4/22/16
//added courses and notebook to the tabs
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



//The main(runner) class
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
//Inorder to properly run.. please make a folder that associates with this project called sers
public class NewTabbedCalendar {

	private static JFrame frame;
	private JTextField yearField;
	private JTextField monthField;
	private JTextField dayField;
	private JTextField descripField;
	public static JPanel contacts;
	public static String descrip="";
	public static int count = 0;
	public static String sen;
	public static int d;
	public static int m;
	public static int y;
	 
	//Package collection for events
	 static EventCollection eventCollection = new EventCollection();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//runs the main program
					NewTabbedCalendar window = new NewTabbedCalendar();

					window.frame.setVisible(true);
					
					//when the frame exits.. aka when the program is exited
					//save the collections
					WindowListener  exitListner = new WindowAdapter(){
						public void windowClosing(WindowEvent e){
							//this is the old events
							//have to put them back into the eventCollection for saving purposes
							//prolly could have done another collection and saved it with "oldEvents" as title
							//but this was done 20 minutes before the presentation
							for(int i=0; i < CalendarGui2.oldEvents.size(); i++){
								eventCollection.addElement(CalendarGui2.oldEvents.get(i));
							}
							
							eventCollection.save("Events");
							Window.assignments.save("Assignments");
							Window.courses.save("Courses");
							ContactGui.addressbook.save("Contacts");
							notebookGui.notes.save("Notes");
						}
					};
					frame.addWindowListener(exitListner);
					
					//checks for an upcoming event every hour(3600000 milliseconds)
					Timer t = new Timer();
					t.schedule(new TimerTask() {
					    @Override
					    public void run() {
					       for(int i=0; i < eventCollection.getSize(); i++){
					    	   Event2 e = (Event2) eventCollection.getElement(i);
					    	   
					    	   e.checkDate();
					       }
					    }
					}, 0, 3600000);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NewTabbedCalendar() {
		//Have to create instance of CalendarGui2 to access the Panel which contains the calendar
		//Probably can do this a different way, but this works for now
		
		//have to create the [][]array for count events
		//else you get an error
		CountEvents.startArray();
			
		//loads the objects into the event package
		File f = new File("sers/Events.ser");
		if(f.exists())
		{
			eventCollection.load("Events");
			//counts the events
			CountEvents.countEm();
		}
		
		//create instances of all major classes
		CalendarGui2 gui = new CalendarGui2();
		ContactGui gui2 = new ContactGui();
		Window gui3 = new Window();
		notebookGui gui4 = new notebookGui();
		
		
		initialize();
		
	}

	//this is the method to run the main program
	//really could have done a different design for all classes methods and have a main class that runs everything
	//but we never got to that
	private void initialize() {
		//gets the dimensions of the monitor
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		
		frame = new JFrame();		
		frame.setBounds((int)width/4,(int)height/12, 600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		
		
		//Creates the tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();//JTabbedPane.TOP);
		tabbedPane.setBounds(17, 6, 550,550 );
		frame.getContentPane().add(tabbedPane);
		
		
				
		//adds all the panels to tabbedPane
		tabbedPane.addTab("My Calendar", null, CalendarGui2.pnlCalendar, null);
		tabbedPane.addTab("Contacts", null, ContactGui.yoyo, null);
		tabbedPane.addTab("My Courses",null, Window.contentPane,null);
		tabbedPane.addTab("My Notes",null, notebookGui.notePanel,null);
		
		yearField = new JTextField();
		yearField.setBounds(408, 46, 86, 20);
		yearField.setText("2016");
		CalendarGui2.pnlCalendar.add(yearField);
		
		yearField.setColumns(10);
		
		monthField = new JTextField();
		monthField.setBounds(408, 77, 86, 20);
		CalendarGui2.pnlCalendar.add(monthField);
		monthField.setColumns(10);
		
		dayField = new JTextField();
		dayField.setBounds(408, 108, 86, 20);
		CalendarGui2.pnlCalendar.add(dayField);
		dayField.setColumns(10);
		
		descripField= new JTextField();
		descripField.setBounds(408, 139, 86, 20);
		CalendarGui2.pnlCalendar.add(descripField);
		descripField.setColumns(10);
		
		
		JButton btnAddEvent = new JButton("Add Event");
		JButton btnRemoveEvent = new JButton("Remove Event");
		JButton viewOldEvent = new JButton("View Old Events");
		JLabel yearLabel = new JLabel("Year:");
		JLabel monthLabel = new JLabel("Month:");
		JLabel dayLabel = new JLabel("Day:");
		JLabel descripLabel = new JLabel("Describe:");
		JLabel length = new JLabel("Length: "+count);
		yearLabel.setBounds(355, 46, 30, 10);
		monthLabel.setBounds(355, 77, 40,10);
		dayLabel.setBounds(355,108,30,10);
		descripLabel.setBounds(355, 139, 80, 10);
		
		//this really should have been in the calendar class
		CalendarGui2.pnlCalendar.add(yearLabel);
		CalendarGui2.pnlCalendar.add(monthLabel);
		CalendarGui2.pnlCalendar.add(dayLabel);
		CalendarGui2.pnlCalendar.add(descripLabel);
		
		
		
	//Button action listener that checks if the year, month, and day are in the appropriate range.. Year is only for 2016!! 	
		btnAddEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				String year = yearField.getText();
				String month = monthField.getText();
				String paragraph=descripField.getText();
				int length = paragraph.length();
				//System.out.println(length);
				try{
					//checks year
				if(year.matches("2016")){
					//checks the months (1-12)
					if(Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12 ){
						//checks the date range for each month.. see method at bottom				
						if(checkDateRange(monthField,dayField,yearField)){
						//makes sure there was something entered in the description field	
					    if(length !=0){		
					    		//makes sure no duplicates.. see method
								if(checkDuplicate(paragraph,dayField.getText(), month)){
									//makes sure the events date hasnt past.. if so dont add
									if( Integer.parseInt(dayField.getText())  >= CalendarGui2.realDay &&  Integer.parseInt(monthField.getText()) >= CalendarGui2.currentMonth+1   ){
										
									//creates the new event
									Event2 e = new Event2(Integer.parseInt(yearField.getText()), Integer.parseInt(monthField.getText()), Integer.parseInt(dayField.getText())
											, descripField.getText());
									
									eventCollection.addEvent(e);//	addEvents.add(e);
									
									//after the event is added. Sets the desripField to empty
									descripField.setText(null);
									
									//Have to refresh the calendar after every event is added
									CalendarGui2.refreshCalendar(CalendarGui2.currentMonth, CalendarGui2.currentYear);
									
									}//end if date has passed
									else{
										JOptionPane.showMessageDialog(null,"That date has already passed!");
									}
								}//end check duplicate if
								else{
									JOptionPane.showMessageDialog(null,"You already added that event!");
								}//end check duplicate else
							
					    }//end length != 0 if
					    else{
							JOptionPane.showMessageDialog(null,"No description was entered.");
						}//end else
			}//check date if
					else{
						JOptionPane.showMessageDialog(null,"Date not in range");
					}//check data else
						
		}//check month if
					
					else{
						JOptionPane.showMessageDialog(null,"Month not in range");

					}//check month else
			
			
		}//check year if
				else{
					JOptionPane.showMessageDialog(null,"Year not 2016");
				}//check year else
		
		}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null,"Please only enter numbers");
				}
			
	}//end action Perfomed method			
		});
		
		btnRemoveEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeEvent();
				
			}});
		
		viewOldEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewOldEvents();
				
			}});
		btnAddEvent.setBounds(400, 210, 89, 23);
		btnRemoveEvent.setBounds(395,240,102,23);
		viewOldEvent.setBounds(btnAddEvent.getX()-14, btnRemoveEvent.getY()+30, 120, 23);
		
		//adds the button to pnlCalendar
		CalendarGui2.pnlCalendar.add(btnAddEvent);
		CalendarGui2.pnlCalendar.add(btnRemoveEvent);
		CalendarGui2.pnlCalendar.add(viewOldEvent);
		
		
	}
	
public void removeEvent(){
		
		//create a new frame
	    final JFrame frame2 = new JFrame();
		frame2.setBounds(700, 100, 500, 350);
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		//create a new button remove
	    JButton remove = new JButton("Remove");
		remove.setBounds((frame2.getWidth()/4),250,100,30);
		frame2.getContentPane().add(remove);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds((remove.getX()+120), remove.getY(), remove.getWidth(),remove.getHeight());;
		frame2.getContentPane().add(cancel);
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame2.dispose();
				
			}
		});
		
		int count=0;
				
		 final DefaultListModel listModel = new DefaultListModel();
		
//This adds the events to the listModel above, to make it clear for the user to select
		for(int months =0; months < 12; months++){
			for(int days =0; days < CountEvents.months.get(months).length; days++){
				//count equals the number of events on that day
				count = CountEvents.getCountEvents(months, days);
				
				for(int i = 0; i < count; i++){
					
					//if an event falls on the day, add to the listModel
					if(CountEvents.months.get(months)[days][i] != null){
						
						if(months >= CalendarGui2.currentMonth && (days+1) >= (CalendarGui2.realDay)){
							
						listModel.addElement((months+1)+"/"+(days+1)+"/"+CalendarGui2.currentYear+"  "+CountEvents.months.get(months)[days][i].getDescription());
							
						}
				}//end if
			}//end i for
				
		}//end days for
	}//end months for
		
		
		//creates the list model. Mainly this is just for the looks. No functionality except for the select item
		final JList list = new JList(listModel);
			
		
		//listen for a selection
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//if item is selected
				if(list.getValueIsAdjusting()){
					
				//gets the descrip that was selected
				   descrip = (String) list.getSelectedValue();
				   
				   //gets the date and description from the selected item
					String date="";
				   //gets the date
					for(int i =0; i < descrip.length(); i++){
						if(descrip.charAt(i) != ' '){
							date+=descrip.charAt(i);
						}
						else{
							break;
						}
					}
					
					//the date
					//had to be static to work in list listener
					m=Integer.parseInt(date.substring(0, date.indexOf("/")));
					d=Integer.parseInt(date.substring(date.indexOf("/")+1,date.lastIndexOf("/")));
					y=Integer.parseInt(date.substring(date.lastIndexOf("/")+1,date.length()));
				
					//the description
					sen= descrip.substring(descrip.indexOf(" ")+2, descrip.length());
										
	
		}//end getValueIsAdjusting
				
	}});//end valueChanged
								
				
			//this is the code for the remove event button. This runs when the button is clicked			
			remove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					
					//create option pane
					int n = JOptionPane.showConfirmDialog(
						    frame,
						    "Delete this event?",
						    "Event Conformation",
						    JOptionPane.YES_NO_OPTION);
					
						//if user selected yes... perform the removal	
						if(n == JOptionPane.YES_OPTION){
							
							//loop through the events	
							for(int i =0; i < eventCollection.getSize(); i++){
								Event2 event = (Event2) eventCollection.getElement(i);
								//compare the descriptions to the selected description aka sen					
								if(sen.equals(event.getDescription())){
									//if the sen is == to description in the events array.. check the day
									if(d == event.getDay()){
										//if == check the month
										if(m == event.getMonth()){
											//if == check the year
											if(y== event.getYear()){
												
												//now remove i which will be the Event object
													eventCollection.removeElement(i);
												//Have to re-count the events
													CountEvents.countEm();
														
													//now remove the item in the listmodel
													//Loop through the list and find the items that are equal and remove the item
														for(int x =0; x < listModel.getSize(); x++){
															if(listModel.get(x).equals(descrip)){
																//System.out.println(listModel.get(x));
																//System.out.println(descrip);
																listModel.remove(x);
																if(listModel.size()==0){
																	frame2.dispose();
																}
																
													}//end listmodel equals descrip if
												}//end for loop for the listmodel if
											}//end y if
										}//end m if
										
									
									}//end d if
								}//end sen equals if
							}//end outside for
							
							//refresh calendar	
							CalendarGui2.refreshCalendar(CalendarGui2.currentMonth, CalendarGui2.currentYear);
						}//end yes/no
				}});//end actionPerformed
		
		
		//scrollpane that holds the list/listModel
		JScrollPane scrolly = new JScrollPane(list);
		scrolly.setBounds(5, 5, 475, 200);
		
		//sets the vertical scroll
		scrolly.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//add scroll pane to the frame
		frame2.getContentPane().add(scrolly);

		
	}

	// method to fill the list model created here with the events that have past
	public static void viewOldEvents(){
		
		//create a new frame
	    final JFrame frame2 = new JFrame();
		frame2.setBounds(700, 100, 500, 300);
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		//Basically cancel
		JButton Back = new JButton("Back");
		Back.setBounds(frame2.getWidth()/3, frame2.getHeight()-75, 100,23);
		frame2.getContentPane().add(Back);
		
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame2.dispose();
				
			}
		});
		
		//creates list model		
		 final DefaultListModel listModel = new DefaultListModel();
		
		//creates the list model. Mainly this is just for the looks. No functionality except for the select item
		final JList list = new JList(listModel);
		
		//Loops through and adds the old events after the date
		for(int i=0; i < CalendarGui2.oldEvents.size(); i++){
			String date = CalendarGui2.oldEvents.get(i).getMonth() + "/"+ CalendarGui2.oldEvents.get(i).getDay() + "/" +CalendarGui2.oldEvents.get(i).getYear();
			listModel.addElement(date+ "    "+CalendarGui2.oldEvents.get(i).getDescription());
		}
								
				
		//scrollpane that holds the list/listModel
		JScrollPane scrolly = new JScrollPane(list);
		scrolly.setBounds(5, 5, 475, 200);
		
		//sets the vertical scroll
		scrolly.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//add scroll pane to the frame
		frame2.getContentPane().add(scrolly);

	}
	
	//method that checks if there are duplicate events on the same day
	public Boolean checkDuplicate(String des, String day, String month){
		
	int len=	CountEvents.getCountEvents(Integer.parseInt(month)-1, Integer.parseInt(day)-1);
	
		boolean check=true;
	
		for(int i =0; i < len; i++){
			
			if(CountEvents.months.get(Integer.parseInt(month)-1)[Integer.parseInt(day)-1][i].getDescription().toLowerCase().equals(des.toLowerCase())){
				check=false;
			}
			
		}
		
		return check;
		
		
	}
	
	public static Boolean checkDateRange(JTextField monthField, JTextField dayField, JTextField yearField){
		
		int month = Integer.parseInt(monthField.getText()) -1 ;
		
		GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(yearField.getText()), month, 1);
        int NumOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        
       
        
        if(Integer.parseInt(dayField.getText())>= 1){ 
        if(Integer.parseInt(dayField.getText()) <= NumOfDays){
        	return true;
        }
        
     }
        
		return false;
		
	}
	
	
	
}//end newtabbedcalendar