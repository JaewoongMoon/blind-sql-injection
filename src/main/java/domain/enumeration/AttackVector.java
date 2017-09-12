/**
 * @ AttackVector.java
 */
package domain.enumeration;

import logic.DefaultQueries;

/**
 * <pre>
 * domain.enumeration
 * AttackVector.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/05
 */
public enum AttackVector {
	
	MYSQL_DB_COUNT(1),
	MYSQL_DB_NAME_LENGTH(2),
	MYSQL_DB_NAME(3),
	MYSQL_TABLE_COUNT(4),
	MYSQL_TABLE_NAME_LENGTH(5),
	MYSQL_TABLE_NAME(6),
	MYSQL_COLUMN_COUNT(7),
	MYSQL_COLUMN_NAME_LENGTH(8),
	MYSQL_COLUMN_NAME(9),
	MYSQL_DATA_COUNT(10),
	MYSQL_DATA_CONTENT_LENGTH(11),
	MYSQL_DATA_CONTENT(12),
	
	MSSQL_DB_COUNT(13),
	MSSQL_DB_NAME_LENGTH(14),
	MSSQL_DB_NAME(15),
	MSSQL_TABLE_COUNT(16),
	MSSQL_TABLE_NAME_LENGTH(17),
	MSSQL_TABLE_NAME(18),
	MSSQL_COLUMN_COUNT(19),
	MSSQL_COLUMN_NAME_LENGTH(20),
	MSSQL_COLUMN_NAME(21),
	MSSQL_DATA_COUNT(22),
	MSSQL_DATA_CONTENT_LENGTH(23),
	MSSQL_DATA_CONTENT(24)
	;
	
	private final int value;
	
	AttackVector(int value){
		this.value = value;
	}
	
	public int intValue(){
		return value;
	}
	
	/**
	 * @Method 	: getAttackVector
	 * @brief	: 유저가 선택한 값에 따른 AttackVector 판정
	 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
	 * @Date	: 2017/09/05
	 * @param dbmsType
	 * @param targetType
	 * @param queryType
	 * @return
	 */
	public static AttackVector getAttackVector(DbmsType dbmsType, TargetType targetType, QueryType queryType){
		if(dbmsType == DbmsType.MY_SQL){
			
			if(targetType == TargetType.DB_SCHEMA){
				
				if(queryType == QueryType.COUNT){
					return MYSQL_DB_COUNT;
				}else if(queryType == QueryType.LENGTH){
					return MYSQL_DB_NAME_LENGTH;
				}else if(queryType == QueryType.CONTENT){
					return MYSQL_DB_NAME;
				}
			}
			else if(targetType == TargetType.TABLE){
				if(queryType == QueryType.COUNT){
					return MYSQL_TABLE_COUNT;
				}else if(queryType == QueryType.LENGTH){
					return MYSQL_TABLE_NAME_LENGTH;
				}else if(queryType == QueryType.CONTENT){
					return MYSQL_TABLE_NAME;
				}
			}
			else if(targetType == TargetType.COLUMN){
				if(queryType == QueryType.COUNT){
					return MYSQL_COLUMN_COUNT;
				}else if(queryType == QueryType.LENGTH){
					return MYSQL_COLUMN_NAME_LENGTH;
				}else if(queryType == QueryType.CONTENT){
					return MYSQL_COLUMN_NAME;
				}
			}
			else if(targetType == TargetType.DATA){
				if(queryType == QueryType.COUNT){
					return MYSQL_DATA_COUNT;
				}else if(queryType == QueryType.LENGTH){
					return MYSQL_DATA_CONTENT_LENGTH;
				}else if(queryType == QueryType.CONTENT){
					return MYSQL_DATA_CONTENT;
				}
			}
			
		}
		else if (dbmsType == DbmsType.MS_SQL){
			if(targetType == TargetType.DB_SCHEMA){
				
				if(queryType == QueryType.COUNT){
					return MSSQL_DB_COUNT;
				}else if(queryType == QueryType.LENGTH){
					return MSSQL_DB_NAME_LENGTH;
				}else if(queryType == QueryType.CONTENT){
					return MSSQL_DB_NAME;
				}
			}
		}
		return null;
	}
	
	public static String getDefaultQuery(AttackVector attackVector){
		switch(attackVector){
		case MYSQL_DB_COUNT : return DefaultQueries.MYSQL_DB_COUNT_QUERY;
		case MYSQL_DB_NAME_LENGTH: return DefaultQueries.MYSQL_DB_NAME_LENGTH_QUERY;
		case MYSQL_DB_NAME: return DefaultQueries.MYSQL_DB_NAME_QUERY;
		case MYSQL_TABLE_COUNT: return DefaultQueries.MYSQL_TABLE_COUNT_QUERY;
		case MYSQL_TABLE_NAME_LENGTH: return DefaultQueries.MYSQL_TABLE_NAME_LENGTH_QUERY;
		case MYSQL_TABLE_NAME: return DefaultQueries.MYSQL_TABLE_NAME_QUERY;
		case MYSQL_COLUMN_COUNT: return DefaultQueries.MYSQL_COLUMN_COUNT_QUERY;
		case MYSQL_COLUMN_NAME_LENGTH: return DefaultQueries.MYSQL_COLUMN_NAME_LENGTH_QUERY;
		case MYSQL_COLUMN_NAME: return DefaultQueries.MYSQL_COLUMN_NAME_QUERY;
		case MYSQL_DATA_COUNT: return DefaultQueries.MYSQL_DATA_COUNT_QUERY;
		case MYSQL_DATA_CONTENT_LENGTH: return DefaultQueries.MYSQL_DATA_CONTENT_LENGTH_QUERY;
		case MYSQL_DATA_CONTENT: return DefaultQueries.MYSQL_DATA_CONTENT_QUERY;
		
		default : throw new AssertionError("Unknown value : " + attackVector); 
		}
	}
}
