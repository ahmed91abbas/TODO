package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class DeleteMenuItem extends JMenuItem implements ActionListener{
	private ToDo todo;
public DeleteMenuItem(ToDo todo){
	super("Delete");
	this.todo = todo;
}
	@Override
	public void actionPerformed(ActionEvent e) {
		todo.remove();
	}

}
