
package http;

import input.UserInput;
import query.QueryMaker;
import query.QueryParam;

/**
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/17
 */
public class HttpPayloadFactory {

	private QueryMaker queryMaker = null;
	
	public HttpPayloadFactory() {
		queryMaker = new QueryMaker();
	}
	
	public HttpPayload getHttpPayload(UserInput input, QueryParam param){
		String query = queryMaker.getQuery(input, param); 
		HttpPayload payload = new HttpPayload();
		
		String domain = input.getTargetURL();
		String url = "";
		if(input.getHttpMethod()  == HttpMethod.GET){
			url = domain + "?" + input.getTargetParamName() + "=" +input.getTargetParamValue() + query;
			payload.setHttpMethod(input.getHttpMethod());
		}
		else if(input.getHttpMethod() == HttpMethod.POST){
			url = domain;
			payload.setHttpMethod(input.getHttpMethod());
			payload.setParamName(input.getTargetParamName());
			payload.setParamValue(input.getTargetParamValue() + query);
		}
		url = url.replaceAll(" ","%20");
		url = url.replaceAll("#","%23");
		//System.out.println("url in payload factory : " + url);
		payload.setUrl(url);

		return payload;
	}
	
}
