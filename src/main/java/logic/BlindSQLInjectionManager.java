/**
 * @ BlindSQLInjectionManager.java
 */
package logic;

import domain.HttpPayload;
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
public class BlindSQLInjectionManager implements IBlindSQLInjectionManager{

	private SuccessDecider decider = null;
	private HttpHelper HttpHelper = null;
	private HttpPayloadFactory factory = null;
	
	/**
	 * 
	 */
	public BlindSQLInjectionManager() {
		decider = new SuccessDecider();
		HttpHelper = new HttpHelper();
		factory = new HttpPayloadFactory();
	}
	
	/**
	 * @Method 	: getDBCount
	 * @brief	: 
	 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
	 * @Date	: 2017/09/05
	 * @param input
	 * @return
	 */
	@Override
	public int getDBCount(UserInput input) {
		int cnt = 0;
		for(int i=0; i < input.getCountUntil(); i++){
			input.setCheckVal(i+"");
			HttpPayload payload = factory.getHttpPayload(input);
			String res = HttpHelper.send(payload);
			if(decider.isSuccess(res, input.getMatch())){
				cnt = i;
				break;
			}
		}
		return cnt;
	}
}
