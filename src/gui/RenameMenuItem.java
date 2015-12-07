package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
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
	public void actionPerformed(ActionEvent arg0) { //TODO dublicated code
		String fileName = JOptionPane.showInputDialog("Enter file name");
//		if (fileName == null || fileName.substring(0, 0) == "") {
//			return;
//		}
		if (fileName != null){
		try {
		
		if (fileName.length() > 3
				&& fileName.substring(fileName.length() - 4)
						.equalsIgnoreCase(".txt")) {
			if (fileName.equals(".txt")
					|| (Character.toString(fileName.charAt(0)).equals(" "))) {
				throw new FileNotFoundException(
						" Illegal file name. Renaming failed!");
			}
			todo.rename(fileName,true,true);
		} else {
			todo.rename(fileName + ".txt",true,true);
		}
		
//		try {
//			todo.rename(fileName,true,true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Obs...", JOptionPane.ERROR_MESSAGE);
		}
		}
	}

}
