package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class DeleteAllMenuItem extends JMenuItem implements ActionListener{
	private ToDo todo;
	public DeleteAllMenuItem(ToDo todo){
		super("Delete all");
		Font font = new Font("Verdana", Font.ITALIC, 18);
		super.setFont(font);
		this.todo = todo;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = "This will delete all entries! Contunie?";
   	 
		 final String objButtons[] = { "Yes", "Cancel"};
    	int option = JOptionPane.showOptionDialog(null,
            	message, "Obs...",
            	JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
            	objButtons, objButtons[1]);
    	if(option == 0){
    		todo.removeAll();
    		return;
    	}
    	return;
	}

}
