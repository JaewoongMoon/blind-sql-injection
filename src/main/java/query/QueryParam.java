package query;

import input.Step;

/**
 * @author	Jae-Woong Moon(mjw8585@gmail.com)
 * @brief 	Programが判断する値
 * @Date	2018. 3. 20.	 
 */
public class QueryParam {

	// common
	private String checkVal; // Blind SQL Injectionクエリの真かどうかを比較するための値  ex) ((some sql) = checkVal) 
	private Step step;
	private QueryType queryType;
	
	// db
	private int dbIndex = -1;  // DB 스키마의 Index (전체 DB 중 몇 번째 스키마인지...) 
	private int dbNameIndex = -1; //DB 이름의 알파벳 Index
	private String dbName;
	
	// table
	private int tableIndex = -1;
	private int tableNameIndex = -1;
	private String tableName;
	
	// column
	private int columnIndex = -1;
	private int columnNameIndex = -1;
	private String columnName;
	
	// data
	// ...
	
	public QueryType getQueryType() {
		return queryType;
	}
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public int getColumnNameIndex() {
		return columnNameIndex;
	}
	public void setColumnNameIndex(int columnNameIndex) {
		this.columnNameIndex = columnNameIndex;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Step getStep() {
		return step;
	}
	public void setStep(Step step) {
		this.step = step;
	}
	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getTableNameIndex() {
		return tableNameIndex;
	}

	public void setTableNameIndex(int tableNameIndex) {
		this.tableNameIndex = tableNameIndex;
	}
	
	public String getCheckVal() {
		return checkVal;
	}
	public void setCheckVal(String checkVal) {
		this.checkVal = checkVal;
	}
	public int getDbIndex() {
		return dbIndex;
	}
	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}
	public int getDbNameIndex() {
		return dbNameIndex;
	}
	public void setDbNameIndex(int dbNameIndex) {
		this.dbNameIndex = dbNameIndex;
	}
	public int getTableIndex() {
		return tableIndex;
	}
	public void setTableIndex(int tableIndex) {
		this.tableIndex = tableIndex;
	}
	
	
}
