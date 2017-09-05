/**
 * @ BlindSQLInjectionManager.java
 */
package logic;

import java.util.List;

import domain.UserInput;

/**
 * <pre>
 * logic
 * BlindSQLInjectionManager.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/09/05
 */
public interface IBlindSQLInjectionManager {

	// db
	public int getDBCount(UserInput input);
	/*public int getDBLength(UserInput input);
	public List<Integer> getDBLengths(UserInput input);
	public String getDBName(UserInput input);
	public List<String> getDBNames(UserInput input);*/
	
	// table
	//public int getTableCount(UserInput input);
}
