package input;

import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import http.HttpMethod;


public class InputPanel extends JPanel{

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
	
	// db name
	JLabel dbNameLabel; 
	JTextField dbNameField;
	
	// table name
	
	
	// column name

	final int START_X = 30;
	final int START_Y = 10;
	final int PADDING_Y = 15;
	final int PADDING_X = 10;
	final int COMPONENT_HEIGHT = 25;
	
	public InputPanel(){
		
		
		
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
		
		// http method 
		methodLabel = new JLabel("Http Method : ");
		httpMethods = new Vector<String>();
		httpMethods.add(HttpMethod.GET.toString());
		httpMethods.add(HttpMethod.POST.toString());
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

		// db name
		dbNameLabel = new JLabel("DB Name : ");
		dbNameField = new JTextField();
		add(dbNameLabel);
		add(dbNameField);
		dbNameLabel.setBounds(START_X, targetTypeLabel.getY() + targetTypeLabel.getHeight() + PADDING_Y, 150, COMPONENT_HEIGHT);
		dbNameField.setBounds(dbNameLabel.getX() + dbNameLabel.getWidth(), dbNameLabel.getY(), 200, COMPONENT_HEIGHT);
	
	}
}
