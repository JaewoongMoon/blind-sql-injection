import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import base.Common;
import config.ConfigPanel;
import control.ControlPanel;
import control.InjectionManager;
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
	
	/** logic **/
	private InjectionManager manager = null;
	
	public MainUI(){
		
		manager = new InjectionManager();
		
		/** set up frame **/ 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1350, 970);
		setVisible(true);
		setResizable(true);
		setTitle("Blind SQL Injection automation tool V1.2 - made by jwmoon");
		setLayout(null);
		
		/** menu bar  **/
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Help(H)");
		menu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu);
		
		JMenuItem menuItem1 = new JMenuItem("Help");
		menuItem1.addActionListener(new HelpHandler());
		menu.add(menuItem1);
		setJMenuBar(menuBar);
		
		/** sub panels **/
		final int LEFT_PANEL_WIDTH = 600;
		final int RIGHT_PANEL_WIDTH = 700;
		
		// ConfigPanel
		configPanel = new ConfigPanel();
		configPanel.setBounds(Common.PADDING_X, Common.PADDING_Y, LEFT_PANEL_WIDTH, 130);  // 700 x 130
		add(configPanel);
		
		// InputPanel
		inputPanel = new InputPanel();
		inputPanel.setBounds(Common.PADDING_X, 150, LEFT_PANEL_WIDTH, 400); // 700 x 400
		add(inputPanel);
		
		// ControlPanel
		controlPanel = new ControlPanel();
		controlPanel.setBounds(Common.PADDING_X, 550, LEFT_PANEL_WIDTH, 50); // 700 x 100
		add(controlPanel);
		
		
		// StatusPanel
		statusPanel = new StatusPanel();
		statusPanel.setBounds(Common.PADDING_X, 600, LEFT_PANEL_WIDTH, 300); // 700 x 200
		add(statusPanel);
		
		// ResultPanel
		resultPanel = new ResultPanel();
		resultPanel.setBounds(LEFT_PANEL_WIDTH + 20, 0, RIGHT_PANEL_WIDTH, 850); // 700 x 850
		add(resultPanel);
		
		/** set reference  **/
		controlPanel.setInputPanel(inputPanel);
		controlPanel.setInjectionManager(manager);
		manager.setControlPanel(controlPanel);
		manager.setResultPanel(resultPanel);
		manager.setStatusPanel(statusPanel);
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
