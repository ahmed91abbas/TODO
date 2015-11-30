package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public abstract class FileMenu extends JMenuItem implements ActionListener {
	protected ToDo todo;
	protected String fileName;

	protected FileMenu(ToDo todo, String title) {
		super(title);
		this.todo = todo;
		addActionListener(this);

	}

	protected abstract void action(File file) throws FileNotFoundException;

	public void actionPerformed(ActionEvent event) {
		JFileChooser fileChooser = new JFileChooser(".");
		FileFilter filter = new FileNameExtensionFilter("TXT files", "txt");
		fileChooser.setFileFilter(filter);
		int option = openDialog(fileChooser);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				action(file);
				todo.rename(file.getName());
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Obs",
						JOptionPane.INFORMATION_MESSAGE);

			}
			fileName = file.getName();
		}

	}

	protected abstract int openDialog(JFileChooser fileChooser);
}
