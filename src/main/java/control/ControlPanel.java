package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import base.Common;
import input.InputPanel;
import input.UserInput;

/**
 * @author	Jae-Woong Moon(mjw8585@gmail.com)
 * @brief	
 * @Date	2018. 3. 15.	 
 */
public class ControlPanel extends JPanel {

	private JButton startBtn;
	private JButton pauseBtn;
	private JButton stopBtn;
	
	/** ref **/
	private InputPanel inputPanel;
	private InjectionManager manager;
	
	
	public void setInputPanel(InputPanel inputPanel) {
		this.inputPanel = inputPanel;
	}
	
	public void setInjectionManager(InjectionManager manager) {
		this.manager = manager;
	}
	
	public ControlPanel() {
		// panel setup
		setLayout(null);
		//this.setBorder(BorderFactory.createTitledBorder("Control"));
		
		manager = new InjectionManager();
		
		startBtn = new JButton("Start");
		startBtn.addActionListener(new StartActionHandler());
		pauseBtn = new JButton("Pause");
		pauseBtn.addActionListener(new PauseActionHandler());
		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(new StopActionHandler());
		add(startBtn);
		add(pauseBtn);
		add(stopBtn);
		startBtn.setBounds(275, Common.PADDING_Y, 100, 35);
		pauseBtn.setBounds(startBtn.getX() + startBtn.getWidth() + Common.PADDING_X, startBtn.getY(), startBtn.getWidth(), startBtn.getHeight());
		stopBtn.setBounds(pauseBtn.getX() + pauseBtn.getWidth() + Common.PADDING_X, startBtn.getY(), startBtn.getWidth(), startBtn.getHeight());
		
		initButtons();
	}
	
	public void initButtons(){
		startBtn.setEnabled(true);
		pauseBtn.setEnabled(false);
		stopBtn.setEnabled(false);
	}
	
	class StartActionHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			// STEP 1. setup buttons
			startBtn.setEnabled(false);
			pauseBtn.setEnabled(true);
			stopBtn.setEnabled(true);
 
			// STEP 2. get user input
			UserInput input = inputPanel.getUserInput(); 
			
			// STEP 3. 로직 처리 요청
			manager.start(input);
		}
	}
	
	class PauseActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startBtn.setEnabled(true);
			pauseBtn.setEnabled(false);
			stopBtn.setEnabled(true);
			manager.pause();
		}
	}
	
	class StopActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			startBtn.setEnabled(true);
			pauseBtn.setEnabled(false);
			stopBtn.setEnabled(false);
			manager.stop();
		}
	}
}
