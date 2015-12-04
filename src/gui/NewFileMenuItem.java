package gui;

import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;


@SuppressWarnings("serial")
public class NewFileMenuItem extends FileMenu{
	private ToDo todo;
	
	public NewFileMenuItem(ToDo todo){
		super(todo,"New list");
		this.todo = todo;
	}

	@Override
	protected void action(File file) throws FileNotFoundException {
		todo.save();
		todo.removeAll();
		todo.createNewFile(file);
	}

	@Override
	protected int openDialog(JFileChooser fileChooser) {
		fileChooser.setApproveButtonText("Create");
		return fileChooser.showOpenDialog(todo);
	}
	

}
