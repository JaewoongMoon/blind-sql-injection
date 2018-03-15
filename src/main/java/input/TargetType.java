package input;

/**
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public enum TargetType {

	DB_SCHEMA,
	TABLE,
	COLUMN,
	DATA
	;
	
	public static TargetType getTargetType(String label){
		switch(label){
		case "DB" : return DB_SCHEMA;
		case "Table" : return TABLE;
		case "Column" : return COLUMN;
		case "Data" : return DATA;
		default : throw new AssertionError("Unknown value : " +label);
		}
	}
}
