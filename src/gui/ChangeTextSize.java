package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ChangeTextSize extends JMenuItem implements ActionListener{
	private ToDo todo;
	public ChangeTextSize(ToDo todo){
		super("Change text size");
		Font font = new Font("Verdana", Font.ITALIC, 18);
		super.setFont(font);
		this.todo = todo;
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = JOptionPane.showInputDialog("Enter text size");
		if (s != null){
		if(!s.matches("[0-9]+")){
			JOptionPane.showMessageDialog(null, "The input must be a number!","Obs..." , JOptionPane.ERROR_MESSAGE);
		}
		else 
		todo.setTextSize(Integer.parseInt(s));
	}
	}
}
