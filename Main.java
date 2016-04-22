package tmp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame
{
	public Main()
	{
		JPanel panel = new JPanel();
		//creates a cancel button
				JButton launch = new JButton("Launch");
				launch.addActionListener(new ActionListener() {
					//Action listener should close window
					public void actionPerformed(ActionEvent e) {
						NewTabbedCalendar ntc = new NewTabbedCalendar();
					}
				});
				panel.add(launch);
				
		this.add(panel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		Main m = new Main();
	}
}
