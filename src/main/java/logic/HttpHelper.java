/**
 * @ HttpHelper.java
 */
package logic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import domain.HttpPayload;
import domain.enumeration.HttpMethod;

/**
 * <pre>
 * logic.http
 * HttpHelper.java 
 * </pre>
 *
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class HttpHelper {

	private final String USER_AGENT = "Mozilla/5.0";
	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8082));
	
	public String send(HttpPayload payload){
		if(payload.getHttpMethod() == HttpMethod.GET){
			return sendGet(payload.getUrl());
		}else if (payload.getHttpMethod() == HttpMethod.POST){
			String postParam = payload.getParamName() + "=" + payload.getParamValue();
			return sendPost(payload.getUrl(), postParam);
		}else{
			System.out.println("Unkown Http Method!!!");
			return "None";
		}
	}
	
	public String sendGet(String targetURL){
		HttpURLConnection con = null;
		try{
			URL obj = new URL(targetURL);
			con = (HttpURLConnection) obj.openConnection(proxy);
			//con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
	
			//int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + targetURL);
			//System.out.println("Response Code : " + responseCode);
	
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		}catch(Exception e){
			 e.printStackTrace();
			 return null;
		}finally {
			if (con != null) {
		      con.disconnect();
		    }
		}

	}
	

	public String sendPost(String targetURL, String urlParameters) {
		  HttpURLConnection connection = null;

		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    //connection = (HttpURLConnection) url.openConnection();
		    connection = (HttpURLConnection) url.openConnection(proxy);
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
		    connection.setRequestProperty("Content-Language", "en-US");  
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
		    wr.writeBytes(urlParameters);
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
}
