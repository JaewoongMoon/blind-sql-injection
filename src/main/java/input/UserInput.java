package input;

import http.HttpMethod;

/**
 * @brief :
 * @author : Jae-Woong Moon(mjw8585@gmail.com)
 * @Date : 2017/09/05
 */
public class UserInput {

	private DbmsType dbmsType;
	private Step step;
	private HttpMethod httpMethod;
	private String targetURL;
	private String targetParamName;
	private String targetParamValue;
	private String etcParamStr;
	private String match;
	private String dbName;
	private String tableName;
	private String columnName;

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public DbmsType getDbmsType() {
		return dbmsType;
	}

	public void setDbmsType(DbmsType dbmsType) {
		this.dbmsType = dbmsType;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
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

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

}
