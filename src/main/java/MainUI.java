import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import ui.BlindSQLInjectionInputUI;
import ui.BlindSQLInjectionResultUI;
import ui.HelpUI;

/**
 * @ MainUI.java
 */

/**
 * <pre>
 * 
 * MainUI.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/04
 */
public class MainUI extends JFrame{

	/*sub ui*/
	public BlindSQLInjectionInputUI inputUI = null;
	public BlindSQLInjectionResultUI resultUI = null; 
	public JMenuBar menuBar = null;
	
	
	public MainUI(){
		setTitle("Blind SQL Injection automation tool - made by jwmoon");
		setLayout(null);
		
		// menu Bar
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("도움말(H)");
		menu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu);
		
		JMenuItem menuItem1 = new JMenuItem("도움말");
		menuItem1.addActionListener(new HelpHandler());
		menu.add(menuItem1);
		
		//add(menuBar);
		setJMenuBar(menuBar);
		
		// add tabs
		inputUI = new BlindSQLInjectionInputUI();
		resultUI = new BlindSQLInjectionResultUI();
		inputUI.setBounds(0,0,700,700);
		resultUI.setBounds(700,0,600,700);
		
		add(inputUI);
		add(resultUI);
		
		// set up frame 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1330, 780);
		setVisible(true);
		setResizable(false);
	}
	
	class HelpHandler implements ActionListener{
		
		/**
		 * @Method 	: actionPerformed
		 * @brief	: 헬프윈도우를 생성한다.
		 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
		 * @Date	: 2017/09/04
		 * @param e
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			new HelpUI("Blind SQL Injection", "resources/help.txt");
		}
	}
	
	public static void main(String[] args) {
		new MainUI();
	}
}
