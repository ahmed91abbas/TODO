package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ToDo extends JTable{
	Map<Integer, Color> rowColor = new HashMap<Integer, Color>();
	Map<Integer, String> db = new HashMap<Integer, String>();
	private JFrame frame;
	private DefaultTableModel model;
	private String fileName = "To do";
	private int textSize = 25;

	public ToDo() {
		frame = new JFrame(fileName);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onExit();
			}
		});
		frame.setLayout(new BorderLayout());

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 3 - frame.getSize().width / 3, dim.height
				/ 6 - frame.getSize().height / 6);

		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu lastMod = new JMenu("Last modified");
		menu.add(file);
		menu.add(edit);
		menu.add(lastMod); // TODO implement
		file.add(new OpenMenuItem(this));
		file.add(new SaveAsMenuItem(this));
		file.add(new NewMenuItem());
		edit.add(new DeleteMenuItem(this));
		edit.add(new DeleteAllMenuItem(this));
		edit.add(new ChangeTextSize(this));

		JPanel panel = new JPanel();
		
		JScrollPane js=new JScrollPane(this);
		js.setVisible(true);
		frame.add(js);

		JButton New = new JButton("New");
		New.addActionListener(new NewListener());
		panel.add(New);
		JButton delete = new JButton("Delete");
		delete.addActionListener(new DeleteMenuItem(this));
		panel.add(delete);
		JButton saveAndClose = new JButton("Save and close");
		saveAndClose.addActionListener(new saveAndClose());
		panel.add(saveAndClose);
		JButton done = new JButton("Mark as done");
		done.addActionListener(new doneListener());
		panel.add(done);

		GridLayout grid = new GridLayout(2, 1);
		panel.setLayout(grid);

		model = new DefaultTableModel();
		setModel(model);
		setFont(new Font("Serif", Font.PLAIN, textSize));
		model.addColumn("Col1");

		getModel().addTableModelListener(new TableModelListener() {

		      public void tableChanged(TableModelEvent e) {
		         fixRowHight();
		      }
		    });
		
		
		frame.add(panel, BorderLayout.PAGE_END);
		frame.add(this);

		frame.setPreferredSize(new Dimension(500, 500));
		frame.pack();
		frame.setVisible(true);
	}

	private class NewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setRowHeight(textSize);
			String s = JOptionPane.showInputDialog("Enter your note");
			if (s != null) {
				model.addRow(new Object[] { s });
				int lastRow = convertRowIndexToView(model.getRowCount() - 1);
				db.put(lastRow, s);
				fixRowHight();
			}
		}
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
		setFont(new Font("Serif", Font.PLAIN, textSize));
		fixRowHight();
	}

	private void fixRowHight() {
		for (int row = 0; row < getRowCount(); row++) {
			int rowHeight = getRowHeight();
			Component comp = prepareRenderer(getCellRenderer(row, 0), row, 0);
			rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			setRowHeight(row, rowHeight);
		}
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		Component c = super.prepareRenderer(renderer, row, column);

		if (!isRowSelected(row)) {
			Color color = rowColor.get(row);
			c.setBackground(color == null ? getBackground() : color);
		}

		return c;
	}

	public void setRowColor(int row, Color color) {
		rowColor.put(row, color);
	}

	private class doneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int row = getSelectedRow();
			if (row != -1)
				setRowColor(row, Color.GREEN);
			model.fireTableRowsUpdated(row, row);//TODO not working probably
		}
	}

	public void removeAll() {
		int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
			db.remove(i);
		}
	}

	public void remove() {
		try {
			int row = getSelectedRow();
			setRowColor(row, Color.WHITE);	
			rowColor.remove(row);
			db.remove(row);
			model.removeRow(row);
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		} catch ( NullPointerException e) {
			return;
		}
	}

	private void onExit() {
		// TODO save file
		System.exit(0);

	}

	

	public void rename(String name) {
		fileName = name;
		frame.setTitle(fileName);
		
	}
	
	public void load(Map<Integer,String> loadmap) {
		db.clear();
		removeAll();
		db.putAll(loadmap);
		for (int i = 0; i < db.size(); i++){
			String s = db.get(i);
			model.addRow(new Object[] { s });
		}
		fixRowHight();
	}

	public void loadFromFile(File file) throws FileNotFoundException {
		ToDoBufferedReader read = new ToDoBufferedReader(file.toString(), this);
		try {
			read.load();
			read.close();
		} catch (Exception e) {
			db.clear();
		}
	}

	public void saveToFile(File file) throws FileNotFoundException {
		if (file != null) {
			String filename = file.getName();
			ToDoPrintStream print;

			if (filename.length() > 3 && filename.substring(filename.length() - 4).equalsIgnoreCase(".txt")) {
				if (filename.equals(".txt") || (Character.toString(filename.charAt(0)).equals(" "))) {
					throw new FileNotFoundException(" Illegal file name. Save aborted.");
				}

				print = new ToDoPrintStream(file.toString());
			} else {

				print = new ToDoPrintStream(file.toString() + ".txt");
			}
			print.save(db.entrySet());
			print.close();
		}
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ToDo toDo = new ToDo();
	}
}
