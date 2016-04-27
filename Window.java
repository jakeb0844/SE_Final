package tmp;

//4/22/16
//Jake--- had to add another panel that sits inside the main panel, so we could dynamicly add courses and print them out

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
public class Window extends JFrame {

	
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane,coursePane;  //creates a global content pane
	private static JEditorPane textArea;
	public static Course courses = new Course();
	public static Assignment assignments = new Assignment();
	public static String tmpTitle="";
	 

	public Window() {
		start();
		
		
		File test = new File("sers/Courses.ser");
		if(test.exists())
		{
			courses.load("Courses");

		}
		
		test = new File("sers/Assignments.ser");
		if(test.exists())
		{
			assignments.load("Assignments");
			CountAssignments.startArray();
			CountAssignments.countAssignments();
		}
	
		
	}
	
	public void start(){
		
		//creates a Course label
		setTitle("Course");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel CourseLabel = new JLabel("Course");
		CourseLabel.setBounds(10, 11, 66, 18);
		CourseLabel.setFont(new Font("Verdana", Font.BOLD, 15));
		contentPane.add(CourseLabel);
		
		
		coursePane = new JPanel();
		coursePane.setBounds(5, 40, 530, 240);
		coursePane.setLayout(null);
		contentPane.add(coursePane);
		
		//Creates a Days label
		JLabel DaysLabel = new JLabel("Days");
		DaysLabel.setBounds(193, 6, 46, 28);
		DaysLabel.setFont(new Font("Verdana", Font.BOLD, 15));
		contentPane.add(DaysLabel);
		
		//Creates a Days Misses label
		JLabel DMLabel = new JLabel("Days Missed");
		DMLabel.setBounds(269, 10, 102, 20);
		DMLabel.setFont(new Font("Verdana", Font.BOLD, 15));
		contentPane.add(DMLabel);
		
		//Creates a Max Skip Days label
		JLabel lblNewLabel_1 = new JLabel("Max Skip Days");
		lblNewLabel_1.setBounds(401, 5, 128, 31);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 15));
		contentPane.add(lblNewLabel_1);
		
		//Creates a scrollpane outside the textArea
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 283, 518, 169);
		contentPane.add(scrollPane);
		
		//Initializes the textArea 
		textArea = new JEditorPane("text/html","");
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		//textArea.setLineWrap(true);
		
		//Button for adding new course
		JButton btnAddCourse = new JButton("Add Course");
		btnAddCourse.addActionListener(new ActionListener() {
			//When pressed, a create course window pops up
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				if(courses.getSize() < 8){
				CreateCourseWindow win = new CreateCourseWindow();
				}
				else{
					JOptionPane.showMessageDialog(null,"You have the maxium amount of coures");
				}
			}
		});
		btnAddCourse.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnAddCourse.setBounds(10, 460, 160, 23);
		contentPane.add(btnAddCourse);
		
		//button for adding new assignment
		JButton btnAddAssignment = new JButton("Add Assignment");
		btnAddAssignment.addActionListener(new ActionListener() {
			//when pressed, an add assignment window pops up
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(courses.getSize() > 0){
				AddAssignmentWindow aaw = new AddAssignmentWindow();	
				
				}
				else{
					JOptionPane.showMessageDialog(null,"Please create a course before you add an assignment");
				}
			}
		});
		btnAddAssignment.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnAddAssignment.setBounds(370, 460, 160, 23);
		contentPane.add(btnAddAssignment);
		
		JButton removeCourse= new JButton("Remove Course");
		removeCourse.setBounds(10, 490, 160, 23);
		removeCourse.setFont(new Font("verdana",Font.PLAIN,12));
		contentPane.add(removeCourse);
		
		removeCourse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(courses.getSize() > 0){
					removeCourse();
				}
				else{
					JOptionPane.showMessageDialog(null,"You have no courses.");
				}
				
			}
		});
		
		
		JButton removeAssignment= new JButton("Remove Assignment");
		removeAssignment.setBounds(370, 490, 160, 23);
		removeAssignment.setFont(new Font("Verdana", Font.PLAIN, 12));
		contentPane.add(removeAssignment);
		
		removeAssignment.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(assignments.getSize() > 0){
				removeAssignment();
				}
				else{
					JOptionPane.showMessageDialog(null,"You have no assignments.");
				}
			}
		});
		
		
		
		//printCourse on content pane
		printCourses();
		
		
		//prints assignments on textarea
		printAssignments();
		
		CalendarGui2.refreshCalendar(CalendarGui2.currentMonth, CalendarGui2.currentYear);
		
		
	}
	
	public static void makeLabel(final Course a, int x, int y, int w, int h){
				//Prints the course's Title
				JLabel lblNewLabel = new JLabel(a.getCourseTitle());
				lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
				lblNewLabel.setBounds(x, y, w, h);
				coursePane.add(lblNewLabel);
				
				//Prints the days the course meets
				JLabel lblNewLabel_2 = new JLabel(a.getMd());
				lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 12));
				lblNewLabel_2.setBounds(x+200, y, 46, h);
				coursePane.add(lblNewLabel_2);
				
				//Prints the amount of days skipped
				final JLabel lblNewLabel_3 = new JLabel(String.valueOf(a.getAbs()));
				lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 12));
				lblNewLabel_3.setBounds(x+300, y, 46, h);
				coursePane.add(lblNewLabel_3);
				
				//prints the max amount of days a person can skip
				JLabel lblNewLabel_4 = new JLabel(String.valueOf(a.getPabs()));
				lblNewLabel_4.setFont(new Font("Verdana", Font.PLAIN, 12));
				lblNewLabel_4.setBounds(x+440, y, 46, h);
				coursePane.add(lblNewLabel_4);
				
				//creates an add button to the right of the days skipped label
				JButton button = new JButton("+");
				button.setFont(new Font("Verdana", Font.PLAIN, 4));
				button.setBounds(x+325, y, 37, 23);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						a.skipped(); //adds a skipped day
						lblNewLabel_3.setText(String.valueOf(a.getAbs()));
					
					}
				});
				coursePane.add(button);
				
				//creates a minus button that brings the value of the days skipped label down
				JButton button_1 = new JButton("-");
				button_1.setFont(new Font("Verdana", Font.PLAIN, 6));
				button_1.setBounds(x+250, y, 37, 23);
				button_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(a.getDaysSkipped() >=1){
						a.errSkip(); //subtracts a skip day		
						lblNewLabel_3.setText(String.valueOf(a.getDaysSkipped()));
						}
					}
				});
				coursePane.add(button_1);
							
	}

	
	public static void printAssignments(){
		
		textArea.setText(null);
		String text="";
		
		for(int i=0; i < courses.getSize(); i++){
			Course c = (Course) courses.getElement(i);
			String title = c.getCourseTitle();
			text+="<b><u>"+title+": </u></b>"+"<br>";
			
			for(int x=0; x < assignments.getSize(); x++){
				Assignment a = (Assignment) assignments.getElement(x);
				 
				if(title.equals(a.getCourse())){
					text+=a.getDueDate() + " " + a.getAssignment()+"<br>";
				}
			}
			
			text+="<br>";
			textArea.setText(text);
			
		}
		
	}
	
	
	public static void printCourses()
	{
		int x = 0;
		int y = 0;
		int w = 200;
		int h = 14;
		
		
		coursePane.removeAll();
		
		for(int i = 0; i < courses.getSize(); i++)
		{
			makeLabel((Course)courses.getElement(i), x, y+(i*30), w, h);
		}
		
		coursePane.repaint();
		
		printAssignments();
		
	}
	
	
	public static void removeCourse(){
		final JFrame frame = new JFrame("Remove Course");		
		frame.setBounds(100, 150, 300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//start the radio button class
		radioButtons rBtn = new radioButtons(5,40,200,120);
		frame.getContentPane().add(radioButtons.radioPanel);
		
		JLabel letters = new JLabel("Select a course to remove.");
		letters.setBounds((frame.getWidth()/7), 5, 300, 20);
		letters.setFont(new Font("Verdana", Font.PLAIN, 15));
		frame.getContentPane().add(letters);
		
		JButton remove = new JButton("Remove Course");
		remove.setBounds(5, (frame.getHeight()-100), ((frame.getWidth()/2)-25), 20);
		frame.getContentPane().add(remove);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(((frame.getWidth()/2)+5), frame.getHeight()-100, ((frame.getWidth()/2)-25), 20);
		frame.getContentPane().add(cancel);
		
		remove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();
				
				if(!(tmpTitle.equals(""))){
				
				int n = JOptionPane.showConfirmDialog(
					    contentPane,
					    "Delete this course?",
					    "Course Conformation",
					    JOptionPane.YES_NO_OPTION);
			
				if(n == JOptionPane.YES_OPTION){	
										
				for(int r=0; r < 1; r++){	
					
					for(int i=0; i < courses.getSize(); i++){
						Course c = (Course) courses.getElement(i);
													
							for(int y=0; y < assignments.getSize(); y++){
								Assignment a = (Assignment) assignments.getElement(y);
								
								if(a.getCourse().equals(tmpTitle)){
									assignmentList.add(a);
								}
								
							}
														
							for(int a =0; a < assignments.getSize(); a ++){
								
								Assignment wes = (Assignment) assignments.getElement(a);
								
								for(int x =0; x < assignmentList.size(); x++){
									
									if(assignmentList.get(x).getCourse().equals(tmpTitle)){
										if(wes.getCourse().equals(tmpTitle)){
										assignments.removeElement(a);
										}
									}//if
							}//inner for
						}		
							
							if(c.getCourseTitle().equals(tmpTitle)){
								courses.removeElement(i);
							}
							
					}//outer for
					
				   }
				}//conformation if
				
					
				printCourses();
				printAssignments();
				CountAssignments.clearArray();
				CountAssignments.countAssignments();
				frame.dispose();
			}//if no course is selected
				else{
					JOptionPane.showMessageDialog(null,"Please choose a course!");
				}
						
		}//action
	});
		
	}
	
	
	public static void removeAssignment(){
				//create a new frame
	    final JFrame frame2 = new JFrame("Remove Assignment");
		frame2.setBounds(700, 100, 500, 300);
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		//create a new button remove
	    JButton remove = new JButton("Remove");
		remove.setBounds(140,(frame2.getHeight()-75),100,30);
		frame2.getContentPane().add(remove);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds((remove.getX()+120), remove.getY(), 100,30);
		frame2.getContentPane().add(cancel);
		
		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				frame2.dispose();
			}
		});
		int count=0;
				
		 final DefaultListModel listModel = new DefaultListModel();
		
