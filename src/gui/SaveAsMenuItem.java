package gui;


import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;




public class SaveAsMenuItem extends FileMenu{
	private ToDo todo;
	public SaveAsMenuItem(ToDo todo){
		super(todo,"Save as");
		this.todo = todo;
	}
	
	protected void action(File file) throws FileNotFoundException {
		todo.saveToFile(file);
	}

	protected int openDialog(JFileChooser fileChooser) {
		return fileChooser.showSaveDialog(todo);
	}

	
}