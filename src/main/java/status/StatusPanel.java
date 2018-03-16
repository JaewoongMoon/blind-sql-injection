package status;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import base.Common;

/**
 * @author	Jae-Woong Moon(mjw8585@gmail.com)
 * @brief	
 * @Date	2018. 3. 15.	 
 */
public class StatusPanel extends JPanel{

	// status
	JLabel statusLabel;
	JLabel statusField;
	
	// logs
	JLabel logLabel;
	JTextArea logArea;
	JScrollPane logPane;
	
	public void printLog(String msg) {
		this.logArea.setText(logArea.getText() + msg + "\n");
	}
	
	public void printRequestCount(String cnt) {
		this.statusField.setText(cnt);
	}
	
	public StatusPanel() {
		// panel setup
		setLayout(null);
		this.setBorder(BorderFactory.createTitledBorder("Status"));
		
		
		// status
		statusLabel = new JLabel("Send Request : ");
		statusField = new JLabel("0");
		add(statusLabel);
		add(statusField);
		statusLabel.setBounds(Common.START_X, Common.START_Y + Common.PADDING_Y, 150, Common.INPUT_FIELD_HEIGHT);
		statusField.setBounds(statusLabel.getX() + statusLabel.getWidth(), statusLabel.getY(), 70, Common.INPUT_FIELD_HEIGHT);
		
		
		// logs
		logLabel = new JLabel("Log"); 
		logArea = new JTextArea(5, 10);
		logArea.setLineWrap(true);
		logPane = new JScrollPane(logArea);
		add(logLabel);
		add(logPane);
		logLabel.setBounds(Common.START_X, statusLabel.getY() + statusLabel.getHeight(), 100, Common.INPUT_FIELD_HEIGHT);
		logPane.setBounds(Common.START_X, logLabel.getY() + logLabel.getHeight(), 550, 200);

	}
}
