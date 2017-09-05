/**
 * @ HttpQueryType.java
 */
package domain.enumeration;

/**
 * <pre>
 * logic.domain
 * HttpQueryType.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/17
 */
public enum HttpQueryType {

	GET_QUERY_ON_URL(1),
	GET_QUERY_ON_PARAM(2),
	POST_QUERY_ON_PARAM(3)
	;
	
	private final int value;
	
	HttpQueryType(int value){
		this.value = value;
	}
	
	public static HttpQueryType valueOf(int value){
		switch(value){
		case 1: return GET_QUERY_ON_URL;
		case 2: return GET_QUERY_ON_PARAM;
		case 3: return POST_QUERY_ON_PARAM;
		default : throw new AssertionError("Unknown value : " +value);
		}
	}
	
}
