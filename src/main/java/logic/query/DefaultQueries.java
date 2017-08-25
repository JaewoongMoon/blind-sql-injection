/**
 * @ Queries.java
 */
package logic.query;

/**
 * <pre>
 * logic
 * Queries.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class DefaultQueries {

	public static final String MYSQL_DB_COUNT_QUERY = "(select count(*) from information_schema.schemata)";
	public static final String MYSQL_DB_LENGTH_QUERY = "(select length(schema_name) from information_schema.schemata limit @{dbIdx},1)";
	public static final String MYSQL_DB_NAME_QUERY = "substring((select schema_name from information_schema.schemata limit @{dbIdx},1),@{dbNameIdx},1)"; // dbIdx 번째 DB 의 dbNameIdx 번째 캐릭터(알파벳)을 알아낸다.
	
	public static final String MYSQL_TABLE_COUNT_QUERY = "(select count(*) from information_schema.tables where table_schema='@{dbName}')";
	public static final String MYSQL_TABLE_LENGTH_QUERY = "(select length(table_name) from information_schema.tables where table_schema = '@{dbName}' limit @{tableIdx}, 1)";
	
}
