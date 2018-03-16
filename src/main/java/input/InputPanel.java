package input;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import base.Common;
import base.SwingUtils;
import http.HttpMethod;


public class InputPanel extends JPanel{

	// url
	JLabel urlLb;
	JTextField urlFld;
	
	// http method
	JLabel methodLb;
	JComboBox<String> methodCombo;
	Vector<String> httpMethods;
	
	// target parameter name
	JLabel targetParamLb;
	JTextField targetParamFld;
	
	// target parameter value 
	JLabel targetParamValueLb;
	JTextField targetParamValueFld;
	
	// etc parameter string
	JLabel etcParamLb;
	JTextField etcParamFld;
	
	// match
	JLabel matchLb;
	JTextField matchFld;
	
	// dbms type
	JLabel dbmsLb;
	ButtonGroup dbmsBtnGroup;
	JRadioButton dbmsBtn1;
	JRadioButton dbmsBtn2;
	JRadioButton dbmsBtn3;
	
	// target type
	JLabel targetTypeLb;
	ButtonGroup targetTypeBtnGroup;
	JRadioButton targetTypeBtn1;
	JRadioButton targetTypeBtn2;
	JRadioButton targetTypeBtn3;
	JRadioButton targetTypeBtn4;
	JRadioButton targetTypeBtn5;
	
	// db name
	JLabel dbNameLb; 
	JTextField dbNameFld;
	
	// table name
	JLabel tblNameLb;
	JTextField tblNameFld;
	
	// column name
	JLabel colNameLb;
	JTextField colNameFld;

	final int START_X = Common.START_X;
	final int START_Y = Common.START_Y;
	final int PADDING_Y = Common.PADDING_Y;
	final int PADDING_X = Common.PADDING_X;
	final int INPUT_FIELD_HEIGHT = Common.INPUT_FIELD_HEIGHT; 
	
