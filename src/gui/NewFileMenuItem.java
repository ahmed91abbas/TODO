package gui;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class NewFileMenuItem extends FileMenu {
	private ToDo todo;
	private LastOpened lastOpened;

	public NewFileMenuItem(ToDo todo, LastOpened lastOpened) {
		super(todo, "New list");
		this.todo = todo;
		this.lastOpened = lastOpened;
	}

	@Override
	protected void action(File file) throws FileNotFoundException {
		todo.saveToFile(todo.getCurrentFile());
		todo.removeAll();
		String path = file.getAbsolutePath();
		if (!path.substring(path.length() - 4).equalsIgnoreCase(".txt")) {
			file = new File(path + ".txt");
		}
		todo.setCurrentFile(file);
		todo.saveToFile(file);
		lastOpened.add(file.getAbsolutePath());
	}

	@Override
	protected int openDialog(JFileChooser fileChooser) {
		fileChooser.setApproveButtonText("Create");
		return fileChooser.showOpenDialog(todo);
	}

}