//This adds the events to the listModel above, to make it clear for the user to select
		 for(int i=0; i < assignments.getSize(); i++){
			 Assignment a = (Assignment) assignments.getElement(i);
			 listModel.addElement(a.getAssignment());
		 }
		
		
		//creates the list model. Mainly this is just for the looks. No functionality except for the select item
		final JList list = new JList(listModel);
			
								
				
			//this is the code for the remove event button. This runs when the button is clicked			
			remove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String sen ="";
					int d=0,m=0,y=0;
					
					sen = (String) list.getSelectedValue();
					
					
					for(int i=0; i < assignments.getSize(); i++){
						Assignment a = (Assignment) assignments.getElement(i);
						
						if(a.getAssignment().equals(sen)){
							d=a.getDay();
							m=a.getMonthNum();
							y=a.getYear();
						}
					}
					
					//create option pane
					int n = JOptionPane.showConfirmDialog(
						    frame2,
						    "Delete this event?",
						    "Event Conformation",
						    JOptionPane.YES_NO_OPTION);
					
						//if user selected yes... perform the removal	
						if(n == JOptionPane.YES_OPTION){
							
							//loop through the events	
							for(int i =0; i < assignments.getSize(); i++){
								Assignment assignment = (Assignment) assignments.getElement(i);
								//compare the descriptions to the selected description aka sen					
								if(sen.equals(assignment.getAssignment())){
									//if the sen is == to description in the events array.. check the day
									if(d == assignment.getDay()){
										//if == check the month
										if(m == assignment.getMonthNum()){
											//if == check the year
											if(y== assignment.getYear()){
												
												//now remove i which will be the Event object
													assignments.removeElement(i);
												//Have to re-count the events
													CountEvents.countEm();
														
													//now remove the item in the listmodel
													//Loop through the list and find the items that are equal and remove the item
														for(int x =0; x < listModel.getSize(); x++){
															
															if(listModel.get(x).equals(sen)){
																listModel.remove(x);
																
																if(listModel.size()==0){
																	frame2.dispose();
																}
																//if the size is == 0.. then add the text "No Events"
																
													}//end listmodel equals descrip if
												}//end for loop for the listmodel if
											}//end y if
										}//end m if
										
									
									}//end d if
								}//end sen equals if
							}//end outside for
							
							//refresh calendar
							CountAssignments.clearArray();
							CountAssignments.countAssignments();
							CalendarGui2.refreshCalendar(CalendarGui2.currentMonth, CalendarGui2.currentYear);
							printAssignments();
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
	
	
}
