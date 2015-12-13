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
import java.util.ArrayList;
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
	private ArrayList<String> db;
	private JFrame frame;
	private DefaultTableModel model;
	private File file;
	private int textSize = 25;
	private LastOpened lastOpened;
	private ToDoPrintStream print;

	public ToDo() {
		db = new ArrayList<String>();
		file = new File("ToDo");
		frame = new JFrame(file.getName());
		model = new DefaultTableModel();
		lastOpened = new LastOpened(this);

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
		file.add(new SaveAsMenuItem(this, lastOpened));
		file.add(new NewFileMenuItem(this, lastOpened));
		file.add(new DeleteFileMenuItem(this));// TODO fix change so it happens with no filechooser
		file.add(new RenameMenuItem(this));
		edit.add(new DeleteMenuItem(this));
		edit.add(new DeleteAllMenuItem(this));
		edit.add(new MarkAsUndone());
		edit.add(new ChangeTextSize(this));
		edit.add(new ClearLastOpened());

		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();

		JScrollPane sp = new JScrollPane(this,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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

		setModel(model);
		setFont(new Font("Serif", Font.PLAIN, textSize));
		model.addColumn(file.getName());
		setTableHeader(null);

		getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				fixTableHightAndWidth();
			}
		});

		frame.add(panel2, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.PAGE_END);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.pack();
		frame.setVisible(true);

		lastOpened.loadLastOpenedFiles(); //TODO still creating a new file "ToDo.txt"
	}

	private class NewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setRowHeight(textSize);
			String s = JOptionPane.showInputDialog("Enter your note");
			if (s != null) {
				model.addRow(new Object[] { s });
				int lastRow = convertRowIndexToView(model.getRowCount() - 1);
				db.add(lastRow, s);
				fixTableHightAndWidth();
			}
		}
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
		setFont(new Font("Serif", Font.PLAIN, textSize));
		fixTableHightAndWidth();
	}

	private void fixTableHightAndWidth() {
			int width = frame.getWidth() - 21;
		for (int row = 0; row < getRowCount(); row++) {
			int rowHeight = getRowHeight();
			Component comp = prepareRenderer(getCellRenderer(row, 0), row, 0);
			rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			setRowHeight(row, rowHeight);
			width = Math.max(width, comp.getPreferredSize().width);
		}
		getColumnModel().getColumn(0).setPreferredWidth(width + 2);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	private void updateDatabase() {
		for (int row = 0; row < model.getRowCount(); row++) {
			String s = (String) model.getValueAt(row, 0);
			if (isDone(row)) {
				db.set(row, s + "#done");
			} else {
				db.set(row, s);
			}
		}
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		try{
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
		return c;}
		catch(Exception e){
			return null;
		}
	}

	private boolean isDone(int row) {
		String s = "";
		try {
			s = db.get(row);
			if (s.length() >= 5
					&& s.substring(s.length() - 5).equalsIgnoreCase("#done")) {
				return true;
			}
		} 
		catch (Exception e) {
			return false;
		}
		return false;
	}

	private class doneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int row = getSelectedRow();
			if (row != -1) {
				String s = db.get(row);
				db.set(row, s + "#done");
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
				db.set(row, s);
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

	private class saveAndClose implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!file.exists()) {
				String message = "Do you want to sava this file as: "
						+ file.getAbsolutePath() + "?";

				final String objButtons[] = { "Yes", "No", "Cancel" };
				int option = JOptionPane.showOptionDialog(null, message,
						"Obs...", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, objButtons,
						objButtons[2]);
				if (option == 0) {
					try {
						saveToFile(file);
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),
								"Obs...", JOptionPane.ERROR_MESSAGE);
					}
				}
				if (option == 2) {
					return;
				}
			} else {
				try {
					saveToFile(file);
				} catch (FileNotFoundException e1) {
					 JOptionPane.showMessageDialog(null, e1.getMessage(),
					 "Obs...", JOptionPane.ERROR_MESSAGE);
					 return;
				}
			}
			System.exit(0);
		}
	}

	public void removeAll() {
		int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		db.clear();
	}

	public void remove() { 
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
		fixTableHightAndWidth();
	}

	private void onExit() {
		String message = "Save changes before exit?";

		final String objButtons[] = { "Yes", "No", "Cancel" };
		int option = JOptionPane.showOptionDialog(null, message, "Obs...",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				objButtons, objButtons[2]);
		if (option == 0) {
			try {
				saveToFile(file);
			} catch (FileNotFoundException e1) {
				 JOptionPane.showMessageDialog(null, e1.getMessage(),
				 "Obs...", JOptionPane.ERROR_MESSAGE);
				 return;
			}

		}
		if (option == 2) {
			return;
		}
		System.exit(0);
	}

	public void rename(String newName, boolean renameFile, boolean renameFrame)
			throws IOException {
		if (newName.trim().equals(".txt") || newName.isEmpty()
				|| newName.trim().equals("")) {
			throw new java.io.IOException("Illegal file name. Renaming failed!");
		}
		if (renameFile) {
			File newFile = new File(newName);
			if (newFile.exists())
				throw new java.io.IOException(
						"A file with that name already exists");
			file.renameTo(newFile);
			lastOpened.add(newFile.getAbsolutePath());
		}
		if (renameFrame) {
			frame.setTitle(newName);
		}
	}

	public void load(ArrayList<String> loadList) {
		db.clear();
		removeAll();
		db = new ArrayList<String>(loadList);
		for (int i = 0; i < db.size(); i++) {
			String s = db.get(i);
			if (s.length() > 5
					&& s.substring(s.length() - 5).equalsIgnoreCase("#done")) {
				s = s.substring(0, s.length() - 5);
			}
			model.addRow(new Object[] { s });
		}
		fixTableHightAndWidth();
	}

	public void loadFromFile(File file) throws FileNotFoundException {
		ToDoBufferedReader read = new ToDoBufferedReader(file.toString(), this);
		try {
			this.file = file;
			read.load();
			read.close();
			lastOpened.add(file.getAbsolutePath());
		} catch (Exception e) {
			db.clear();
		}
	}

	public void saveToFile(File file) throws FileNotFoundException {
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
				print = new ToDoPrintStream(file.toString());
			} else {

				print = new ToDoPrintStream(file.toString() + ".txt");
			}
			updateDatabase();
			print.save(db);
			print.close();
			lastOpened.addLastOpenedFiles();
		}
	}

	public File getCurrentFile() {
		return file;
	}

	public void setCurrentFile(File file) {
		this.file = file;
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ToDo toDo = new ToDo();
	}
}
