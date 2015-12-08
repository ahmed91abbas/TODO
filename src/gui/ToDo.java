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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ToDo extends JTable {
	Map<Integer, String> db = new HashMap<Integer, String>();
	private JFrame frame;
	private DefaultTableModel model;
	private String fileName = "To do"; // TODO making problem cause it's not
										// giving the path
	private int textSize = 25;
	private LastOpened lastOpened;
	private ToDoPrintStream print;

	public ToDo() {
		lastOpened = new LastOpened(this);
		lastOpened.loadLastOpenedFiles();

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
		JMenu help = new JMenu("Help");
		menu.add(file);
		menu.add(edit);
		menu.add(help);// TODO add settings with shourtcut keys and about
		menu.add(lastOpened);
		file.add(new OpenMenuItem(this));
		file.add(new SaveAsMenuItem(this));
		file.add(new NewFileMenuItem(this));// TODO Opens a new jchoosefile and
		// creates a new file be4 opening
		file.add(new DeleteFileMenuItem(this));
		file.add(new RenameMenuItem(this));// TODO fix
		edit.add(new DeleteMenuItem(this));
		edit.add(new DeleteAllMenuItem(this));
		edit.add(new MarkAsUndone());
		edit.add(new ChangeTextSize(this));
		edit.add(new ClearLastOpened());

		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		// TODO fix scrolling and fit
		// JScrollPane js = new JScrollPane(this);
		// text to wedith
		// js.setVisible(true);
		// frame.add(js);

		JScrollPane sp = new JScrollPane(this,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// frame.getContentPane().add(sp);
		panel2.add(sp);

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
		GridLayout grid2 = new GridLayout(1, 1);

		panel.setLayout(grid);
		panel2.setLayout(grid2);

		model = new DefaultTableModel();
		setModel(model);
		setFont(new Font("Serif", Font.PLAIN, textSize));
		model.addColumn(fileName);
		setTableHeader(null);

		getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				fixRowHight();
			}
		});

//		setPreferredScrollableViewportSize(new Dimension(300, 200));
//        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        TableColumnAdjuster tca = new TableColumnAdjuster(this);
//        tca.adjustColumns();
		
//		getColumnModel().getColumn(0).setPreferredWidth(600);
//		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		frame.add(panel2, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.PAGE_END);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.pack();
		frame.setVisible(true);

		lastOpened.openTheLastOpenedFile();
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

	private void updateDatabase() {
		for (int row = 0; row < model.getRowCount(); row++) {
			String s = (String) model.getValueAt(row, 0);
			if (isDone(row)) {
				db.put(row, s + "#done");
			} else {
				db.put(row, s);
			}
		}
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		Component c = super.prepareRenderer(renderer, row, column);

		if (!isRowSelected(row)) {
			Color color;
			if (isDone(row)) {
				color = Color.GREEN;
			} else {
				color = Color.WHITE;
			}
			c.setBackground(color == null ? getBackground() : color);
		}

		return c;
	}

	private boolean isDone(int row) {
		String s = "";
		try {
			s = db.get(row);
			if (s.length() > 5
					&& s.substring(s.length() - 5).equalsIgnoreCase("#done")) {
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	private class doneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int row = getSelectedRow();
			if (row != -1) {
				String s = db.get(row);
				db.put(row, s + "#done");
			}
			model.fireTableRowsUpdated(row, row);
		}
	}

	private class MarkAsUndone extends JMenuItem implements ActionListener {
		public MarkAsUndone() {
			super("Mark as undone");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int row = getSelectedRow();
			if (isDone(row)) {
				String s = db.get(row);
				s = s.substring(0, s.length() - 6);
				db.put(row, s);
			}
		}

	}

	private class ClearLastOpened extends JMenuItem implements ActionListener {
		public ClearLastOpened() {
			super("Clear last opened list");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			lastOpened.clear();
		}

	}

	// TODO creating a new file instead of saving to the original when the file
	// is not placed on the defulet directory
	private class saveAndClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			File file = new File(fileName);
			if (!file.exists()) {

				System.out.println(fileName);
				// TODO saveas
				try {
					createNewFile(file);
					saveToFile();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),
							"Obs...", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				save();
			}
			System.exit(0);
		}
	}

	public void save() {
		updateDatabase();
		ToDoPrintStream print;
		try {
			print = new ToDoPrintStream(fileName);
			print.save(db.entrySet());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		;
		lastOpened.addLastOpenedFiles();
	}

	public void removeAll() {

		int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
			db.remove(i);
		}

	}

	public void remove() { // TODO fix color adjustment
		try {
			int row = getSelectedRow();
			model.removeRow(row);
			db.remove(row);
			updateDatabase();
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		} catch (NullPointerException e) {
			return;
		}
	}

	private void onExit() {
		String message = "Save changes before exit?";

		final String objButtons[] = { "Yes", "No", "Cancel" };
		int option = JOptionPane.showOptionDialog(null, message, "Obs...",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				objButtons, objButtons[2]);
		if (option == 0) {
			save();
			System.exit(0);
		}
		if (option == 1) {
			System.exit(0);
		}
		return;
	}

	public void rename(String newName, boolean renameFile, boolean renameFrame)
			throws IOException { // TODO add .txt to newFile only when renamed
									// file is being saved
		if (renameFile) {
			File oldFile = new File(fileName);
			File newFile = new File(newName);
			if (newFile.exists())
				throw new java.io.IOException("file exists");
			oldFile.renameTo(newFile);
			lastOpened.add(newFile.getAbsolutePath());
		}
		if (renameFrame) {
			fileName = newName;
			frame.setTitle(fileName);
		}
	}

	public void load(Map<Integer, String> loadmap) {
		db.clear();
		removeAll();
		db.putAll(loadmap);
		for (int i = 0; i < db.size(); i++) {
			String s = db.get(i);
			if (s.length() > 6
					&& s.substring(s.length() - 5).equalsIgnoreCase("#done")) {
				s = s.substring(0, s.length() - 5);

			}
			model.addRow(new Object[] { s });
		}
		fixRowHight();
	}

	public void loadFromFile(File file) throws FileNotFoundException {
		ToDoBufferedReader read = new ToDoBufferedReader(file.toString(), this);
		try {
			lastOpened.add(file.toString());
			read.load();
			read.close();
		} catch (Exception e) {
			db.clear();
		}
	}

	public void saveToFile() {
		updateDatabase();
		print.save(db.entrySet());
		print.close();

	}

	public void createNewFile(File file) throws FileNotFoundException {
		if (file != null) {
			String filename = file.getName();
			try {
				rename(filename, false, true);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Obs...",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (filename.length() > 3
					&& filename.substring(filename.length() - 4)
							.equalsIgnoreCase(".txt")) {
				if (filename.equals(".txt")
						|| (Character.toString(filename.charAt(0)).equals(" "))) {
					throw new FileNotFoundException(
							" Illegal file name. Save aborted.");
				}
				print = new ToDoPrintStream(file.toString());
			} else {

				print = new ToDoPrintStream(file.toString() + ".txt");
			}
		}
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ToDo toDo = new ToDo();
	}

}
