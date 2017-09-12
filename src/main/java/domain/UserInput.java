/**
 * @ UserInput.java
 */
package domain;

import domain.enumeration.AttackVector;
import domain.enumeration.DbmsType;
import domain.enumeration.HttpQueryType;
import domain.enumeration.QueryType;
import domain.enumeration.TargetType;

/**
 * <pre>
 * domain
 * UserInput.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/05
 */
public class UserInput {

	/** User Inputs **/ 
	private DbmsType dbmsType;
	private TargetType targetType;
	private QueryType queryType;
	private HttpQueryType httpQueryType;
	private String targetURL;
	private String targetParamName;
	private String targetParamValue;
	private String etcParamStr;
	private String match;
	
	
	/** Program 이 판단하는 값들 **/
	// 공통
	private String checkVal; 
	private int lengthUntil = 50; // search length until
	private int countUntil = 30; // search count until
	
	// db
	private int dbIndex = -1;  // DB 스키마의 Index (전체 DB 중 몇 번째 스키마인지...) 
	private int dbNameIndex = -1; //DB 이름의 알파벳 Index
	private String dbName;
	
	// table
	private int tableIndex = -1;
	private int tableNameIndex = -1;
	private String tableName;
	
	/*
	public UserInput copy(UserInput src){
		UserInput newInput = new UserInput();
		newInput.setDbmsType(src.getDbmsType());
		newInput.setTargetType(src.getTargetType());
		newInput.setQueryType(src.getQueryType());
		newInput.setHttpQueryType(src.getHttpQueryType());
		newInput.setTargetURL(src.getTargetURL());
		newInput.setTargetParamName(src.getTargetParamName());
		newInput.setTargetParamValue(src.getTargetParamValue());
		newInput.setEtcParamStr(src.getEtcParamStr());
		newInput.setMatch(src.getMatch());
		newInput.setCheckVal(src.getCheckVal());
		//..
		
		return newInput;
	}*/
	
	public AttackVector getAttackVector() {
		return AttackVector.getAttackVector(dbmsType, targetType, queryType);
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

	public DbmsType getDbmsType() {
		return dbmsType;
	}
	public void setDbmsType(DbmsType dbmsType) {
		this.dbmsType = dbmsType;
	}
	public TargetType getTargetType() {
		return targetType;
	}
	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}
	public QueryType getQueryType() {
		return queryType;
	}
	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
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
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public int getTableIndex() {
		return tableIndex;
	}
	public void setTableIndex(int tableIndex) {
		this.tableIndex = tableIndex;
	}
	
	public String getTargetURL() {
		return targetURL;
	}
	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}
	
	public String getTargetParamName() {
		return targetParamName;
	}
	public void setTargetParamName(String targetParamName) {
		this.targetParamName = targetParamName;
	}
	public String getTargetParamValue() {
		return targetParamValue;
	}
	public void setTargetParamValue(String targetParamValue) {
		this.targetParamValue = targetParamValue;
	}
	public String getEtcParamStr() {
		return etcParamStr;
	}
	public void setEtcParamStr(String etcParamStr) {
		this.etcParamStr = etcParamStr;
	}
	public HttpQueryType getHttpQueryType() {
		return httpQueryType;
	}
	public void setHttpQueryType(HttpQueryType httpQueryType) {
		this.httpQueryType = httpQueryType;
	}
	public int getCountUntil() {
		return countUntil;
	}
	public void setCountUntil(int countUntil) {
		this.countUntil = countUntil;
	}
	public int getLengthUntil() {
		return lengthUntil;
	}
	public void setLengthUntil(int lengthUntil) {
		this.lengthUntil = lengthUntil;
	}
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	
	
	
}
