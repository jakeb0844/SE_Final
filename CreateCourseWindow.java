package tmp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Window.Type;

public class CreateCourseWindow extends JFrame {

	
	private static final long serialVersionUID = 45;
	private JFrame frame;
	private JTextField CourseTitle;
	private JTextField CourseMeetDays;
	private JTextField MaxAbs;
	private JTextField CourseHours;

	
	public CreateCourseWindow() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		//Creates a textfield for the input of a course title
		CourseTitle = new JTextField();
		CourseTitle.setFont(new Font("Verdana", Font.PLAIN, 12));
		CourseTitle.setBounds(25, 34, 144, 20);
		frame.add(CourseTitle);
		CourseTitle.setColumns(10);
		
		//creates course title label
		JLabel lblCourseTitle = new JLabel("Course Title");
		lblCourseTitle.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblCourseTitle.setBounds(26, 11, 85, 14);
		frame.add(lblCourseTitle);
		
		//creates course meet days label
		JLabel CourseMeetDayslbl = new JLabel("Days Course Meet");
		CourseMeetDayslbl.setFont(new Font("Verdana", Font.PLAIN, 12));
		CourseMeetDayslbl.setBounds(25, 65, 123, 14);
		frame.add(CourseMeetDayslbl);
		
		//creates a course meet days text field
		CourseMeetDays = new JTextField();
		CourseMeetDays.setText("MWF");
		CourseMeetDays.setFont(new Font("Verdana", Font.PLAIN, 12));
		CourseMeetDays.setBounds(25, 90, 59, 20);
		frame.add(CourseMeetDays);
		CourseMeetDays.setColumns(10);
		
		//creates a max abs label
		JLabel lblNewLabel = new JLabel("Max Absensces");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel.setBounds(25, 121, 123, 14);
		frame.add(lblNewLabel);
		
		//creates a max abs text field
		MaxAbs = new JTextField();
		MaxAbs.setText("8");
		MaxAbs.setFont(new Font("Verdana", Font.PLAIN, 12));
		MaxAbs.setBounds(25, 146, 51, 20);
		frame.add(MaxAbs);
		MaxAbs.setColumns(10);
		
		//creates a course hours label
		JLabel lblCourseHours = new JLabel("Course Hours");
		lblCourseHours.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblCourseHours.setBounds(25, 177, 94, 14);
		frame.add(lblCourseHours);
		
		//creates a course hours text field
		CourseHours = new JTextField();
		CourseHours.setText("3");
		CourseHours.setFont(new Font("Verdana", Font.PLAIN, 12));
		CourseHours.setBounds(25, 202, 51, 20);
		frame.add(CourseHours);
		CourseHours.setColumns(10);
		
		//creates a button that enables a user to add course
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			//Action Listener adds the course
			public void actionPerformed(ActionEvent e) {
				String cTitle="",cMd="";
				int cMaxD=0,cHrs=0;
				boolean noDuplicate=true;
			try{
				//makes sure none of the textfield are empty
				if(!(CourseTitle.getText().equals(""))){
					if(!(CourseMeetDays.getText().equals(""))){
						if(!(MaxAbs.getText().equals(""))){
							if(!(CourseHours.getText().equals(""))){
								
								//checks if there are any duplicates
								for(int i=0; i < Window.courses.getSize();i++){
									Course c = (Course) Window.courses.getElement(i);
									
									if(c.getCourseTitle().equals(CourseTitle.getText())){
										noDuplicate=false;
									}
								}
						
								if(noDuplicate){
								
										cTitle=CourseTitle.getText();
										cMd = CourseMeetDays.getText();
										cMaxD = Integer.parseInt(MaxAbs.getText());
										cHrs = Integer.parseInt(CourseHours.getText());
										
										//public Course(String title, String meetDays, int pAbs, int creditHours, String term, int year)
										Course x = new Course(cTitle, cMd, cMaxD, cHrs);
										
										
										Window.courses.addCourse(x);
										
										Window.printCourses();
										
										
										frame.dispose();
								}//no duplicate
								else{
									JOptionPane.showMessageDialog(null,"You have already created that course!");
								}
							}//ends Course Hours if
							else{
								JOptionPane.showMessageDialog(null,"Please enter Course Hours!");
							}
						} //ends MaxAbs if
						else{
							JOptionPane.showMessageDialog(null,"Please enter the Max Course Days!");
						}
					} //ends CourseMeetDays if
					else{
						JOptionPane.showMessageDialog(null,"Please enter Course Meet Days!");
					}
				
				}//end CourseTitle if
				else{
					JOptionPane.showMessageDialog(null,"Please enter a Course Title");
				}
				
				
			}
			catch(NumberFormatException eee){
				JOptionPane.showMessageDialog(null,"Please enter only numbers!");
			}
			}
		});
		
		btnAdd.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnAdd.setBounds(185, 168, 89, 23);
		frame.add(btnAdd);
		
		//creates a cancel button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			//Action listener should close window
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancel.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnCancel.setBounds(185, 201, 89, 23);
		frame.add(btnCancel);
	}
}
