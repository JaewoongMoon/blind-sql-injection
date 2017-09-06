/**
 * @ BlindSQLInjectionResultUI.java
 */
package ui;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
		setLayout(null);
		setSize(600, 700); 
		setVisible(true);
		
		tabs = new JTabbedPane();
		tabs.setBounds(0, 0, 600, 700);
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
	
	public DBResultUI getDBResultUI(){
		return dbUI;
	}

	/* sub tabs */
	public class DBResultUI extends JPanel{
		String[] title = {"순번", "DB이름 길이", "DB명", "테이블수"};
		Vector<String> headers = new Vector<String>(Arrays.asList(title));
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		JTable table;
		JScrollPane scroll;
		
		
		public DBResultUI(){
			table = new JTable(data, headers);
			scroll = new JScrollPane(table);
			add(scroll);
		}
		
		public DefaultTableModel getTableModel(){
			return (DefaultTableModel)table.getModel();
		}
	}
	
	class TableResultUI extends JPanel{
		
		public TableResultUI(){
			
		}
	}
	
	class ColumnResultUI extends JPanel{
		
		public ColumnResultUI(){
			
		}
	}

	class DataResultUI extends JPanel{
		
		public DataResultUI(){
			
		}
	}

}
