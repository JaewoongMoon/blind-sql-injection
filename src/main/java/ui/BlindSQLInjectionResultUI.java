/**
 * @ BlindSQLInjectionResultUI.java
 */
package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

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

	/* sub tabs */
	class DBResultUI extends JPanel{
		
		public DBResultUI(){
			
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
