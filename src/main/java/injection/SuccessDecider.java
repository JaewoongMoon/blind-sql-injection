package injection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @brief	: 웹으로 쿼리를 전송하고, 결과를 받아서 성공(true)인지 실패인지(false)여부를 판단한 결과를 리턴해준다.
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class SuccessDecider {

	
	public boolean isSuccess(String input, String match){
		//System.out.println("=== isSucess > input : " + input + ", and match : " + match);
		//Pattern p = Pattern.compile(match);
		//Matcher m = p.matcher(input);
		//System.out.println(m.find());
		//return m.find();
		return input.contains(match);
	}
}
