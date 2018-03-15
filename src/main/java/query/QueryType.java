package query;

/**
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public enum QueryType {

	COUNT,
	LENGTH,
	CONTENT //(=NAME)
	;
	
	public static QueryType getQueryType(String label){
		switch(label){
		case "갯수" : return COUNT;
		case "이름의 길이" : return LENGTH;
		case "내용" : return CONTENT;
		default : throw new AssertionError("Unknown value : " +label);
		}
	}
}
