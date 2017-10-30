package ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import domain.UserInput;
import domain.enumeration.DbmsType;
import domain.enumeration.HttpQueryType;
import domain.enumeration.QueryType;
import domain.enumeration.TargetType;
import logic.BlindSQLInjectionManager;
import util.SwingUtils;


public class BlindSQLInjectionInputUI extends JPanel{

	/** Domain 설정 **/
	// url
	JLabel urlLabel;
	JTextField urlField;
	
	// http method
	JLabel methodLabel;
	JComboBox<String> methodCombo;
	Vector<String> httpMethods;
	
	// target parameter name
	JLabel targetParamLabel;
	JTextField targetParamField;
	
	// target parameter value 
	JLabel targetParamValueLabel;
	JTextField targetParamValueField;
	
	// etc parameter string
	JLabel etcParamLabel;
	JTextField etcParamField;
	
	// match
	JLabel matchLabel;
	JTextField matchField;
	
	
	
	/** 세부 설정 **/
	// dbms type
	JLabel dbmsLabel;
	ButtonGroup dbmsButtonGroup;
	JRadioButton dbmsButton1;
	JRadioButton dbmsButton2;
	
	// target type
	JLabel targetTypeLabel;
	ButtonGroup targetTypeButtonGroup;
	JRadioButton targetTypeButton1;
	JRadioButton targetTypeButton2;
	JRadioButton targetTypeButton3;
	JRadioButton targetTypeButton4;
	
	// query type
	/*
	JLabel queryTypeLabel;
	ButtonGroup queryTypeButtonGroup;
	JRadioButton queryTypeButton1;
	JRadioButton queryTypeButton2;
	JRadioButton queryTypeButton3;
	*/
	
	// db name
	JLabel dbNameLabel; 
	JTextField dbNameField;
	
	// table name
	
	
	// column name
	
	
	
	// 검색 조건 : search until (numbers only) or search name 
	JLabel searchConditionLabel; 
	JTextField searchConditionField;
	
	// query payload (입력한 조건에 따라 완성된)
	/*
	JLabel payloadLabel;
	JTextField payloadField;
	*/
	
	/** Controls **/
	// btns
	JButton startBtn;
	JButton pauseBtn;
	JButton stopBtn;
	
	// status
	JLabel statusLabel;
	JLabel statusField;
	
	// logs
	JLabel logLabel;
	JTextArea logArea;
	JScrollPane logPane;
	
	
	final int START_X = 30;
	final int START_Y = 10;
	final int PADDING_Y = 15;
	final int PADDING_X = 10;
	final int COMPONENT_HEIGHT = 25;
	
	BlindSQLInjectionManager manager;
	BlindSQLInjectionResultUI resultUI;
	
	public void setResultUI(BlindSQLInjectionResultUI resultUI){
		this.resultUI = resultUI;
		manager.setResultUI(resultUI);
	}
	
	public JLabel getStatusField() {
		return statusField;
	}

	public JTextArea getLogArea() {
		return logArea;
	}

