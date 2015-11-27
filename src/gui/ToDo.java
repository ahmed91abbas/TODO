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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class ToDo{
	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private String fileName = "To do";

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
		menu.add(lastMod); //TODO implement
		file.add(new OpenMenuItem());
		file.add(new SaveAsMenuItem());
		file.add(new NewMenuItem());
		edit.add(new DeleteMenuItem(this));
		edit.add(new DeleteAllMenuItem(this));
		edit.add(new ChangeTextSize());
		
		JPanel panel = new JPanel();

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
		table = new JTable(model);
		table.setFont(new Font("Serif", Font.PLAIN, 25));
		model.addColumn("Col1");

		frame.add(panel, BorderLayout.PAGE_END);
		frame.add(table);

		frame.setPreferredSize(new Dimension(500, 500));
		frame.pack();
		frame.setVisible(true);
	}

	private class NewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String s = JOptionPane.showInputDialog("Enter your note");
			if(s != null){
			model.addRow(new Object[] { s });
			fixRowHight();
			}
			// TODO add mouselistner to each row
		}
	}

	private void fixRowHight() {
		for (int row = 0; row < table.getRowCount(); row++) {
			int rowHeight = table.getRowHeight();
			Component comp = table.prepareRenderer(
					table.getCellRenderer(row, 0), row, 0);
			rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			table.setRowHeight(row, rowHeight);
		}
	}

	private class doneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int row = table.getSelectedRow();
			if(row != -1)
			setRowColour(Color.GREEN, row);
			model.fireTableRowsUpdated(row, row);
		}
	}

	private void setRowColour(Color color, int pos) {
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				final Component c = super.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, column);
				System.out.println(row);
				if(row == pos){
					c.setBackground(color);
				} else 
					if(!c.getBackground().equals(color)){
					c.setBackground(Color.WHITE);
				}
					return c;
				}
		});
//			 public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
//		     {
//		          Component c = super.prepareRenderer(renderer, row, column);
//
//		          if (!isRowSelected(row))
//		          {
//		               Color color = rowColor.get( row );
//		               c.setBackground(color == null ? getBackground() : color);
//		          }
//
//		          return c;
//		     
	}

	public void removeAll() {
		int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	public void remove() {
		try{
		model.removeRow(table.getSelectedRow());
		} catch(ArrayIndexOutOfBoundsException e){
		return;
		}
	}

	private void onExit() {
		//TODO save file
		System.exit(0);

	}

	public static void main(String[] args) {
		ToDo toDo = new ToDo();
	}

}
