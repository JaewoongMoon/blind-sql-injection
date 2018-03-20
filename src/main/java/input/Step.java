package input;

/**
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public enum Step {

	DB_SCHEMA,
	TABLE,
	COLUMN,
	DATA
	;
	
	public static Step getStep(String label){
		switch(label){
		case "DB" : return DB_SCHEMA;
		case "Table" : return TABLE;
		case "Column" : return COLUMN;
		case "Data" : return DATA;
		default : throw new AssertionError("Unknown value : " +label);
		}
	}
}