	public InputPanel(){
		// panel setup
		setLayout(null);
		this.setBorder(BorderFactory.createTitledBorder("User Input"));
		
		// url
		urlLb = new JLabel("Target URL : ");
		urlFld = new JTextField();
		urlLb.setBounds(START_X, START_Y, 150, INPUT_FIELD_HEIGHT);
		urlFld.setBounds(urlLb.getX() + urlLb.getWidth(), urlLb.getY(), 400, INPUT_FIELD_HEIGHT);
		add(urlLb);
		add(urlFld);
		
		// http method 
		methodLb = new JLabel("Http Method : ");
		httpMethods = new Vector<String>();
		httpMethods.add(HttpMethod.GET.toString());
		httpMethods.add(HttpMethod.POST.toString());
		methodCombo = new JComboBox<>(httpMethods);
		methodLb.setBounds(START_X, urlLb.getY() + urlLb.getHeight() + PADDING_Y, 150, INPUT_FIELD_HEIGHT);
		methodCombo.setBounds(methodLb.getX() + methodLb.getWidth(), methodLb.getY(), 200, INPUT_FIELD_HEIGHT);
		add(methodLb);
		add(methodCombo);
		
		// target parameter name
		targetParamLb = new JLabel("Target Parameter Name :");
		targetParamFld = new JTextField();
		targetParamLb.setBounds(START_X, methodLb.getY() + methodLb.getHeight() + PADDING_Y, 150, INPUT_FIELD_HEIGHT);
		targetParamFld.setBounds(targetParamLb.getX() + targetParamLb.getWidth(), targetParamLb.getY(), 150, INPUT_FIELD_HEIGHT);
		add(targetParamLb);
		add(targetParamFld);

		// target parameter value
		targetParamValueLb = new JLabel("Target Parameter Value :");
		targetParamValueFld = new JTextField();
		targetParamValueLb.setBounds(START_X, targetParamLb.getY() + targetParamLb.getHeight() + PADDING_Y, 150, INPUT_FIELD_HEIGHT);
		targetParamValueFld.setBounds(targetParamValueLb.getX() + targetParamValueLb.getWidth(), targetParamValueLb.getY(), 150, INPUT_FIELD_HEIGHT);
		add(targetParamValueLb);
		add(targetParamValueFld);
		
		// etc paramter string
		etcParamLb = new JLabel("Etc Parameter String : "); 
		etcParamFld = new JTextField();
		etcParamLb.setBounds(START_X, targetParamValueLb.getY() + targetParamValueLb.getHeight() + PADDING_Y, 150, INPUT_FIELD_HEIGHT);
		etcParamFld.setBounds(etcParamLb.getX() + etcParamLb.getWidth(), etcParamLb.getY(), 250, INPUT_FIELD_HEIGHT);
		add(etcParamLb);
		add(etcParamFld);
		
		// match
		matchLb = new JLabel("Success String (Match) : ");
		matchFld = new JTextField();
		matchLb.setBounds(START_X, etcParamLb.getY() + etcParamLb.getHeight() + PADDING_Y, 150, INPUT_FIELD_HEIGHT);
		matchFld.setBounds(matchLb.getX() + matchLb.getWidth(), matchLb.getY(), 250, INPUT_FIELD_HEIGHT);
		add(matchLb);
		add(matchFld);
		
		// dbms type radio buttons
		dbmsLb = new JLabel("DBMS : ");
		dbmsBtn1 = new JRadioButton("MySQL");
		dbmsBtn2 = new JRadioButton("MS SQL");
		dbmsBtn3 = new JRadioButton("Oracle");
		dbmsBtnGroup = new ButtonGroup();
		dbmsBtn1.setSelected(true);
		dbmsBtnGroup.add(dbmsBtn1);
		dbmsBtnGroup.add(dbmsBtn2);
		dbmsBtnGroup.add(dbmsBtn3);
		dbmsLb.setBounds(START_X, matchLb.getY() + matchLb.getHeight() + PADDING_Y, 150, INPUT_FIELD_HEIGHT);
		dbmsBtn1.setBounds(dbmsLb.getX() + dbmsLb.getWidth(), dbmsLb.getY(), 70, INPUT_FIELD_HEIGHT);
		dbmsBtn2.setBounds(dbmsBtn1.getX() + dbmsBtn1.getWidth(), dbmsLb.getY(), 70, INPUT_FIELD_HEIGHT);
		dbmsBtn2.setEnabled(false);
		dbmsBtn3.setBounds(dbmsBtn2.getX() + dbmsBtn2.getWidth(), dbmsLb.getY(), 70, INPUT_FIELD_HEIGHT);
		dbmsBtn3.setEnabled(false);
		add(dbmsLb);
		add(dbmsBtn1);
		add(dbmsBtn2);
		add(dbmsBtn3);
		
		// target type radio buttons
		targetTypeLb = new JLabel("Target : ");
		targetTypeBtn1 = new JRadioButton("DB");
		targetTypeBtn2 = new JRadioButton("Table");
		targetTypeBtn3 = new JRadioButton("Column");
		targetTypeBtn4 = new JRadioButton("Data");
		targetTypeBtn5 = new JRadioButton("All");
		targetTypeBtnGroup = new ButtonGroup();
		targetTypeBtn1.setSelected(true);
		targetTypeBtnGroup.add(targetTypeBtn1);
		targetTypeBtnGroup.add(targetTypeBtn2);
		targetTypeBtnGroup.add(targetTypeBtn3);
		targetTypeBtnGroup.add(targetTypeBtn4);
		targetTypeBtnGroup.add(targetTypeBtn5);
		targetTypeLb.setBounds(START_X, dbmsLb.getY() + dbmsLb.getHeight() + PADDING_Y, 150, INPUT_FIELD_HEIGHT);
		targetTypeBtn1.setBounds(targetTypeLb.getX() + targetTypeLb.getWidth(), targetTypeLb.getY(),70, INPUT_FIELD_HEIGHT);
		targetTypeBtn2.setBounds(targetTypeBtn1.getX() + targetTypeBtn1.getWidth(), targetTypeLb.getY(),70, INPUT_FIELD_HEIGHT);
		targetTypeBtn3.setBounds(targetTypeBtn2.getX() + targetTypeBtn2.getWidth(), targetTypeLb.getY(),70, INPUT_FIELD_HEIGHT);
		targetTypeBtn3.setEnabled(false);
		targetTypeBtn4.setBounds(targetTypeBtn3.getX() + targetTypeBtn3.getWidth(), targetTypeLb.getY(),70, INPUT_FIELD_HEIGHT);
		targetTypeBtn4.setEnabled(false);
		targetTypeBtn5.setBounds(targetTypeBtn4.getX() + targetTypeBtn4.getWidth(), targetTypeLb.getY(),70, INPUT_FIELD_HEIGHT);
		targetTypeBtn5.setEnabled(false);
		add(targetTypeLb);
		add(targetTypeBtn1);
		add(targetTypeBtn2);
		add(targetTypeBtn3);
		add(targetTypeBtn4);
		add(targetTypeBtn5);

		// db name
		dbNameLb = new JLabel("DB Name : ");
		dbNameFld = new JTextField();
		dbNameLb.setBounds(START_X, targetTypeLb.getY() + targetTypeLb.getHeight() + PADDING_Y, 150, INPUT_FIELD_HEIGHT);
		dbNameFld.setBounds(dbNameLb.getX() + dbNameLb.getWidth(), dbNameLb.getY(), 200, INPUT_FIELD_HEIGHT);
		add(dbNameLb);
		add(dbNameFld);
		
		// table Name
		tblNameLb = new JLabel("Table Name : ");
		tblNameFld = new JTextField();
		tblNameLb.setBounds(START_X, dbNameLb.getY() + dbNameLb.getHeight() + 5, 150, INPUT_FIELD_HEIGHT);
		tblNameFld.setBounds(tblNameLb.getX() + tblNameLb.getWidth(), tblNameLb.getY(), 200, INPUT_FIELD_HEIGHT);
		add(tblNameLb);
		add(tblNameFld);
		
		// column Name
		colNameLb = new JLabel("Column Name : ");
		colNameFld = new JTextField();
		colNameLb.setBounds(START_X, tblNameLb.getY() + tblNameLb.getHeight() + 5, 150, INPUT_FIELD_HEIGHT);
		colNameFld.setBounds(colNameLb.getX() + colNameLb.getWidth(), colNameLb.getY(), 200, INPUT_FIELD_HEIGHT);
		add(colNameLb);
		add(colNameFld);
	}
	
	public UserInput getUserInput() {
		
		// STEP 1. user input 값 체크 후 필요한 값이 없을 경우 얼럿창 
		
		// STEP 2. UserInput 객체 생성
		DbmsType selectedDbms = DbmsType.getDbmsType(SwingUtils.getSelectedButtonText(dbmsBtnGroup));
		TargetType selectedTarget = TargetType.getTargetType(SwingUtils.getSelectedButtonText(targetTypeBtnGroup));
		//System.out.println("현재 선택 HTTP Method Combo index : " +methodCombo.getSelectedIndex());
		UserInput input = new UserInput();
		input.setTargetURL(urlFld.getText());
		input.setTargetParamName(targetParamFld.getText());
		input.setTargetParamValue(targetParamValueFld.getText());
		input.setEtcParamStr(etcParamFld.getText());
		input.setMatch(matchFld.getText());
		input.setDbmsType(selectedDbms);
		input.setTargetType(selectedTarget);
		input.setDbName(dbNameFld.getText());
		return input;
		
	}
}
