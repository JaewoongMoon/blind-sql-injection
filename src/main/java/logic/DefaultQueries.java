/**
 * @ Queries.java
 */
package logic;

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

	/* MY_SQL */
	public static final String MYSQL_DB_COUNT_QUERY = "(select count(*) from information_schema.schemata)";
	public static final String MYSQL_DB_NAME_LENGTH_QUERY = "(select length(schema_name) from information_schema.schemata limit @{dbIdx},1)";
	public static final String MYSQL_DB_NAME_QUERY = "substring((select schema_name from information_schema.schemata limit @{dbIdx},1),@{dbNameIdx},1)"; // dbIdx 번째 DB 의 dbNameIdx 번째 캐릭터(알파벳)을 알아낸다.
	
	public static final String MYSQL_TABLE_COUNT_QUERY = "(select count(*) from information_schema.tables where table_schema='@{dbName}')";
	public static final String MYSQL_TABLE_NAME_LENGTH_QUERY = "(select length(table_name) from information_schema.tables where table_schema = '@{dbName}' limit @{tableIdx}, 1)";
	public static final String MYSQL_TABLE_NAME_QUERY = "substring((select length(table_name) from information_schema.tables where table_schema = '@{dbName}' limit @{tableIdx}, 1), @{tableNameIdx},1)";
	
	

	/* MS_SQL */
	public static final String MSSQL_DB_COUNT_QUERY = "(select count(*) from sys.databases)";
	//public static final String MSSQL_DB_LENGTH_QUERY = "(select len(name) from master..sysdatabases limit @{dbIdx}, 1)"; // 동작 x, mssql에서는 limit 를 사용할 수 없는 듯 하다. 
	// 아래 쿼리는 동작하지만 가장 위의 1건만을 조회한다. 
	public static final String MSSQL_DB_NAME_LENGTH_QUERY = "(select top 1 len(name) from (select top 1 name,dbid from master..sysdatabases order by name asc,dbid desc) as T order by name desc,dbid asc)"; 
	//public static final String MSSQL_DB_LENGTH_QUERY = "(select len(name) from (select top 1 name,dbid from master..sysdatabases order by name asc,dbid desc) as T limit @{dbIdx}, 1)";
	public static final String MSSQL_DB_NAME_QUERY = "()";
}
