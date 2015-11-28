package gui;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

public class OpenMenuItem extends FileMenu {
	private ToDo todo;

	public OpenMenuItem(ToDo todo) {
		super(todo, "Open");
		this.todo = todo;
	}

	@Override
	protected void action(File file) throws FileNotFoundException {
		todo.loadFromFile(file);
	}

	protected int openDialog(JFileChooser fileChooser) {
		return fileChooser.showOpenDialog(todo);
	}
}
