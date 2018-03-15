package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import base.Common;
import base.SwingUtils;
import domain.enumeration.HttpQueryType;
import injection.InjectionManager;
import input.DbmsType;
import input.InputPanel;
import input.TargetType;
import input.UserInput;
import result.ResultPanel;

/**
 * @author	Jae-Woong Moon(mjw8585@gmail.com)
 * @brief	
 * @Date	2018. 3. 15.	 
 */
public class ControlPanel extends JPanel {

	private JButton startBtn;
	private JButton pauseBtn;
	private JButton stopBtn;
	
	/** another panels ref**/
	private InputPanel inputPanel;
	private ResultPanel resultPanel;
	
	private InjectionManager manager;
	
	
	public void setInputPanel(InputPanel inputPanel) {
		this.inputPanel = inputPanel;
	}
	
	public void setResultPanel(ResultPanel resultPanel) {
		this.resultPanel = resultPanel;
		this.manager.setResultPanel(resultPanel);
	}
	
	public ControlPanel() {
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
		startBtn.setBounds(300, 450, 100, 35);
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

			
			// STEP 2. user input 값 체크
			DbmsType selectedDbms = DbmsType.getDbmsType(SwingUtils.getSelectedButtonText(dbmsButtonGroup));
			TargetType selectedTarget = TargetType.getTargetType(SwingUtils.getSelectedButtonText(targetTypeButtonGroup));
			/*
			if(selectedTarget == TargetType.TABLE){
				if(dbNameField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please enter DB Name.");
					dbNameField.requestFocus();
					return ;
				}
			}*/
			
			
			// STEP 3. UserInput 객체 생성
			UserInput input = new UserInput();
			input.setTargetURL(urlField.getText());
			System.out.println("현재 선택 HTTP Method Combo index : " +methodCombo.getSelectedIndex());
			input.setHttpQueryType(HttpQueryType.valueOf(methodCombo.getSelectedIndex() + 1));
			System.out.println("현재 선택 HTTP QUERY TYPE : " + input.getHttpQueryType());
			input.setTargetParamName(targetParamField.getText());
			input.setTargetParamValue(targetParamValueField.getText());
			input.setEtcParamStr(etcParamField.getText());
			input.setMatch(matchField.getText());
			input.setDbmsType(selectedDbms);
			input.setTargetType(selectedTarget);
			input.setDbName(dbNameField.getText());
			/*input.setQueryType(QueryType.getQueryType(
					SwingUtils.getSelectedButtonText(queryTypeButtonGroup)));*/
			
			
			// searchCondition은 선택한 상황에 따라 다양한 값으로 변환되어 저장... 
			
			
			// STEP 4. 로직 처리 요청
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
