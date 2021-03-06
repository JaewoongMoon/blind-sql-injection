package query;

import input.DbmsType;
import input.Step;

/**
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
	public static final String MYSQL_TABLE_NAME_QUERY = "substring((select table_name from information_schema.tables where table_schema = '@{dbName}' limit @{tableIdx}, 1), @{tableNameIdx},1)";
	
	public static final String MYSQL_COLUMN_COUNT_QUERY = "(select count(*) from information_schema.columns where table_schema = '@{dbName}' and table_name = '@{tableName}' )";
	public static final String MYSQL_COLUMN_NAME_LENGTH_QUERY = "(select length(column_name) from information_schema.columns where table_schema = '@{dbName}' and table_name = '@{tableName}' limit @{columnIdx}, 1)";
	public static final String MYSQL_COLUMN_NAME_QUERY = "substring((select column_name from information_schema.columns where table_schema = '@{dbName}' and table_name = '@{tableName}' limit @{columnIdx}, 1), @{columnNameIdx},1)";
	
	public static final String MYSQL_DATA_COUNT_QUERY = "(select count(*) from @{dbName}.@{tableName})";
	public static final String MYSQL_DATA_CONTENT_LENGTH_QUERY = "(select length(@{columnName}) from @{dbName}.@{tableName} limit @{dataIdx}, 1)";
	public static final String MYSQL_DATA_CONTENT_QUERY = "substring((select @{columnName} from @{dbName}.@{tableName} limit @{dataIdx}, 1), @{dataContentIdx},1)";
	

	/* MS_SQL */
	public static final String MSSQL_DB_COUNT_QUERY = "(select count(*) from sys.databases)";
	//public static final String MSSQL_DB_LENGTH_QUERY = "(select len(name) from master..sysdatabases limit @{dbIdx}, 1)"; // 동작 x, mssql에서는 limit 를 사용할 수 없는 듯 하다. 
	// 아래 쿼리는 동작하지만 가장 위의 1건만을 조회한다. 
	public static final String MSSQL_DB_NAME_LENGTH_QUERY = "(select top 1 len(name) from (select top 1 name,dbid from master..sysdatabases order by name asc,dbid desc) as T order by name desc,dbid asc)"; 
	//public static final String MSSQL_DB_LENGTH_QUERY = "(select len(name) from (select top 1 name,dbid from master..sysdatabases order by name asc,dbid desc) as T limit @{dbIdx}, 1)";
	public static final String MSSQL_DB_NAME_QUERY = "()";
	
	
	public static String getDefaultQuery(DbmsType dbmsType, Step step, QueryType queryType){
		if(dbmsType == DbmsType.MY_SQL){
			
			if(step == Step.DB_SCHEMA){
				
				if(queryType == QueryType.COUNT){
					return MYSQL_DB_COUNT_QUERY;
				}else if(queryType == QueryType.LENGTH){
					return MYSQL_DB_NAME_LENGTH_QUERY;
				}else if(queryType == QueryType.CONTENT){
					return MYSQL_DB_NAME_QUERY;
				}
			}
			else if(step == Step.TABLE){
				if(queryType == QueryType.COUNT){
					return MYSQL_TABLE_COUNT_QUERY;
				}else if(queryType == QueryType.LENGTH){
					return MYSQL_TABLE_NAME_LENGTH_QUERY;
				}else if(queryType == QueryType.CONTENT){
					return MYSQL_TABLE_NAME_QUERY;
				}
			}
			else if(step == Step.COLUMN){
				if(queryType == QueryType.COUNT){
					return MYSQL_COLUMN_COUNT_QUERY;
				}else if(queryType == QueryType.LENGTH){
					return MYSQL_COLUMN_NAME_LENGTH_QUERY;
				}else if(queryType == QueryType.CONTENT){
					return MYSQL_COLUMN_NAME_QUERY;
				}
			}
			else if(step == Step.DATA){
				if(queryType == QueryType.COUNT){
					return MYSQL_DATA_COUNT_QUERY;
				}else if(queryType == QueryType.LENGTH){
					return MYSQL_DATA_CONTENT_LENGTH_QUERY;
				}else if(queryType == QueryType.CONTENT){
					return MYSQL_DATA_CONTENT_QUERY;
				}
			}
			
		}
		else if (dbmsType == DbmsType.MS_SQL){
			if(step == Step.DB_SCHEMA){
				
				if(queryType == QueryType.COUNT){
					return MSSQL_DB_COUNT_QUERY;
				}else if(queryType == QueryType.LENGTH){
					return MSSQL_DB_NAME_LENGTH_QUERY;
				}else if(queryType == QueryType.CONTENT){
					return MSSQL_DB_NAME_QUERY;
				}
			}
		}
		return "";
	}
}
