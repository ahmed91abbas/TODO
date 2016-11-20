package gui;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class DeleteFileMenuItem extends JMenuItem implements ActionListener{
	private File file;
	private ToDo todo; 
	
	public DeleteFileMenuItem(ToDo todo) {
		super("Delete file");
		Font font = new Font("Verdana", Font.ITALIC, 18);
		super.setFont(font);
		this.todo = todo;
		addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		file = todo.getCurrentFile();
		String message = "This will delete "+file.getName()+" entirely! Do you want to continue?";
	   	 
		 final String objButtons[] = { "Yes", "Cancel"};
   	int option = JOptionPane.showOptionDialog(null,
           	message, "Obs...",
           	JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
           	objButtons, objButtons[1]);
   	if(option == 0){
		try {
			file.createNewFile();
			file.delete();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Something went wrong while deleting the file!", "Obs",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
   	}
   	return;
	}
}
