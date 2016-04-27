package tmp;
//4/3/16
//added a remove contact feature
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionListener;



//import Calendar.calendarGui2;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;

public class ContactGui {

	private JFrame frame;
	private static JFrame frame2;
	ListSelectionModel listSelection;
	private static JTextField firstField;	
	static JTextField lastField;
	static JTextField BirthMonthField;
	static JTextField 	BirthYearField;
	static JTextField BirthDayField;
	static JTextField phoneField;
	static JTextField 	addressField;
	static DefaultListModel listModel;
	static Contact con;
	public static JPanel yoyo,panel2;
	public static JLabel lblFirstName,lblLastName,lblBirthMonth,lblBirthyear,lblBirthDay, lblPhoneNumber,lblAddress;
	public static JList list;
	public static ArrayList<String> holdNames = new ArrayList<String>();
	public static Boolean answer=true;
	
	static AddressBook addressbook = new AddressBook();
	 

	public ContactGui() {
		
		initialize();
		
CountBirthdays.startArray();
		
		File e= new File("sers/Contacts.ser");
		if(e.exists())
		{
			addressbook.load("Contacts");
			CountBirthdays.countBdays();
		}
		
		//adds the contacts to the jlist on startup
		startUp();
	}

	protected void initialize() {
		
	
		final JButton removeContact = new JButton("Remove");
		
		
		listModel = new DefaultListModel();
		
		 yoyo = new JPanel();
		 yoyo.setBorder(BorderFactory.createTitledBorder("Contacts"));
		 panel2 = new JPanel();
		 panel2.setBounds(280, 30, 275, 300);
		 
		 panel2.setLayout(null);
			
		 list = new JList(listModel);
		 
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(list.getValueIsAdjusting()){
			    String name = (String) list.getSelectedValue();
				String temp;
				
			//if element is selected in the jlist then display the credentials	
			for(int i =0; i < addressbook.getSize(); i++){	
				Contact tempContact = (Contact)addressbook.getElement(i);
				temp = tempContact.getFirstName() + " " + tempContact.getLastName();
				
				if(name.equals(temp)){
					panel2.removeAll();
					
					
					final JLabel firstName = new JLabel(tempContact.getFirstName());
					firstName.setBounds(0, 0, 300, 14);
					panel2.add(firstName);
					
					final JLabel lastName = new JLabel(tempContact.getLastName());
					lastName.setBounds(0,20,300,14);
					panel2.add(lastName);
					
					final JLabel birthMonth = new JLabel(tempContact.getBirthMonth());
					birthMonth.setBounds(0,40,75,14);
					panel2.add(birthMonth);
					
					final JLabel birthDay = new JLabel(Integer.toString(tempContact.getBirthday()));
					birthDay.setBounds(0,60,75,14);
					panel2.add(birthDay);
					
					final JLabel birthYear = new JLabel(Integer.toString(tempContact.getBirthYear()));
					birthYear.setBounds(0,80,75,14);
					panel2.add(birthYear);
					
					final JLabel phoneNum = new JLabel(tempContact.getPhoneNumber());
					phoneNum.setBounds(0,100,75,14);
					panel2.add(phoneNum);
					
					final JLabel address = new JLabel(tempContact.getAddress());
					address.setBounds(0,120,300,14);
					panel2.add(address);
					
					
					//this is used for, if an address was wider than the panel then drop the extra down under the first line
					String extraLen = tempContact.getAddress();
					String word;
					if(extraLen.length() >= 43){
						word= extraLen.substring(43, extraLen.length());
						JLabel extra = new JLabel(word);
						extra.setBounds(0, 140, 300, 14);
						panel2.add(extra);
					}
					
					yoyo.add(panel2);
					panel2.revalidate();
					panel2.repaint();
					
				}//end if
			}//end for
		}//end if adjusting
				
				
			}
		});
		yoyo.setLayout(null);
		
		
		
		JScrollPane scrolly = new JScrollPane(list);
		scrolly.setBounds(5, 30, 150, 350);
		scrolly.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		yoyo.add(scrolly);
		
		JButton btnAddContact = new JButton("Add Contact");
		btnAddContact.setBounds(250, 350, 93, 23);
		removeContact.setBounds(350, 350, 93, 23);
		
		
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				createContact();
				
			}
		});
		
		removeContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//checks an element was selected in the jlist
				isSelected();
				
				if(addressbook.getSize()>0){	
					//if true
					if(answer){
						
						int n = JOptionPane.showConfirmDialog(
							    frame,
							    "Delete this contact?",
							    "Contact Conformation",
							    JOptionPane.YES_NO_OPTION);
					
				if(n == JOptionPane.YES_OPTION){		
				
				String name2 = (String)list.getSelectedValue();

				for(int x =0; x < addressbook.getSize(); x++){
					Contact tempContact = (Contact)addressbook.getElement(x);
					if(name2.equals(tempContact.getFirstName()+" " + tempContact.getLastName())){
						addressbook.removeElement(x);
					}
				}
				
				//loop through and remove the name from holdNames arraylist
				for(int i=0; i < holdNames.size(); i++){
					if(name2.equals(holdNames.get(i))){
						holdNames.remove(i);
					}
				}
				
				
				for(int i=0; i < listModel.size(); i++){
					if(name2.equals(listModel.getElementAt(i))){
						listModel.remove(i);
						
						panel2.removeAll();
						panel2.repaint();
						
					}
				}
				
				CountBirthdays.countBdays();
				CalendarGui2.refreshCalendar(CalendarGui2.currentMonth, CalendarGui2.currentYear);
				}
			}
		}			
				
			}
		});
		yoyo.add(btnAddContact);
		yoyo.add(removeContact);
		
		lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(200, 30, 75, 14);
		yoyo.add(lblFirstName);
		
		lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(200, 50, 75, 14);
		yoyo.add(lblLastName);
			
        lblBirthMonth = new JLabel("BirthMonth:");
		lblBirthMonth.setBounds(200, 70, 75, 14);
		yoyo.add(lblBirthMonth);
			
		lblBirthDay = new JLabel("Birth Day:");
		lblBirthDay.setBounds(200, 90, 75, 14);
		yoyo.add(lblBirthDay);
		
		lblBirthyear = new JLabel("Birth Year:");
		lblBirthyear.setBounds(200, 110, 75, 14);
		yoyo.add(lblBirthyear);
			
		lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setBounds(200, 130, 75, 14);
		yoyo.add(lblPhoneNumber);
			
		lblAddress = new JLabel("Address:");
		lblAddress.setBounds(200, 150, 75, 14);
		yoyo.add(lblAddress);
			
		
	
	}
	
	public void createContact(){
		 frame2 = new JFrame();
		frame2.setBounds(700, 100, 450, 300);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		createFields();
		
		JButton btnCreateContact = new JButton("Create Contact");
		btnCreateContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//public Contact(String lastName, String firstName, int birthMonth, int birthYear, int birthday,
				//		String phoneNumber, String address)
				boolean canCreate=true;
				String name = firstField.getText() + " " + lastField.getText();
				
				//checks for duplicates
				for(int i=0; i < addressbook.getSize(); i++){
					Contact tmp = (Contact) addressbook.getElement(i);
					String tempName= tmp.getFirstName() + " " + tmp.getLastName();
					
					if(name.equals(tempName)){
						canCreate = false;
						JOptionPane.showMessageDialog(null,"You already created a contact for " + tempName+ ". \n Please chose a different name. ");
					}
					
				}
				
				if(!(lastField.getText().equals(""))){
					if(!(firstField.getText().equals(""))){
						if(!(BirthMonthField.getText().equals(""))){
							if(!(BirthYearField.getText().equals(""))){
								if(!(BirthDayField.getText().equals(""))){
									if(!(phoneField.getText().equals(""))){
										if(!(addressField.getText().equals(""))){
					//if no duplicates
					if(canCreate){
					
					//hold names is a static arraylist to hold the name of the contacts
					//uses the sortContacts method to sort the names (A-Z)
					holdNames.add(name);
					sortContacts();
					
					listModel.clear();
					
					//displays the names (A-Z)
					for(int i = 0; i < holdNames.size(); i++){
						listModel.addElement(holdNames.get(i));
					}
					
							con = new Contact(lastField.getText(),firstField.getText(),Integer.parseInt(BirthMonthField.getText()),Integer.parseInt(BirthYearField.getText()),
							Integer.parseInt(BirthDayField.getText()),phoneField.getText(),addressField.getText());
						
					 addressbook.addContact(con);
					 
					 CalendarGui2.refreshCalendar(CalendarGui2.currentMonth, CalendarGui2.currentYear);
					//frame closes once the contact is added
	   				frame2.dispose();
						}//if can create
										}else{ //ends addressField if
											JOptionPane.showMessageDialog(null,"Please enter an Address!");
										}
							
									}else{ //ends phoneField if
										JOptionPane.showMessageDialog(null,"Please enter a Phone Number!");
									}
					
								}else{//ends BirthDayField if
									JOptionPane.showMessageDialog(null,"Please enter a Birth Day!");
								}
							
							}
						
							else{//end BirthYearField if
								JOptionPane.showMessageDialog(null,"Please enter a Birth Year!");
							}
							
												
						}	
						else{ //end BirthMonthField if
							JOptionPane.showMessageDialog(null,"Please enter a Birth Month!");
						}
						
					}//end firstfield if
					else{
						JOptionPane.showMessageDialog(null,"Please enter a first name!");
					}
					
				}//end lastfield if
				else{
					JOptionPane.showMessageDialog(null,"Please enter a last name!");
				}
					
			}//action
		});
		btnCreateContact.setBounds(100, 227, 119, 23);
		frame2.getContentPane().add(btnCreateContact);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds((btnCreateContact.getX()+120), btnCreateContact.getY(), 119, 23);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame2.dispose();
				}
			});
		frame2.getContentPane().add(cancel);
		
		
		
		frame2.setVisible(true);
		
	}
	
	//just a method to create all the fields
	//purpose... just got tired of it junking up the above method 
	public static void createFields(){
		 lblFirstName = new JLabel("First Name:");
			lblFirstName.setBounds(10, 29, 75, 14);
			frame2.getContentPane().add(lblFirstName);
			
			 lblLastName = new JLabel("Last Name:");
			lblLastName.setBounds(10, 54, 75, 14);
			frame2.getContentPane().add(lblLastName);
			
			 lblBirthMonth = new JLabel("BirthMonth:");
			lblBirthMonth.setBounds(10, 79, 75, 14);
			frame2.getContentPane().add(lblBirthMonth);
			
			 lblBirthDay = new JLabel("Birth Day:");
			lblBirthDay.setBounds(10, 128, 75, 14);
			frame2.getContentPane().add(lblBirthDay);
			
			lblBirthyear = new JLabel("BirthYear:");
			lblBirthyear.setBounds(10, 103, 75, 14);
			frame2.getContentPane().add(lblBirthyear);
			
			 lblPhoneNumber = new JLabel("Phone Number:");
			lblPhoneNumber.setBounds(10, 153, 75, 14);
			frame2.getContentPane().add(lblPhoneNumber);
			
			 lblAddress = new JLabel("Address:");
			lblAddress.setBounds(10, 178, 75, 14);
			frame2.getContentPane().add(lblAddress);
			
			firstField = new JTextField();
			firstField.setText("Jake");
			firstField.setBounds(146, 26, 86, 20);
			frame2.getContentPane().add(firstField);
			firstField.setColumns(10);
			
			 lastField = new JTextField();
			 lastField.setText("Busby");
			lastField.setColumns(10);
			lastField.setBounds(146, 51, 86, 20);
			frame2.getContentPane().add(lastField);
			
			 BirthMonthField = new JTextField();
			 BirthMonthField.setText("9");
			BirthMonthField.setColumns(10);
			BirthMonthField.setBounds(146, 76, 86, 20);
			frame2.getContentPane().add(BirthMonthField);
			
			BirthYearField = new JTextField();
			BirthYearField.setText("1993");
			BirthYearField.setColumns(10);
			BirthYearField.setBounds(146, 100, 86, 20);
			frame2.getContentPane().add(BirthYearField);
			
			 BirthDayField = new JTextField();
			 BirthDayField.setText("15");
			BirthDayField.setColumns(10);
			BirthDayField.setBounds(146, 125, 86, 20);
			frame2.getContentPane().add(BirthDayField);
			
			 phoneField = new JTextField();
			 phoneField.setText("3182821852");
			phoneField.setColumns(10);
			phoneField.setBounds(146, 150, 86, 20);
			frame2.getContentPane().add(phoneField);
			
		 	addressField = new JTextField();
		 	addressField.setText("1111111111111111111111111111111111111111111");
			addressField.setColumns(10);
			addressField.setBounds(146, 175, 86, 20);
			frame2.getContentPane().add(addressField);
	}
	
	public static void isSelected(){
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
		
				if(list.isSelectionEmpty()){
					answer=false;
				}
				else{		
					answer=true;
				}
				
			}
		});
		
		
	}
	
	public static void startUp(){
		
		for(int i=0; i < addressbook.getSize(); i++){
			Contact c = (Contact) addressbook.getElement(i);
			holdNames.add(c.getFirstName() + " " + c.getLastName());
			//listModel.addElement(c.getFirstName() + " " + c.getLastName());
		}
		
		sortContacts();
		
		for(int i=0; i < holdNames.size(); i++){
			listModel.addElement(holdNames.get(i));
		}
		
		
	}
	
	public static void sortContacts(){
		Collections.sort(holdNames);
	}
}