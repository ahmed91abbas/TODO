package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;

public class LastOpened extends JMenuItem{
	private ToDo todo;
	private static String[] a = new String[10];
	private JComboBox<String> c;
	private Preferences prefs;
	 String ID1 = "Test1";

	public LastOpened(ToDo toDo) {
		super("Last Modified");
		this.todo = todo;
		
		 prefs = Preferences.userRoot().node(this.getClass().getName());
		   
		
		c = new JComboBox<String>();
		super.add(c);
		
//		c.insertItemAt("dsa", 0);
//		c.insertItemAt("dewqewqea", 0);
//		c.insertItemAt("last", 0);
		
	}

	public void add(String s) {
		prefs.put(ID1, s);
		c.insertItemAt(prefs.get(ID1, s),0);
		
		
	}


}
