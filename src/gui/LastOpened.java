package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;

public class LastOpened extends JMenuItem implements ActionListener{
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
		c.addActionListener(this);
		super.add(c);
		
//		c.insertItemAt("dsa", 0);
//		c.insertItemAt("dewqewqea", 0);
//		c.insertItemAt("last", 0);
//		prefs.put(ID1, "dsad");
//		prefs.put(ID1, "test");
		
		
		
	}

	public void add(String s) {

		c.insertItemAt(s,0);

	}
	public void loadLastOpenedFiles(){
		//TODO put elements from prefs into comboBox on startup
	}
	
	public void addLastOpenedFiles(){ //TODO put elements from comboBox into prefs when closing
		 ComboBoxModel<String> model = c.getModel();
	     int size = model.getSize();
	     for(int i=0;i<size;i++) {
	         Object element = model.getElementAt(i);
	        prefs.put(ID1, (String) element);
	     }
	}

	 public void actionPerformed(ActionEvent e) { //TODO open the selected file by using the path and save the current file
//		 JComboBox cb = (JComboBox)e.getSource();
//	        String petName = (String)cb.getSelectedItem();
//	        updateLabel(petName);
 }
}
