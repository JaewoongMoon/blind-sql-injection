/**
 * @ DbmsType.java
 */
package logic.domain.enumeration;

/**
 * <pre>
 * logic.domain
 * DbmsType.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public enum DbmsType {

	ORACLE(1),
	MY_SQL(2),
	MS_SQL(3)
	;
	
	private final int value;
	
	DbmsType(int value){
		this.value = value;
	}
	
	public int intValue(){
		return value;
	}
	
	public static DbmsType valueOf(int value){
		switch(value){
		case 1: return ORACLE;
		case 2: return MY_SQL;
		case 3: return MS_SQL;
		default : throw new AssertionError("Unknown value : " +value);
		}
	}
	
	public String getComment(){
		switch(value){
		case 1: return "--";
		case 2: return "#";
		case 3: return "--";
		default : throw new AssertionError("Unknown value : " +value);
		}
	}
}
