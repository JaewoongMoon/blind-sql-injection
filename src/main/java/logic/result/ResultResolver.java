/**
 * @ ResultResolver.java
 */
package logic.result;

import java.util.List;

import logic.domain.enumeration.DbmsType;
import logic.domain.enumeration.HttpQueryType;
import logic.domain.QueryCondition;
import logic.domain.URLCondition;
import logic.domain.UserValueCondition;
import logic.domain.enumeration.QueryType;
import logic.domain.enumeration.TargetType;
import logic.query.QueryMaker;

/**
 * <pre>
 * logic.sqlinjection
 * ResultResolver.java 
 * </pre>
 *
 * @brief	: Make result by using SuccessDecider's deciding result.  
 * 	Responsible for making QueryCondition and URLCondition.
 * 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class ResultResolver {

	
	private ResultHelper helper = new ResultHelper();
	

	public int getDBCount(String targetURL, String match, int until, String targetParam, String targetParamVal){
		int cnt = 0;
		QueryCondition qc = new QueryCondition(DbmsType.MY_SQL, TargetType.DB_SCHEMA, QueryType.COUNT);
		
		URLCondition uc = new URLCondition();
		uc.setDomain(targetURL);
		uc.setHttpQueryType(HttpQueryType.GET_QUERY_ON_PARAM);
		uc.setParamName(targetParam);
		uc.setParamValue(targetParamVal);
		
		UserValueCondition cond = new UserValueCondition();
		cond.setCountUntil(until);
		cond.setMatch(match);
		
		cnt = helper.getCount(qc, uc, cond);
		return cnt;
	}
	
	
	public int getDBNameLength(String targetURL, String match,int targetDBIndex, int until, String targetParam, String targetParamVal){
		int length = 0;
		QueryCondition qc = new QueryCondition(DbmsType.MY_SQL, TargetType.DB_SCHEMA, QueryType.LENGTH);
		qc.setDbIndex(targetDBIndex);
		
		URLCondition uc = new URLCondition();
		uc.setDomain(targetURL);
		uc.setHttpQueryType(HttpQueryType.GET_QUERY_ON_PARAM);
		uc.setParamName(targetParam);
		uc.setParamValue(targetParamVal);
		
		UserValueCondition cond = new UserValueCondition();
		cond.setLengthUntil(until);
		cond.setMatch(match);
		
		length = helper.getLength(qc, uc, cond);
		return length;
	}
	
	public String getDBName(String targetURL, String match, int targetDBIndex, int targetDBNameLength, String targetParam, String targetParamVal){
		// 여기서부터는 이중 for 문이 필요 (i: DB이름의 index, 시도하고자 하는 문자의 index)
		String dbName = "";
		QueryCondition qc = new QueryCondition(DbmsType.MY_SQL, TargetType.DB_SCHEMA, QueryType.CONTENT);
		qc.setDbIndex(targetDBIndex);
		
		URLCondition uc = new URLCondition();
		uc.setDomain(targetURL);
		uc.setHttpQueryType(HttpQueryType.GET_QUERY_ON_PARAM);
		uc.setParamName(targetParam);
		uc.setParamValue(targetParamVal);
		
		UserValueCondition cond = new UserValueCondition();
		cond.setMatch(match);
		
		dbName = helper.getContent(qc, uc, cond, targetDBNameLength);
		/*
		for(int i=0; i < targetDBNameLength ; i++){
			qc.setDbNameIndex(i);
		}*/
		/*
			for(int j=33; j < 127; j++){ //(아스키 33~126까지 찾기)
				// STEP 1. 요청 URL 생성
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
		}*/
		
		return dbName;
	}
	
}
