package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ChangeTextSize extends JMenuItem implements ActionListener{
	private ToDo todo;
	public ChangeTextSize(ToDo todo){
		super("Change text size");
		this.todo = todo;
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = JOptionPane.showInputDialog("Enter text size");
		todo.setTextSize(Integer.parseInt(s));
	}

}
