/**
 * @ QueryCondition.java
 */
package logic.domain;

/**
 * <pre>
 * logic.domain
 * QueryCondition.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class QueryCondition {
	private DbmsType dbmsType;
	private TargetType targetType;
	private QueryType queryType;
	private String checkVal;
	private int dbIndex = -1;
	private int dbNameIndex = -1;
	private int tableIndex = -1;
	private String dbName;
	
	
	/**
	 * 
	 */
	public QueryCondition() {
		
	}
	
	public QueryCondition(DbmsType dbmsType, TargetType targetType, QueryType queryType) {
		this.dbmsType = dbmsType;
		this.targetType = targetType;
		this.queryType = queryType;
	}
	
	
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
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
	public int getTableIndex() {
		return tableIndex;
	}
	public void setTableIndex(int tableIndex) {
		this.tableIndex = tableIndex;
	}

	
}