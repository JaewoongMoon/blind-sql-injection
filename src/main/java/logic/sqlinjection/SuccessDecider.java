/**
 * @ SuccessDecider.java
 */
package logic.sqlinjection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * logic.sqlinjection
 * SuccessDecider.java 
 * </pre>
 *
 * @brief	: 웹으로 쿼리를 전송하고, 결과를 받아서 성공(true)인지 실패인지(false)여부를 판단한 결과를 리턴해준다.
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class SuccessDecider {

	
	public boolean isSuccess(String input, String match){
		Pattern p = Pattern.compile(match);
		Matcher m = p.matcher(input);
		return !m.find();
	}
}
