package gui;


import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class DeleteFileMenuItem extends FileMenu {
	private ToDo todo;
	
	public DeleteFileMenuItem(ToDo todo) {
		super(todo, "Delete file");
		this.todo = todo;
	}

	@Override
	protected void action(File file) throws FileNotFoundException {
		file.delete();
	}

	@Override
	protected int openDialog(JFileChooser fileChooser) {
		fileChooser.setApproveButtonText("Delete");
		return fileChooser.showOpenDialog(todo);
	}

}