	public BlindSQLInjectionInputUI(){
		
		manager = new BlindSQLInjectionManager();
		manager.setInputUI(this);
		
		// panel setup
		setLayout(null);
		setSize(700, 700);
		setVisible(true);
		
		// url
		urlLabel = new JLabel("Target URL : ");
		urlField = new JTextField();
		add(urlLabel);
		add(urlField);
		urlLabel.setBounds(START_X, START_Y, 150, COMPONENT_HEIGHT);
		urlField.setBounds(urlLabel.getX() + urlLabel.getWidth(), urlLabel.getY(), 400, COMPONENT_HEIGHT);
		
		// http method types
		methodLabel = new JLabel("Http Method : ");
		httpMethods = new Vector<String>();
		//httpMethods.add("--SELECT--");
		httpMethods.add(HttpQueryType.GET_QUERY_ON_URL.toString());
		httpMethods.add(HttpQueryType.GET_QUERY_ON_PARAM.toString());
		httpMethods.add(HttpQueryType.POST_QUERY_ON_PARAM.toString());
		methodCombo = new JComboBox<>(httpMethods);
		add(methodLabel);
		add(methodCombo);
		methodLabel.setBounds(START_X, urlLabel.getY() + urlLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		methodCombo.setBounds(methodLabel.getX() + methodLabel.getWidth(), methodLabel.getY(), 200, COMPONENT_HEIGHT);
		
		// target parameter name
		targetParamLabel = new JLabel("Target Parameter Name :");
		targetParamField = new JTextField();
		add(targetParamLabel);
		add(targetParamField);
		targetParamLabel.setBounds(START_X, methodLabel.getY() + methodLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		targetParamField.setBounds(targetParamLabel.getX() + targetParamLabel.getWidth(), targetParamLabel.getY(), 150, COMPONENT_HEIGHT);

		// target parameter value
		targetParamValueLabel = new JLabel("Target Parameter Value :");
		targetParamValueField = new JTextField();
		add(targetParamValueLabel);
		add(targetParamValueField);
		targetParamValueLabel.setBounds(START_X, targetParamLabel.getY() + targetParamLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		targetParamValueField.setBounds(targetParamValueLabel.getX() + targetParamValueLabel.getWidth(), targetParamValueLabel.getY(), 150, COMPONENT_HEIGHT);
		
		// etc paramter string
		etcParamLabel = new JLabel("Etc Parameter String : "); 
		etcParamField = new JTextField();
		add(etcParamLabel);
		add(etcParamField);
		etcParamLabel.setBounds(START_X, targetParamValueLabel.getY() + targetParamValueLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		etcParamField.setBounds(etcParamLabel.getX() + etcParamLabel.getWidth(), etcParamLabel.getY(), 250, COMPONENT_HEIGHT);
		
		// match
		matchLabel = new JLabel("Success String (Match) : ");
		matchField = new JTextField();
		add(matchLabel);
		add(matchField);
		matchLabel.setBounds(START_X, etcParamLabel.getY() + etcParamLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		matchField.setBounds(matchLabel.getX() + matchLabel.getWidth(), matchLabel.getY(), 250, COMPONENT_HEIGHT);
		
		// dbms type radio buttons
		dbmsLabel = new JLabel("DBMS : ");
		dbmsButton1 = new JRadioButton("MySQL");
		dbmsButton2 = new JRadioButton("MS SQL");
		dbmsButtonGroup = new ButtonGroup();
		dbmsButton1.setSelected(true);
		dbmsButtonGroup.add(dbmsButton1);
		dbmsButtonGroup.add(dbmsButton2);
		add(dbmsLabel);
		add(dbmsButton1);
		add(dbmsButton2);
		dbmsLabel.setBounds(START_X, matchLabel.getY() + matchLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		dbmsButton1.setBounds(dbmsLabel.getX() + dbmsLabel.getWidth(), dbmsLabel.getY(), 70, COMPONENT_HEIGHT);
		dbmsButton2.setBounds(dbmsButton1.getX() + dbmsButton1.getWidth(), dbmsLabel.getY(), 70, COMPONENT_HEIGHT);
		dbmsButton2.setEnabled(false);
		
		// target type radio buttons
		targetTypeLabel = new JLabel("Target : ");
		targetTypeButton1 = new JRadioButton("DB");
		targetTypeButton2 = new JRadioButton("Table");
		targetTypeButton3 = new JRadioButton("Column");
		targetTypeButton4 = new JRadioButton("Data");
		targetTypeButtonGroup = new ButtonGroup();
		targetTypeButton1.setSelected(true);
		targetTypeButtonGroup.add(targetTypeButton1);
		targetTypeButtonGroup.add(targetTypeButton2);
		targetTypeButtonGroup.add(targetTypeButton3);
		targetTypeButtonGroup.add(targetTypeButton4);
		add(targetTypeLabel);
		add(targetTypeButton1);
		add(targetTypeButton2);
		add(targetTypeButton3);
		add(targetTypeButton4);
		targetTypeLabel.setBounds(START_X, dbmsLabel.getY() + dbmsLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		targetTypeButton1.setBounds(targetTypeLabel.getX() + targetTypeLabel.getWidth(), targetTypeLabel.getY(),70, COMPONENT_HEIGHT);
		targetTypeButton2.setBounds(targetTypeButton1.getX() + targetTypeButton1.getWidth(), targetTypeLabel.getY(),70, COMPONENT_HEIGHT);
		targetTypeButton3.setBounds(targetTypeButton2.getX() + targetTypeButton2.getWidth(), targetTypeLabel.getY(),70, COMPONENT_HEIGHT);
		targetTypeButton4.setBounds(targetTypeButton3.getX() + targetTypeButton3.getWidth(), targetTypeLabel.getY(),70, COMPONENT_HEIGHT);
		targetTypeButton3.setEnabled(false);
		targetTypeButton4.setEnabled(false);
		
		
		// query type radio buttons
		/*
		queryTypeLabel = new JLabel("쿼리 타입 : ");
		queryTypeButton1 = new JRadioButton("갯수");
		queryTypeButton2 = new JRadioButton("이름의 길이");
		queryTypeButton3 = new JRadioButton("내용");
		queryTypeButtonGroup = new ButtonGroup();
		queryTypeButton1.setSelected(true);
		queryTypeButtonGroup.add(queryTypeButton1);
		queryTypeButtonGroup.add(queryTypeButton2);
		queryTypeButtonGroup.add(queryTypeButton3);
		add(queryTypeLabel);
		add(queryTypeButton1);
		add(queryTypeButton2);
		add(queryTypeButton3);
		queryTypeLabel.setBounds(START_X, targetTypeLabel.getY() + targetTypeLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		queryTypeButton1.setBounds(queryTypeLabel.getX() + queryTypeLabel.getWidth(), queryTypeLabel.getY(),70, COMPONENT_HEIGHT);
		queryTypeButton2.setBounds(queryTypeButton1.getX() + queryTypeButton1.getWidth(), queryTypeLabel.getY(),100, COMPONENT_HEIGHT);
		queryTypeButton3.setBounds(queryTypeButton2.getX() + queryTypeButton2.getWidth(), queryTypeLabel.getY(),70, COMPONENT_HEIGHT);
		queryTypeButton1.setEnabled(false);
		queryTypeButton2.setEnabled(false);
		queryTypeButton3.setEnabled(false);
		*/
		
		// db name
		dbNameLabel = new JLabel("DB Name : ");
		dbNameField = new JTextField();
		add(dbNameLabel);
		add(dbNameField);
		dbNameLabel.setBounds(START_X, targetTypeLabel.getY() + targetTypeLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		dbNameField.setBounds(dbNameLabel.getX() + dbNameLabel.getWidth(), dbNameLabel.getY(), 200, COMPONENT_HEIGHT);
		
		
		// search condition 
		searchConditionLabel = new JLabel("Search Until (max num) : ");
		searchConditionField = new JTextField();
		add(searchConditionLabel);
		add(searchConditionField);
		searchConditionLabel.setBounds(START_X, dbNameLabel.getY() + dbNameLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		searchConditionField.setBounds(searchConditionLabel.getX() + searchConditionLabel.getWidth(), searchConditionLabel.getY(), 200, COMPONENT_HEIGHT);
		searchConditionField.setEnabled(false);
		
		// payload 
		/*
		payloadLabel = new JLabel("전송될 Payload : ");
		payloadField = new JTextField();
		add(payloadLabel);
		add(payloadField);
		payloadLabel.setBounds(START_X, searchConditionLabel.getY() + searchConditionLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		payloadField.setBounds(payloadLabel.getX() + payloadLabel.getWidth(), payloadLabel.getY(), 400, COMPONENT_HEIGHT);
		payloadField.setEnabled(false);
		*/
		
		// btns
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
		pauseBtn.setBounds(startBtn.getX() + startBtn.getWidth() + PADDING_X, startBtn.getY(), startBtn.getWidth(), startBtn.getHeight());
		stopBtn.setBounds(pauseBtn.getX() + pauseBtn.getWidth() + PADDING_X, startBtn.getY(), startBtn.getWidth(), startBtn.getHeight());
		
		// status
		statusLabel = new JLabel("Send Request : ");
		statusField = new JLabel("0");
		add(statusLabel);
		add(statusField);
		statusLabel.setBounds(START_X, startBtn.getY() + startBtn.getHeight(), 150, COMPONENT_HEIGHT);
		statusField.setBounds(statusLabel.getX() + statusLabel.getWidth(), statusLabel.getY(), 70, COMPONENT_HEIGHT);
		
		
		// logs
		logLabel = new JLabel("Log"); 
		logArea = new JTextArea(5, 10);
		logArea.setLineWrap(true);
		logPane = new JScrollPane(logArea);
		add(logLabel);
		add(logPane);
		logLabel.setBounds(START_X, statusLabel.getY() + statusLabel.getHeight(), 100, COMPONENT_HEIGHT);
		logPane.setBounds(START_X, logLabel.getY() + logLabel.getHeight(), 610, 200);

		initButtons();
		init();
	}
	
	public void init(){
		
		// 테스트용 값 1
		/*
		urlField.setText("http://localhost:8080/unsafeweb/loginProcess.jsp");
		targetParamField.setText("userid");
		targetParamValueField.setText("admin");
		methodCombo.setSelectedIndex(2);
		matchField.setText("Vulnerable");
		*/
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
