/**
 * @ QueryMaker.java
 */
package logic;

import domain.enumeration.AttackVector;
import domain.enumeration.DbmsType;
import domain.UserInput;

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
	public String getQuery(UserInput cond){
		final DbmsType dbmsType = cond.getDbmsType();
		final String checkVal = cond.getCheckVal();
		final int dbIndex = cond.getDbIndex();
		final int dbNameIndex = cond.getDbNameIndex();
		final String dbName = cond.getDbName();
		
		// STEP 1. get default query
		String defaultQuery = AttackVector.getDefaultQuery(cond.getAttackVector());
		
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

}
