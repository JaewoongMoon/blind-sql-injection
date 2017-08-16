/**
 * @ ProcessManager.java
 */
package logic;

import java.util.ArrayList;
import java.util.List;

import logic.domain.DbmsType;
import logic.domain.QueryType;
import logic.domain.TargetType;
import logic.http.HttpUrlResolver;
import logic.http.URLMaker;
import logic.sqlinjection.ResultResolver;
import logic.sqlinjection.SuccessDecider;
import logic.sqlinjection.query.QueryMaker;

/**
 * <pre>
 * logic.sqlinjection
 * ProcessManager.java 
 * </pre>
 *
 * @brief	: 전체 프로세스를 컨트롤하는 메서드. DB만 볼 것인지, Table만 볼 것인지. 혹은 전체를 진행할 것인지 등...
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class ProcessManager {

	private ResultResolver resultResolver;
	private String match;
	private String targetURL;
	private String targetParam;
	private String targetParamVal;
	
	
	public String getTargetParamVal() {
		return targetParamVal;
	}

	public void setTargetParamVal(String targetParamVal) {
		this.targetParamVal = targetParamVal;
	}

	public String getTargetParam() {
		return targetParam;
	}

	public void setTargetParam(String targetParam) {
		this.targetParam = targetParam;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}
	
	public ProcessManager() {
		resultResolver = new ResultResolver();
	}
	
	public int getDBCount(){
		return resultResolver.getDBCount(targetURL, match, 20, targetParam);
	}
	
	public List<Integer> getDBNameLength(int dbCnt){
		List<Integer> dbLengths = new ArrayList<Integer>();
		for (int i=0; i < dbCnt; i++){
			dbLengths.add(resultResolver.getDBNameLength(targetURL, match, i, 40, targetParam));
		}
		// show data
		for (int i=0; i < dbLengths.size(); i++){
			System.out.println((i+1) + "번 째 DB의 길이:" + dbLengths.get(i));
		}	
		return dbLengths;
	}
	
	public List<String> getDBNames(int dbCnt, List<Integer> dbLengths ){
		List<String> dbNames = new ArrayList<String>();
		for (int i=0; i < dbCnt; i++){
			dbNames.add(resultResolver.getDBName(targetURL, match, i, dbLengths.get(i), targetParam));
		}
		// show data
		for (int i=0; i < dbNames.size(); i++){
			System.out.println((i+1) + "번 째 DB의 이름:" + dbNames.get(i));
		}
		return dbNames;
	}
	
	public String getDBName(int targetDBIndex, int targetDBLength){
		return resultResolver.getDBName(targetURL, match, targetDBIndex, targetDBLength, targetParam);
	}
	
	public int getTableCount(String dbName){
		return resultResolver.getTableCount(targetURL, match, dbName, 50, targetParam);
	}


	public static void main(String[] args) {
		// GET 메서드중에서도 URL 자체가 변화하는 타입...
		String targetURL = "http://localhost:8088/unsafeweb/loginProcess.jsp";
		ProcessManager m = new ProcessManager();
		m.setTargetURL(targetURL);
		m.setTargetParam("userid");
		//m.setMatch("로그인하였습니다");
		m.setMatch("실패하였습니다"); //false 로 찾기..
		
		// 순서가 있다. DB개수 구하기 -> 각 DB명의 길이 구하기 -> 각 DB의 이름구하기 
		// TEST STEP 1. DB 개수 done
		int dbCnt = m.getDBCount();
		System.out.println("DB 개수는...: " + dbCnt);
		
		// TEST STEP 2. DB이름들의 길이
		List<Integer> dbLengths = m.getDBNameLength(dbCnt);
		
		// TEST STEP 3. DB이름
		List<String> dbNames = m.getDBNames(dbCnt, dbLengths);
		
		// TEST STEP 4. 테이블 개수
		//System.out.println(targetDB + " 테이블 개수 :" + m.getTableCount(targetDB));
		
	}
}
