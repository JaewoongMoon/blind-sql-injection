package result;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import base.SwingUtils;

/**
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/04
 */
public class ResultPanel extends JPanel{

	private JTabbedPane tabs = null;
	private DBResultUI dbUI = null;
	private TableResultUI tableUI = null;
	private ColumnResultUI columnUI = null;
	private DataResultUI dataUI = null; 
	
	public ResultPanel(){
		// panel setup
		setLayout(new BorderLayout());
		setVisible(true);
		
		tabs = new JTabbedPane();
		add(tabs);
		
		dbUI = new DBResultUI();
		tableUI = new TableResultUI();
		columnUI = new ColumnResultUI();
		dataUI = new DataResultUI();
		
		tabs.add("DB", dbUI);
		tabs.add("Table", tableUI);
		tabs.add("Column", columnUI);
		tabs.add("Data", dataUI);
		
	}
	
	public void selectTab(int tabIndex){
		tabs.setSelectedIndex(tabIndex);
	}
	
	public DBResultUI getDBResultUI(){
		return dbUI;
	}
	
	public TableResultUI getTableResultUI(){
		return tableUI;
	}

	public void clearResults(){
		// clear db 
		dbUI.clearResult();
		// clear table 
		tableUI.clearResult();
		// clear column 
		// clear data
	}
	
	/* sub tabs */
	public class DBResultUI extends JPanel{
		String[] title = {"#", "name length", "DB name"};
		float[] columnWidthPercentage = {10.0f, 20.0f, 70.0f};
		Vector<String> headers = new Vector<String>(Arrays.asList(title));
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		JTable table;
		JScrollPane scroll;
		
		public DBResultUI(){
			setLayout(new BorderLayout());
			table = new JTable(data, headers);
			scroll = new JScrollPane(table);
			add(scroll);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					SwingUtils.resizeColumnWidthPercentage(table, columnWidthPercentage);
				}
			});
		}
		
		public void clearResult(){
			System.out.println("clear db result ...");
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
		}
		
		public DefaultTableModel getTableModel(){
			return (DefaultTableModel)table.getModel();
		}
		
		public String getColumnCount(String dbName){
			return null;
		}
		
		public boolean isExist(String dbName){
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
			Vector data = tableModel.getDataVector();
			for(int i=0; i < data.size(); i++){
				Vector item = (Vector)data.get(i);
				for(int j=0; j < item.size(); j++){
					if(dbName.equals(item.get(j))){
						return true;
					}
				}
			}
			return false;
		}
	}
	
	public class TableResultUI extends JPanel{
		String[] title = {"#", "DB", "name length", "table name"};
		float[] columnWidthPercentage = {5.0f, 30.0f, 20.0f, 45.0f};
		Vector<String> headers = new Vector<String>(Arrays.asList(title));
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		JTable table;
		JScrollPane scroll;
		
		public TableResultUI(){
			setLayout(new BorderLayout());
			table = new JTable(data, headers);
			scroll = new JScrollPane(table);
			add(scroll);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					SwingUtils.resizeColumnWidthPercentage(table, columnWidthPercentage);
				}
			});
		}
		
		public void clearResult(){
			System.out.println("clear table result ...");
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
		}
		
		public DefaultTableModel getTableModel(){
			return (DefaultTableModel)table.getModel();
		}
	}
	
	public class ColumnResultUI extends JPanel{
		String[] title = {"#", "DB", "table", "name length", "column name"};
		float[] columnWidthPercentage = {5.0f, 20.0f, 20.0f, 10.0f, 50.0f};
		Vector<String> headers = new Vector<String>(Arrays.asList(title));
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		JTable table;
		JScrollPane scroll;
		
		
		public ColumnResultUI(){
			setLayout(new BorderLayout());
			table = new JTable(data, headers);
			scroll = new JScrollPane(table);
			add(scroll);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					SwingUtils.resizeColumnWidthPercentage(table, columnWidthPercentage);
				}
			});
		}
		
		public DefaultTableModel getTableModel(){
			return (DefaultTableModel)table.getModel();
		}
	}

	public class DataResultUI extends JPanel{
		String[] title = {"#", "DB", "table", "column", "data length", "data"};
		Vector<String> headers = new Vector<String>(Arrays.asList(title));
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		JTable table;
		JScrollPane scroll;
		
		public DataResultUI(){
			setLayout(new BorderLayout());
			table = new JTable(data, headers);
			scroll = new JScrollPane(table);
			add(scroll);
		}
		
		public DefaultTableModel getTableModel(){
			return (DefaultTableModel)table.getModel();
		}
	}

}
