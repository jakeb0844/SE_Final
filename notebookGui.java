package tmp;

//created 4/22/16

import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class notebookGui {

	private JFrame frame;
	private static JFrame addFrame;
	static DefaultListModel listModel;
	static JPanel notePanel;
	public static JList list;
	public static JTextArea textArea;
	public static Notebook notes = new Notebook();
	public static JTextField search;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public static boolean whichOne= true;
	

	
	public notebookGui() {
		//primary method
		initialize();
		
		File f= new File("sers/Notes.ser");
		if(f.exists())
		{
			notebookGui.notes.load("Notes");
			
		}
		//loads the notes in the jlist
		startUp();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550,550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creates list model to hold the notes
		listModel = new DefaultListModel();
		
		//the panel that will be used for the newTabbedcalendar or what ever main class
		notePanel = new JPanel();
		notePanel.setBorder(BorderFactory.createTitledBorder("NoteBook"));
		notePanel.setLayout(null);
		frame.getContentPane().add(notePanel);
		
		//creates the list. Basically just for viewing and selecting
		list = new JList(listModel);
		 
		//creates scrollPane for the list
		JScrollPane scrolly = new JScrollPane(list);
		scrolly.setBounds(10, 20, 150, 350);
		scrolly.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notePanel.add(scrolly);
		
		//textArea for viewing
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		//scrollPane for the textArea
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(196, 17, 329, 353);
		scrollPane.setViewportView(textArea);
		notePanel.add(scrollPane);
		
		//button to add a note
		JButton btnAddNote = new JButton("Add Note");
		btnAddNote.setBounds(350, 390, 102, 23);
		notePanel.add(btnAddNote);
		
		//button to remove a note
		JButton btnRemoveNote = new JButton("Remove Note");
		btnRemoveNote.setBounds(350, 420, 102, 23);
		notePanel.add(btnRemoveNote);
		
		JRadioButton rdbtnSearchTitles = new JRadioButton("Search Titles");
		buttonGroup.add(rdbtnSearchTitles);
		rdbtnSearchTitles.setBounds(6, 390, 109, 23);
		rdbtnSearchTitles.setSelected(true);
		notePanel.add(rdbtnSearchTitles);
		
		//action listener for the searchtitles radio button
		//if selected whichOne gets true
		rdbtnSearchTitles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				whichOne = true;
			}
		});
		
		
		
		JRadioButton rdbtnSearchContents = new JRadioButton("Search Contents");
		buttonGroup.add(rdbtnSearchContents);
		rdbtnSearchContents.setBounds(6, 410, 109, 23);
		notePanel.add(rdbtnSearchContents);
		
		//same thing as rdbtnSearchTitles but whichOne gets false
		rdbtnSearchContents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				whichOne = false;
			}
		});
		
		JLabel display = new JLabel("Search notes for:");
		display.setBounds(6, 430, 100, 23);
		notePanel.add(display);
		
		search = new JTextField();
		search.setBounds((display.getX()+110),(display.getY()+3) , 150, 20);
		notePanel.add(search);
		
		
		//When pressed addNote method fires
		btnAddNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNote();
			}
		});
		//When pressed removeNote method fires
		btnRemoveNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(notes.getSize() > 0){
				removeNote();
				}
				else{
					JOptionPane.showMessageDialog(null,"You have no notes");
				}
			}
		});
		
		
		// Listen for changes in the text
		search.getDocument().addDocumentListener(new DocumentListener() {
			//not used
		  public void changedUpdate(DocumentEvent e) {
		  }
		  
		  public void removeUpdate(DocumentEvent e) {
			  //if search field has something in it then set the textArea to null 
			  //and then search for the indicated context
			  //else remove all elements from the listmodel and set the textarea to null
			  //then reset the listModel with the orginal content
			if(search.getText().length() > 0){  
				textArea.setText(null);
			
				searchNotes();
		    
			}
			else{
				listModel.removeAllElements();
				textArea.setText(null);
				
				for(int i=0; i < notes.getSize(); i++){
					Note n = (Note) notes.getElement(i);
					listModel.addElement(n.getTitle());
				}
			}
		    
		  }
		  
		  //just searches the notes if the size is greater than 0
		  public void insertUpdate(DocumentEvent e) {
			  textArea.setText(null);
			 if(notes.getSize() > 0){ 
		    searchNotes();
			}
		  }

		 
		});
		
		
		
		//When pressed viewNote method fires
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				viewNote();
			}
			
		});
		
	}//end initialize
	
	public static void addNote(){
		addFrame = new JFrame();
		addFrame.setBounds(700, 100, 450, 400);
		addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addFrame.getContentPane().setLayout(null);
		addFrame.setVisible(true);
		
		final Date now = new Date();
		final DateFormat dateFmt = DateFormat.getDateInstance(DateFormat.LONG);
		final DateFormat timeFmt = DateFormat.getTimeInstance (DateFormat.SHORT);
		
		
		final JTextField titleField = new JTextField();
		titleField.setBounds(180, 10, 75, 20);
		addFrame.getContentPane().add(titleField);
		
		JLabel lbltitle = new JLabel("Title:");
		lbltitle.setBounds(150, 10, 30, 20);
		addFrame.getContentPane().add(lbltitle);
		
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(5, 45, 425, 200);
		scrollPane.setViewportView(textArea);
		addFrame.getContentPane().add(scrollPane);
		
		JButton add = new JButton("Add Note");
		add.setBounds(90, 300, 120, 20);
		addFrame.getContentPane().add(add);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(220, 300, 120,20);
		addFrame.getContentPane().add(cancel);
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String title = titleField.getText();
				String content = textArea.getText();
				boolean canAdd = true;
				
				//checks for duplicates...if not, we have the same problem as the contacts
				for(int i =0; i < notes.getSize(); i++){
					Note n= (Note) notes.getElement(i);
					
					if(title.equals(n.getTitle())){
						canAdd=false;
					}
				}//edn for
				
				//checks if the title is empty.. if so pop up a message
				if(!(title.equals("")) ){
					
					//the duplication
					if(canAdd){
					
					//checks if the content of the textArea is empty... if so pop up a message
					if(!(content.equals(""))){
						String date =  dateFmt.format(now) + " " + timeFmt.format(now);
						Note n = new Note(title,content);
						
						notes.addNote(n);
						n.setDateCreated(date);
						listModel.addElement(n.getTitle());
						
						//throw away the addFrame
						addFrame.dispose();
						
					}//content
					else{
						JOptionPane.showMessageDialog(null,"Nothing was entered in the text area");
					}//content
					
				}//duplication
					else{
						JOptionPane.showMessageDialog(null,"You have already created "+ title + ". \n Please enter another title.");
					
					}//duplication
				}//title
				else{
					JOptionPane.showMessageDialog(null,"Please enter a title");
				}//title
			}
		});
		
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				addFrame.dispose();
				
			}
		
		});
		
	}//end addNote
	
	
	public static void removeNote(){
		String title = (String) list.getSelectedValue();
		
		int a = JOptionPane.showConfirmDialog(
			    addFrame,
			    "Delete this Note?",
			    "Note Conformation",
			    JOptionPane.YES_NO_OPTION);
	
		if(a == JOptionPane.YES_OPTION){
		
		
		for(int i=0; i < notes.getSize(); i++){
			Note n= (Note) notes.getElement(i);
			
			if(title.equals(n.getTitle())){
				notes.removeElement(i);
				listModel.remove(i);
				textArea.setText(null);
			}
			
		}
		
	}
}
	
	
	public static void viewNote(){
		String title = (String) list.getSelectedValue();
		
	if(title != null){	
			for(int i=0; i < notes.getSize(); i++){
				Note n = (Note) notes.getElement(i);
				if(title.equals(n.getTitle())){
					textArea.setText(null);
					textArea.setText(n.getContent()+"\n\n");
					textArea.append("Created on: " + n.getDateCreated());
					}
			}
		}
		
	}
	
	
	public static void startUp(){
		for(int i=0; i < notes.getSize(); i++){
			Note n = (Note) notes.getElement(i);
			listModel.addElement(n.getTitle());
		}
	}
	
	
	public static void searchNotes(){
		try{
		
			ArrayList<Note> holdNotes;
				//declare the arraylists
			if(whichOne){
				holdNotes = notes.searchTitle(search.getText());
			}
			else{
				holdNotes= notes.findTarget(search.getText());
			}
			
				ArrayList<String> holdTitles = new ArrayList<String>();
				
				//if there is any notes that were in the search
				//add the title to holdTitles
				if(holdNotes.size() > 0){
					
					for(int i=0; i < holdNotes.size(); i++){
						holdTitles.add(holdNotes.get(i).getTitle());
					}
					
				}
				//sort the titles (A-Z)
				Collections.sort(holdTitles);
				
				//remove the elements already in the listModel
				listModel.removeAllElements();
				
				//put the newly searched elements in the listModel
				for(int i=0; i < holdTitles.size(); i++){
					listModel.addElement(holdTitles.get(i));
				}
				
				//highlight the first selection
				list.setSelectedIndex(0);
				
				//if there are any titles in holdTitles
				//display the first title's content in the textArea
				if(holdTitles.size() > 0){	
					for(int i=0; i < notes.getSize(); i++){
						Note n = (Note) notes.getElement(i);
							
							for(int x=0;x < 1; x++){
								if(n.getTitle().equals(holdTitles.get(x))){
									textArea.setText(n.getContent() + "\n\n");
									textArea.append("Created on: " + n.getDateCreated());
								}//inner if
							}//inner for
						}//outer for
					}//outer if
		
		}catch(NullPointerException e){
			
		}
		
	}//end search
}
