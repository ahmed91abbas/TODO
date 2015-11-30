package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class RenameMenuItem extends JMenuItem implements ActionListener {
	private ToDo todo;

	public RenameMenuItem(ToDo todo) {
		super("Rename file");
		this.todo = todo;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String s = JOptionPane.showInputDialog("Enter file name");
		if (s == null || s.substring(0, 0) == "") {
			return;
		}
		todo.rename(s);
	}

}
