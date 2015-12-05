package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class LastOpened extends JMenuItem implements ActionListener {
	private ToDo todo;
	private JComboBox<String> c;
	private Preferences prefs;
	final static String ID0 = "0";
	final static String ID1 = "1";
	final static String ID2 = "2";
	final static String ID3 = "3";
	final static String ID4 = "4";
	final static String ID5 = "5";
	final static String ID6 = "6";
	final static String ID7 = "7";
	final static String ID8 = "8";
	final static String ID9 = "9";

	public LastOpened(ToDo todo) {
		this.todo = todo;

		prefs = Preferences.userRoot().node(this.getClass().getName());

		c = new JComboBox<String>();
		c.addActionListener(this);
		add(c);

	}

	public void clear() {
		try {
			prefs.clear();
			c.removeAllItems();
		} catch (BackingStoreException e) {
			return;
		}
	}

	public void add(String s) {
		c.insertItemAt(s, 0);
	}

	public void loadLastOpenedFiles() {
		String id;
		for (int i = 0; i < 10; i++) {
			id = "ID" + Integer.toString(i);
			String s = prefs.get(id, "");
			if (s != null && !s.equals(""))
				add(s);
		}

	}

	public void openTheLastOpenedFile() {//TODO fix
		String filePath = prefs.get(ID0, "");
		System.out.println(filePath);
		if (filePath != null && !filePath.equals("")) {
			try {
				readFile(filePath);
			} catch (IOException e) {
				return;
			}
		}
	}

	public void addLastOpenedFiles() {
		ComboBoxModel<String> model = c.getModel();
		int size = model.getSize();
		String id;
		for (int i = 0; i < size; i++) {
			id = "ID" + Integer.toString(i);
			Object element = model.getElementAt(i);
			prefs.put(id, (String) element);
		}
	}

	private void readFile(String filePath) throws IOException {
		ToDoBufferedReader read = new ToDoBufferedReader(filePath, todo);
		read.load();
		read.close();
		throw new IOException("Couldn't open the file!");
	}

	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) e.getSource();
		String filePath = (String) cb.getSelectedItem();

		try {
			todo.save();
			readFile(filePath);
		} catch (Exception e1) {
			return;
		}
	}
}
