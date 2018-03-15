/**
 * @ HttpPayloadFactory.java
 */
package logic;

import domain.HttpPayload;
import domain.UserInput;
import domain.enumeration.HttpMethod;
import domain.enumeration.HttpQueryType;
import logic.QueryMaker;

/**
 * <pre>
 * logic
 * HttpPayloadFactory.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/17
 */
public class HttpPayloadFactory {

	private QueryMaker queryMaker = null;
	
	public HttpPayloadFactory() {
		queryMaker = new QueryMaker();
	}
	
	public HttpPayload getHttpPayload(UserInput input){
		String query = queryMaker.getQuery(input);
		HttpPayload payload = new HttpPayload();
		
		String domain = input.getTargetURL();
		String url = "";
		if(input.getHttpQueryType() == HttpQueryType.GET_QUERY_ON_URL){
			System.out.println("Http Method Type 1 -> GET_QUERY_ON_URL");
			url = domain.replace("@{query}", query);
			payload.setHttpMethod(HttpMethod.GET);
		}
		else if(input.getHttpQueryType() == HttpQueryType.GET_QUERY_ON_PARAM){
			System.out.println("Http Method Type 2 -> GET_QUERY_ON_PARAM");
			//url = domain + "?" + input.getTargetParamName() + "=" +input.getTargetParamValue() + query;
			url = domain + "?query=%E8%B2%A9%E4%BF%83&submit=%E6%A4%9C%E7%B4%A2&" + 
					input.getTargetParamName() + "=" +input.getTargetParamValue() + query;
			payload.setHttpMethod(HttpMethod.GET);
		}
		else if(input.getHttpQueryType() == HttpQueryType.POST_QUERY_ON_PARAM){
			System.out.println("Http Method Type 3 -> POST_QUERY_ON_PARAM");
			url = domain;
			payload.setHttpMethod(HttpMethod.POST);
			payload.setParamName(input.getTargetParamName());
			payload.setParamValue(input.getTargetParamValue() + query);
		}
		url = url.replaceAll(" ","%20");
		url = url.replaceAll("#","%23");
		System.out.println("url in payload factory : " + url);
		payload.setUrl(url);

		return payload;
	}
	
}
