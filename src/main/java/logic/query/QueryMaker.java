/**
 * @ QueryMaker.java
 */
package logic.query;

import logic.domain.enumeration.DbmsType;
import logic.domain.QueryCondition;
import logic.domain.enumeration.QueryType;
import logic.domain.enumeration.TargetType;

/**
 * <pre>
 * logic.sqlinjection.query
 * QueryMaker.java 
 * </pre>
 *
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
	 * @param dbmsType
	 * @param targetType
	 * @param queryType
	 * @param payload
	 * @return
	 */
	public String getQuery(QueryCondition cond){
		final DbmsType dbmsType = cond.getDbmsType();
		final TargetType targetType = cond.getTargetType();
		final QueryType queryType = cond.getQueryType();
		final String checkVal = cond.getCheckVal();
		final int dbIndex = cond.getDbIndex();
		final int dbNameIndex = cond.getDbNameIndex();
		final String dbName = cond.getDbName();
		
		// STEP 1. get default query
		String defaultQuery = getDefaultQuery(dbmsType, targetType, queryType);
		
		// STEP 2. replace query index (LIMIT)
		String replacedQuery = defaultQuery;
		if(dbIndex > -1){
			replacedQuery = replacedQuery.replace("@{dbIdx}", dbIndex+"");
		}
		if(dbNameIndex > -1){
			replacedQuery = replacedQuery.replace("@{dbNameIdx}", dbNameIndex+"");
		}
		if(dbName != null && !dbName.equals("")){
			replacedQuery = replacedQuery.replace("@{dbName}", dbName);
		}
		
		// STEP 3. complete query
		String query = "' and (" + replacedQuery  +" = " + checkVal + ")" +dbmsType.getComment();
		return query;
	}
	
	/**
	 * @Method 	: getDefaultQuery
	 * @brief	: 
	 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
	 * @Date	: 2017/08/16
	 * @param dbmsType
	 * @param targetType
	 * @param queryType
	 * @return
	 */
	private String getDefaultQuery(DbmsType dbmsType, TargetType targetType, QueryType queryType){
		if(dbmsType == DbmsType.MY_SQL){
			
			if(targetType == TargetType.DB_SCHEMA){
				
				if(queryType == QueryType.COUNT){
					return DefaultQueries.MYSQL_DB_COUNT_QUERY;
				}else if(queryType == QueryType.LENGTH){
					return DefaultQueries.MYSQL_DB_LENGTH_QUERY;
				}else if(queryType == QueryType.CONTENT){
					return DefaultQueries.MYSQL_DB_NAME_QUERY;
				}
			}
			else if(targetType == TargetType.TABLE){
				if(queryType == QueryType.COUNT){
					return DefaultQueries.MYSQL_TABLE_COUNT_QUERY;
				}else if(queryType == QueryType.LENGTH){
					return DefaultQueries.MYSQL_TABLE_LENGTH_QUERY;
				}
			}
		}
		return null;
	}
}
