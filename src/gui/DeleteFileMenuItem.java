package gui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class DeleteFileMenuItem extends FileMenu {
	private ToDo todo;
	
	public DeleteFileMenuItem(ToDo todo) {
		super(todo, "Delete file");
		this.todo = todo;
	}

	@Override
	protected void action(File file) throws FileNotFoundException {
		try {
			file.createNewFile();
			file.delete();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong while deleting the file!", "Obs",
					JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	@Override
	protected int openDialog(JFileChooser fileChooser) {
		fileChooser.setApproveButtonText("Delete");
		return fileChooser.showOpenDialog(todo);
	}

}
