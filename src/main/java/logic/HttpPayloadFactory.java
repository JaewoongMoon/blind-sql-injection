/**
 * @ HttpPayloadFactory.java
 */
package logic;

import domain.HttpPayload;
import domain.QueryCondition;
import domain.URLCondition;
import domain.enumeration.HttpMethod;
import domain.enumeration.HttpQueryType;
import logic.QueryMaker;

/**
 * <pre>
 * logic.query
 * HttpPayloadFactory.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/17
 */
public class HttpPayloadFactory {

	private QueryMaker queryMaker = null;
	
	/**
	 * 
	 */
	public HttpPayloadFactory() {
		queryMaker = new QueryMaker();
	}
	
	public HttpPayload getHttpPayload(QueryCondition qc, URLCondition uc){
		String query = getQuery(qc);
		HttpPayload bullet = new HttpPayload();
		
		String domain = uc.getDomain();
		String url = "";
		if(uc.getHttpQueryType() == HttpQueryType.GET_QUERY_ON_URL){ 
			url = domain.replace("@{query}", query);
			bullet.setHttpMethod(HttpMethod.GET);
		}
		else if(uc.getHttpQueryType() == HttpQueryType.GET_QUERY_ON_PARAM){
			url = domain + "?" + uc.getParamName() + "=" +uc.getParamValue() + query;
			bullet.setHttpMethod(HttpMethod.GET);
		}
		else if(uc.getHttpQueryType() == HttpQueryType.POST_QUERY_ON_PARAM){
			url = domain;
			bullet.setHttpMethod(HttpMethod.POST);
			bullet.setParamName(uc.getParamName());
			bullet.setParamValue(uc.getParamValue() + query);
		}
		url = url.replaceAll(" ","%20");
		url = url.replaceAll("#","%23");
		bullet.setUrl(url);

		return bullet;
	}
	
	private String getQuery(QueryCondition cond){
		return queryMaker.getQuery(cond);
	}
	
}
