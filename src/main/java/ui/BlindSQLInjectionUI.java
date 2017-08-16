package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import logic.BlindSQLInjection;

public class BlindSQLInjectionUI extends JFrame{

	JLabel urlLabel;
	JTextField urlField;
	
	JTextArea resArea;
	JScrollPane resPane;
	
	JButton startBtn;
	
	final int START_X = 30;
	final int START_Y = 10;
	final int PADDING_Y = 30;
	final int PADDING_X = 30;
	
	public BlindSQLInjectionUI(){
		// frame setup
		setTitle("SQL Injection Resolver");
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(950, 700);
		setVisible(true);
		setResizable(false);
		
		// url
		urlLabel = new JLabel("URL : ");
		urlField = new JTextField();
		
		resArea = new JTextArea(5, 10);
		resArea.setLineWrap(true);
		resPane = new JScrollPane(resArea); 
		
		// btns
		startBtn = new JButton("Start!");
		startBtn.addActionListener(new StartActionHandler());
		
		// add components 
		add(urlLabel);
		add(urlField);
		add(resPane);
		add(startBtn);
		
		urlLabel.setBounds(START_X, START_Y, 500, 20);
		urlField.setBounds(urlLabel.getX() + PADDING_X, urlLabel.getY(), 500, 20);
		resPane.setBounds(START_X, urlField.getY() + 200, 500, 200);
		startBtn.setBounds(550, 220, 130, 40);
	}
	
	public static void main(String[] args) {
		new BlindSQLInjectionUI();
	}
	
	class StartActionHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String url = urlField.getText();
			BlindSQLInjection logic = new BlindSQLInjection();
			try {
				String result = logic.sendGet(url);
				resArea.setText(result);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
}
