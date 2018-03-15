package config;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import base.Common;

/**
 * @author	Jae-Woong Moon(mjw8585@gmail.com)
 * @brief	
 * @Date	2018. 3. 15.	 
 */
public class ConfigPanel extends JPanel {

	// proxy
	JLabel proxyLabel;
	JTextField proxyField;
	
	// 검색 조건 : search until (numbers only) 
	JLabel searchConditionLabel; 
	JTextField searchConditionField;
	
	public ConfigPanel() {
		//search condition 
		searchConditionLabel = new JLabel("Search Until (max num) : ");
		searchConditionField = new JTextField();
		
		add(searchConditionLabel);
		add(searchConditionField);
		searchConditionLabel.setBounds(
				Common.START_X,  
				Common.START_Y, 
				150, 
				Common.INPUT_FIELD_HEIGHT);
		searchConditionField.setBounds(
				searchConditionLabel.getX() + searchConditionLabel.getWidth(), 
				searchConditionLabel.getY(), 
				200, 
				Common.INPUT_FIELD_HEIGHT);
		searchConditionField.setEnabled(false);
	}
}
