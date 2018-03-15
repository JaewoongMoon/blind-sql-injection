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
import util.SwingUtils;

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
		String[] title = {"#", "DB name length", "DB", "table count"};
		float[] columnWidthPercentage = {5.0f, 20.0f, 55.0f, 20.0f};
		Vector<String> headers = new Vector<String>(Arrays.asList(title));
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		JTable table;
		JScrollPane scroll;
		
		
		private void testRowDo(){
			Vector<String> testRow = new Vector<String>();
			
			testRow.add("1");
			testRow.add("15");
			testRow.add("APPSCAN_PROJECT");
			testRow.add("1");
			
			Vector<String> testRow2 = new Vector<String>();
			testRow2.add("2");
			testRow2.add("6");
			testRow2.add("PHPWEB");
			testRow2.add("1");
			Vector<String> testRow3 = new Vector<String>();
			testRow3.add("3"); 
			testRow3.add("10");
			testRow3.add("SPRINGBOOK");
			testRow3.add("2");
			Vector<String> testRow4 = new Vector<String>();
			testRow4.add("4");
			testRow4.add("9");
			testRow4.add("STONESOUP");
			testRow4.add("2");
			
			Vector<String> testRow5 = new Vector<String>();
			testRow5.add("5");
			testRow5.add("3");
			testRow5.add("SYS");
			testRow5.add("0");
			
			Vector<String> testRow6 = new Vector<String>();
			testRow6.add("6");
			testRow6.add("6");
			testRow6.add("TASKDB");
			testRow6.add("2");
			Vector<String> testRow7 = new Vector<String>();
			testRow7.add("7");
			testRow7.add("13");
			testRow7.add("WEB_DIAGNOSIS");
			testRow7.add("18");
			
			data.add(testRow);
			data.add(testRow2);
			data.add(testRow3);
			data.add(testRow4);
			data.add(testRow5);
			data.add(testRow6);
			data.add(testRow7);

			
		}
		public DBResultUI(){
			//testRowDo();
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
		String[] title = {"#", "DB", "table name length", "table", "columns count"};
		float[] columnWidthPercentage = {5.0f, 20.0f, 20.0f, 40.0f, 15.0f};
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
		String[] title = {"#", "DB", "table", "column name length", "column", "data count"};
		Vector<String> headers = new Vector<String>(Arrays.asList(title));
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		JTable table;
		JScrollPane scroll;
		
		
		public ColumnResultUI(){
			setLayout(new BorderLayout());
			table = new JTable(data, headers);
			scroll = new JScrollPane(table);
			add(scroll);
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
