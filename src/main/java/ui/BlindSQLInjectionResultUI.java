/**
 * @ BlindSQLInjectionResultUI.java
 */
package ui;

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
 * <pre>
 * ui
 * BlindSQLInjectionResultUI.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/04
 */
public class BlindSQLInjectionResultUI extends JPanel{

	private JTabbedPane tabs = null;
	private DBResultUI dbUI = null;
	private TableResultUI tableUI = null;
	private ColumnResultUI columnUI = null;
	private DataResultUI dataUI = null; 
	
	public BlindSQLInjectionResultUI(){
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
		String[] title = {"순번", "DB명 길이", "DB명", "테이블수"};
		float[] columnWidthPercentage = {15.0f, 15.0f, 55.0f, 15.0f};
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
	}
	
	public class TableResultUI extends JPanel{
		String[] title = {"순번", "DB명", "테이블명 길이", "테이블명", "칼럼 수"};
		float[] columnWidthPercentage = {10.0f, 20.0f, 15.0f, 45.0f, 10.0f};
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
		String[] title = {"순번", "DB명", "테이블명", "칼럼명 길이", "칼럼명", "데이터 수"};
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
		String[] title = {"순번", "DB명", "테이블명", "칼럼명", "데이터 길이", "데이터 내용"};
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
