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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class notebookGui {

	private JFrame frame;
	private static JFrame addFrame;
	static DefaultListModel listModel;
	static JPanel notePanel;
	public static JList list;
	public static JTextArea textArea;
	public static Notebook notes = new Notebook();
	

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					notebookGui window = new notebookGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public notebookGui() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
		btnAddNote.setBounds(152, 416, 102, 23);
		notePanel.add(btnAddNote);
		
		//button to remove a note
		JButton btnRemoveNote = new JButton("Remove Note");
		btnRemoveNote.setBounds(304, 416, 102, 23);
		notePanel.add(btnRemoveNote);
		
		//When pressed addNote method fires
		btnAddNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNote();
			}
		});
		//When pressed removeNote method fires
		btnRemoveNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeNote();
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
		
		final JTextField titleField = new JTextField();
		titleField.setBounds(180, 10, 75, 20);
		addFrame.add(titleField);
		
		JLabel lbltitle = new JLabel("Title:");
		lbltitle.setBounds(150, 10, 30, 20);
		addFrame.add(lbltitle);
		
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(5, 45, 425, 200);
		scrollPane.setViewportView(textArea);
		addFrame.add(scrollPane);
		
		JButton add = new JButton("Add Contact");
		add.setBounds(90, 300, 120, 20);
		addFrame.add(add);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(220, 300, 120,20);
		addFrame.add(cancel);
		
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
						
						Note n = new Note(title,content);
						notes.addNote(n);
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
		
		for(int i=0; i < notes.getSize(); i++){
			Note n= (Note) notes.getElement(i);
			
			if(title.equals(n.getTitle())){
				notes.removeElement(i);
				listModel.remove(i);
				textArea.setText(null);
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
					textArea.setText(n.getContent());
					}
			}
		}
		
	}
	
	
	
}
