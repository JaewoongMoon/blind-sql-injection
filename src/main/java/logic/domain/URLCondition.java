/**
 * @ URLCondition.java
 */
package logic.domain;

import logic.domain.enumeration.HttpMethod;
import logic.domain.enumeration.HttpQueryType;

/**
 * <pre>
 * logic.domain
 * URLCondition.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/17
 */
public class URLCondition {

	private String domain;
	private String paramName;
	private String paramValue;
	private HttpQueryType httpQueryType;
	
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public HttpQueryType getHttpQueryType() {
		return httpQueryType;
	}
	public void setHttpQueryType(HttpQueryType httpQueryType) {
		this.httpQueryType = httpQueryType;
	}

	
}
