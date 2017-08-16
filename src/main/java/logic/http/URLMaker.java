/**
 * @ URLMaker.java
 */
package logic.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <pre>
 * logic.sqlinjection
 * URLMaker.java 
 * </pre>
 *
 * @brief	: make url parameter or url
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class URLMaker {
	
	
	public String getParameter(){
		return "";
	}
	
	public String getURL(String domain,String targetParam, String query){
		String url = "";
		if(domain.contains("@{query}")){
			url = domain.replace("@{query}", query);
		}else{
			url = domain + "?" + targetParam +"=admin" + query;
		}
		// url encoding
		/*
		try {
			url= URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		url = url.replaceAll(" ","%20");
		url = url.replaceAll("#","%23");
		return url;
	}
}
