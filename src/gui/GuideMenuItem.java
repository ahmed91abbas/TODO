package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

	public class GuideMenuItem extends JMenuItem implements ActionListener{

		public GuideMenuItem(){
			super("User guide");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
