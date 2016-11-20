package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class RenameMenuItem extends JMenuItem implements ActionListener {
	private ToDo todo;

	public RenameMenuItem(ToDo todo) {
		super("Rename file");
		Font font = new Font("Verdana", Font.ITALIC, 18);
		super.setFont(font);
		this.todo = todo;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String fileName = JOptionPane.showInputDialog("Enter file name");
		if (fileName != null) {
			try {
				if (fileName.length() > 3
						&& fileName.substring(fileName.length() - 4)
								.equalsIgnoreCase(".txt")) {

					todo.rename(fileName, true, true);
				} else {
					todo.rename(fileName + ".txt", true, true);
				}
			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, e.getMessage(), "Obs...",
//						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
}
