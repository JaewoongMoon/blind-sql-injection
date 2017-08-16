/**
 * @ QueryMaker.java
 */
package logic.sqlinjection.query;

import logic.domain.DbmsType;
import logic.domain.QueryType;
import logic.domain.TargetType;

/**
 * <pre>
 * logic.sqlinjection.query
 * QueryMaker.java 
 * </pre>
 *
 * @brief	: 基本クエリーをもとにインジェクションクエリーを作るクラス
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
	public String getQuery(DbmsType dbmsType, TargetType targetType, QueryType queryType, String payload){
		String defaultQuery = getDefaultQuery(dbmsType, targetType, queryType);
		String query = "' and (" + defaultQuery +  " > " + payload + dbmsType.getComment();
		
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
				}
			}
		}
		return null;
	}
}
