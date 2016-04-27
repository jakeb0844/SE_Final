package tmp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class radioButtons extends JPanel implements ActionListener {
		
		public static JPanel radioPanel;
		private JRadioButton[] buttons = new JRadioButton[8];
		
   public radioButtons(int x, int y, int w, int h) {
		super(new BorderLayout());
		
		//Create the radio buttons.
		JRadioButton class1 = new JRadioButton();
		class1.setVisible(false);
		
		JRadioButton class2 = new JRadioButton();
		class2.setVisible(false);
		
		JRadioButton class3 = new JRadioButton();
		class3.setVisible(false);
		
		JRadioButton class4 = new JRadioButton();
		class4.setVisible(false);
		
		JRadioButton class5 = new JRadioButton();
		class5.setVisible(false);
		
		JRadioButton class6 = new JRadioButton();
		class6.setVisible(false);
		
		JRadioButton class7 = new JRadioButton();
		class7.setVisible(false);
		
		JRadioButton class8 = new JRadioButton();
		class8.setVisible(false);
		
		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(class1);
		group.add(class2);
		group.add(class3);
		group.add(class4);
		group.add(class5);
		group.add(class6);
		group.add(class7);
		group.add(class8);
		//Register a listener for the radio buttons.
		class1.addActionListener(this);
		class2.addActionListener(this);
		class3.addActionListener(this);
		class4.addActionListener(this);
		class5.addActionListener(this);
		class6.addActionListener(this);
		class7.addActionListener(this);
		class8.addActionListener(this);
		
			
		//Put the radio buttons in a column in a panel.
	    radioPanel = new JPanel(new GridLayout(0, 1));
	    //radioPanel.setBounds(10, 120, 200, 100);
	    radioPanel.setBounds(x, y, w, h);
		radioPanel.add(class1);
		radioPanel.add(class2);
		radioPanel.add(class3);
		radioPanel.add(class4);
		radioPanel.add(class5);
		radioPanel.add(class6);
		radioPanel.add(class7);
		radioPanel.add(class8);
		
		buttons[0]=class1;
		buttons[1]=class2;
		buttons[2]=class3;
		buttons[3]=class4;
		buttons[4]=class5;
		buttons[5]=class6;
		buttons[6]=class7;
		buttons[7]=class8;
		
		add(radioPanel, BorderLayout.LINE_START);
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		setButtons();
		}
		
		/** Listens to the radio buttons. */
		public void actionPerformed(ActionEvent e) {
			//System.out.println(e.getActionCommand());
			AddAssignmentWindow.cT = e.getActionCommand();
			Window.tmpTitle=e.getActionCommand();
		}
		
		public void setButtons(){
			for(int i=0; i < Window.courses.getSize(); i++){
				Course c = (Course) Window.courses.getElement(i);
				buttons[i].setText(c.getCourseTitle());
				buttons[i].setActionCommand(c.getCourseTitle());
				buttons[i].setVisible(true);
			}
		}
		
}
