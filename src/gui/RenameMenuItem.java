package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
		String fileName = JOptionPane.showInputDialog("Enter file name");
		if (fileName == null || fileName.substring(0, 0) == "") {
			return;
		}
		try {
			todo.rename(fileName,true,true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Obs...", JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
