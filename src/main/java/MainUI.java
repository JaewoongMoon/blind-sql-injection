import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import config.ConfigPanel;
import control.ControlPanel;
import help.HelpUI;
import input.InputPanel;
import result.ResultPanel;
import status.StatusPanel;

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

	public JMenuBar menuBar = null;
	
	/** panels **/
	public ConfigPanel configPanel = null;
	public InputPanel inputPanel = null;
	public ControlPanel controlPanel = null;
	public StatusPanel statusPanel = null;
	public ResultPanel resultPanel = null;
	
	public MainUI(){
		/** set up frame **/ 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1330, 780);
		setVisible(true);
		setResizable(true);
		setTitle("Blind SQL Injection automation tool V1.0 - made by jwmoon");
		setLayout(null);
		
		/** menu Bar  **/
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Help(H)");
		menu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu);
		
		JMenuItem menuItem1 = new JMenuItem("Help");
		menuItem1.addActionListener(new HelpHandler());
		menu.add(menuItem1);
		setJMenuBar(menuBar);
		
		/** sub Panels **/
		final int PANEL_WIDTH = 700;
		
		// ConfigPanel
		configPanel = new ConfigPanel();
		configPanel.setBounds(0, 0, PANEL_WIDTH, 150);  // 700 x 150
		add(configPanel);
		
		// InputPanel
		inputPanel = new InputPanel();
		inputPanel.setBounds(0, 150, PANEL_WIDTH, 400); // 700 x 400
		add(inputPanel);
		
		// ControlPanel
		controlPanel = new ControlPanel();
		controlPanel.setBounds(0, 550, PANEL_WIDTH, 100); // 700 x 100
		add(configPanel);
		
		// StatusPanel
		statusPanel = new StatusPanel();
		statusPanel.setBounds(0, 650, PANEL_WIDTH, 200); // 700 x 200
		
		// ResultPanel
		resultPanel = new ResultPanel();
		resultPanel.setBounds(PANEL_WIDTH,0, PANEL_WIDTH, 850); // 700 x 850
		add(resultPanel);
		

	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainUI();
			}
		});
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
}
