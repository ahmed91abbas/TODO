package gui;


import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class SaveAsMenuItem extends FileMenu{
	private ToDo todo;
	private LastOpened lastOpened;
	
	public SaveAsMenuItem(ToDo todo, LastOpened lastOpened){
		super(todo,"Save as");
		this.todo = todo;
		this.lastOpened = lastOpened;
	}
	
	protected void action(File file) throws FileNotFoundException {
		todo.saveToFile(todo.getCurrentFile());
		String path = file.getAbsolutePath();
		if(!path.substring(path.length()-5).equalsIgnoreCase(".txt")){
			file = new File(path+".txt");
		}
		todo.saveToFile(file);
		todo.setCurrentFile(file);
		lastOpened.add(file.getAbsolutePath());
	}

	protected int openDialog(JFileChooser fileChooser) {
		return fileChooser.showSaveDialog(todo);
	}

	
}