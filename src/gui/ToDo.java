package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ToDo {
	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	
	
	public ToDo(){
		frame = new JFrame("To do");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	frame.addWindowListener(new java.awt.event.WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
            	onExit();
        	}	
    	});
		frame.setLayout(new BorderLayout());
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/3-frame.getSize().width/3, dim.height/6-frame.getSize().height/6);
		
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		menu.add(file);
		menu.add(edit);
		file.add(new OpenMenuItem());
		file.add(new SaveMenuItem());
		file.add(new NewMenuItem());
		edit.add(new DeleteMenuItem());
		edit.add(new DeleteAllMenuItem());
		edit.add(new ChangeTextSize());
		
		JPanel panel = new JPanel();
		
		JButton New = new JButton("New");
		New.addActionListener(new NewListener());
		
		panel.add(New);
		
		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);
		panel.add(new JScrollPane(table));
		
		
		model = new DefaultTableModel(); 
		table = new JTable(model);
		model.addColumn("Col1");
		model.addRow(new Object[]{"v1"});
		
		
		frame.add(panel, BorderLayout.PAGE_END);
		frame.add(table);
		
		frame.setPreferredSize(new Dimension(500,500));
		frame.pack();
    	frame.setVisible(true);
	}
	
	private class NewListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String s = JOptionPane.showInputDialog("Enter your note");
			model.addRow(new Object[]{s});
			
		}
	}
	
	
	private void onExit() {
		System.exit(0);
		
		
	}
	
	public static void main(String[] args){
		ToDo toDo = new ToDo();
	}

}
