package tmp;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AddAssignmentWindow extends JFrame {

	private JPanel contentPane;
	private JTextField aTitle;
	private JTextField Month;
	private JTextField Day;
	private JTextField Year;
	
	//static string to get the course name from radio panel
	public static String cT="";


	public AddAssignmentWindow() {
		final JFrame frame = new JFrame();
		frame.setTitle("Add Assignment");
		frame.setBounds(500, 100, 300, 350);
		frame.setVisible(true);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		frame.getContentPane().add(contentPane);
		
		JLabel AssignmentTitle = new JLabel("Assignment");
		AssignmentTitle.setFont(new Font("Verdana", Font.PLAIN, 12));
		AssignmentTitle.setBounds(99, 11, 114, 14);
		contentPane.add(AssignmentTitle);
		
		aTitle = new JTextField();
		aTitle.setFont(new Font("Verdana", Font.PLAIN, 12));
		aTitle.setBounds(10, 36, 264, 20);
		contentPane.add(aTitle);
		aTitle.setColumns(10);
		
		JLabel DueDate = new JLabel("DueDate");
		DueDate.setFont(new Font("Verdana", Font.PLAIN, 12));
		DueDate.setBounds(99, 69, 80, 14);
		contentPane.add(DueDate);
		
		Month = new JTextField();
		Month.setText("MM");
		Month.setFont(new Font("Verdana", Font.PLAIN, 12));
		Month.setBounds(73, 94, 26, 20);
		contentPane.add(Month);
		Month.setColumns(10);
		
		//focus listner to selectall in the month field
		Month.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent evt) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                Month.selectAll();
		            }
		        });
		    }
		});		
		Day = new JTextField();
		Day.setFont(new Font("Verdana", Font.PLAIN, 12));
		Day.setText("DD");
		Day.setBounds(109, 94, 26, 20);
		contentPane.add(Day);
		Day.setColumns(10);
		
		//focus listner to selectall in the day field
		Day.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent evt) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                Day.selectAll();
		            }
		        });
		    }
		});	
		
		Year = new JTextField();
		Year.setText("YYYY");
		Year.setFont(new Font("Verdana", Font.PLAIN, 12));
		Year.setBounds(147, 94, 53, 20);
		contentPane.add(Year);
		Year.setColumns(10);
		
		//focus listner to selectall in the year field
		Year.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent evt) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                Year.selectAll();
		            }
		        });
		    }
		});	
		
		//creates the radio buttons... look at radioButtons class		
		radioButtons r = new radioButtons(10, 120, 200, 120);
		contentPane.add(radioButtons.radioPanel);
		
		//adds a new button... not yet completed
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			//Adds assignment to course
			public void actionPerformed(ActionEvent e) {
			try{	
				//Parameters for the asssignment object
				String aT;
				int m=0,d=0,y=0;
				
				if(!(aTitle.getText().equals(""))){
				 aT = aTitle.getText();
				 
					if(!(Month.getText().equals(""))){
					 m = Integer.parseInt(Month.getText()) ;
					 
						 if(!(Day.getText().equals(""))){
						 d = Integer.parseInt(Day.getText());
						 
							 if(!(Year.getText().equals(""))){
							 
							 y = Integer.parseInt(Year.getText());
							 
								 if(!(cT.equals(""))){
									 //checks month range
									 if(Integer.parseInt(Month.getText()) >= 1 && Integer.parseInt(Month.getText()) <= 12 ){
										 
										 if(NewTabbedCalendar.checkDateRange(Month,Day,Year)){
											 
											 if(Year.getText().equals("2016")){
												 
												 if(Integer.parseInt(Day.getText()) >= CalendarGui2.realDay && Integer.parseInt(Month.getText()) >= CalendarGui2.currentMonth){
								
										int x = JOptionPane.showConfirmDialog(
											    frame,
											    "Add this assignment to " + cT+"?",
											    "Assignment Conformation",
											    JOptionPane.YES_NO_OPTION);
									
										if(x == JOptionPane.YES_OPTION){
										
											
										Assignment a = new Assignment(aT,m,d,y,cT);
										
										//adds assignment then re count them
										Window.assignments.addAssignment(a);
										CountAssignments.countAssignments();
											
										//writes out the assignments... the method is in the window class
										Window.printAssignments();
										
										frame.dispose();
										}//yes/no
										
										}//end date has passed
												 else{
													 JOptionPane.showMessageDialog(null,"Date has passed!");
												 }
												 
										 }//year
											 
										 else{
											 JOptionPane.showMessageDialog(null,"Year not in range!");
										 }
											 
										}//end date check
										 
										 else{
											 JOptionPane.showMessageDialog(null,"Day not in range!");
										 }
									 }//end year check
									 else{
										 JOptionPane.showMessageDialog(null,"Month not in range!");
									 }
							 }//end course if
								 else{
									 JOptionPane.showMessageDialog(null,"Please select a course!");
								 }
							
							 }
								 else{
									 JOptionPane.showMessageDialog(null,"Please enter a year!");
								 }
							
											
							 }//end day if
							 else{
								 JOptionPane.showMessageDialog(null,"Please enter a day!");
							 }
							
					
					}//end month if
					else{
						JOptionPane.showMessageDialog(null,"Please enter a month!");
					}
				
				
			}//end title if
			else{
				JOptionPane.showMessageDialog(null,"Please enter a title!");
			}
				
			}
			catch(NumberFormatException ee){
				JOptionPane.showMessageDialog(null,"Please only enter numbers!");
			}
				
			}
		});
		btnAdd.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnAdd.setBounds(10, 277, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnCancel.setBounds(185, 277, 89, 23);
		contentPane.add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				
			}
		});
		
		
	}
	
}