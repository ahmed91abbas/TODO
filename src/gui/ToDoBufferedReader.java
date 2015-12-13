package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ToDoBufferedReader extends BufferedReader {
	private ToDo todo;
	private File file;

	public ToDoBufferedReader(String name, ToDo todo)
			throws FileNotFoundException {
		super(new FileReader(name));
		this.todo = todo;
		file = new File(name);
	}

	public void load() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			int i = 0;
			while (ready()) {
				String string = readLine();
				list.add(i, string);
				i++;
			}
			todo.load(list);
			todo.rename(file.getName(), false, true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Obs...",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
