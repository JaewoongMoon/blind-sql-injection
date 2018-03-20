package config;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import base.Common;
import base.HintTextField;

/**
 * @author	Jae-Woong Moon(mjw8585@gmail.com)
 * @brief	
 * @Date	2018. 3. 15.	 
 */
public class ConfigPanel extends JPanel {

	// proxy
	JLabel proxyLb;
	JTextField proxyFld;
	
	// Length Until
	JLabel lenLb; 
	JTextField lenFld;
	
	// Count Until
	JLabel cntLb;
	JTextField cntFld;
	
	public ConfigPanel() {
		// panel setup
		setLayout(null);
		
		this.setBorder(BorderFactory.createTitledBorder("Configuration"));
		
		// proxy
		proxyLb = new JLabel("Local Proxy : ");
		proxyFld = new JTextField();
		proxyFld = new HintTextField("ex) 127.0.0.1:8080");
		proxyLb.setBounds(Common.START_X, Common.START_Y, 150, Common.INPUT_FIELD_HEIGHT);
		proxyFld.setBounds(proxyLb.getX() + proxyLb.getWidth(), Common.START_Y, 200, Common.INPUT_FIELD_HEIGHT);
		add(proxyLb);
		add(proxyFld);
		
		
		// Length Until 
		lenLb = new JLabel("Search Until : ");
		lenFld = new JTextField();
		lenLb.setBounds(Common.START_X, proxyLb.getY() + proxyLb.getHeight() + Common.PADDING_Y, 150, Common.INPUT_FIELD_HEIGHT);
		lenFld.setBounds(lenLb.getX() + lenLb.getWidth(), lenLb.getY(), 50, Common.INPUT_FIELD_HEIGHT);
		lenFld.setText("50");
		add(lenLb);
		add(lenFld);
		
		// Count Until
		cntLb = new JLabel("Count Until : ");
		cntFld = new JTextField();
		cntLb.setBounds(Common.START_X, lenLb.getY() + lenLb.getHeight() + Common.PADDING_Y, 150, Common.INPUT_FIELD_HEIGHT);
		cntFld.setBounds(cntLb.getX() + cntLb.getWidth(), cntLb.getY(), 50, Common.INPUT_FIELD_HEIGHT);
		cntFld.setText("60");
		add(cntLb);
		add(cntFld);
		//proxyFld.setText("127.0.0.1:8080");
	}
	
	public SystemConfig getSystemConfig() {
		// STEP 1. value check
		
		// STEP 2. create system config
		SystemConfig config = new SystemConfig();
		config.setCountUntil(Integer.parseInt(cntFld.getText()));
		config.setLengthUntil(Integer.parseInt(lenFld.getText()));
		
		if(!proxyFld.getText().equals("")) {
			String[] split = proxyFld.getText().split(":");
			if(split.length > 1) {
				config.setProxyAddress(split[0]);
				config.setPortNum(split[1]);
			}
		}
		
		return config;
	}
}
