
package query;

import input.UserInput;

/**
 * @brief	: Make query string based on default query string
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class QueryMaker {

	/**
	 * @Method 	: getQuery
	 * @brief	: 
	 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
	 * @Date	: 2017/08/16
	 * @return
	 */
	public String getQuery(UserInput input, QueryParam param){
		
		// STEP 1. get default query
		String defaultQuery = DefaultQueries.getDefaultQuery(input.getDbmsType(), param.getStep(), param.getQueryType());
		
		// STEP 2. get replaced query
		String replacedQuery = defaultQuery;
		final int dbIndex = param.getDbIndex();
		if(dbIndex > -1){
			replacedQuery = replacedQuery.replace("@{dbIdx}", dbIndex+"");
		}
		final int dbNameIndex = param.getDbNameIndex();
		if(dbNameIndex > -1){
			replacedQuery = replacedQuery.replace("@{dbNameIdx}", dbNameIndex+"");
		}
		final String dbName = param.getDbName();
		if(dbName != null && !dbName.equals("")){
			replacedQuery = replacedQuery.replace("@{dbName}", dbName);
		}
		final int tableIndex = param.getTableIndex();
		if(tableIndex > -1){
			replacedQuery = replacedQuery.replace("@{tableIdx}", tableIndex+"");
		}
		final int tableNameIndex = param.getTableNameIndex();
		if(tableNameIndex > -1){
			replacedQuery = replacedQuery.replace("@{tableNameIdx}", tableNameIndex+"");
		}
		final String tableName = param.getTableName();
		if(tableName != null && !tableName.equals("")){
			replacedQuery = replacedQuery.replace("@{tableName}", tableName);
		}
		
		// STEP 3. complete query
		final String checkVal = param.getCheckVal();
		String query = "' and (" + replacedQuery  +"=" + checkVal + ")" +input.getDbmsType().getComment();
		//String query = " and (" + replacedQuery  +"=" + checkVal + ")" +dbmsType.getComment();
		return query;
	}

}
