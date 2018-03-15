package status;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
	
	public JLabel getStatusField() {
		return statusField;
	}

	public JTextArea getLogArea() {
		return logArea;
	}
	
	public StatusPanel() {
		
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

	}
}
