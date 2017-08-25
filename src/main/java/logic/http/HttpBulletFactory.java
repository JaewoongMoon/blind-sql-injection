/**
 * @ HttpBulletFactory.java
 */
package logic.http;

import logic.domain.HttpBullet;
import logic.domain.QueryCondition;
import logic.domain.URLCondition;
import logic.domain.enumeration.HttpMethod;
import logic.domain.enumeration.HttpQueryType;
import logic.query.QueryMaker;

/**
 * <pre>
 * logic.query
 * HttpBulletFactory.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/17
 */
public class HttpBulletFactory {

	private QueryMaker queryMaker = null;
	
	/**
	 * 
	 */
	public HttpBulletFactory() {
		queryMaker = new QueryMaker();
	}
	
	public HttpBullet getHttpBullet(QueryCondition qc, URLCondition uc){
		String query = getQuery(qc);
		HttpBullet bullet = new HttpBullet();
		
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
