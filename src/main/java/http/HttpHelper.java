package http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * @brief	: 
 * @author	: Jae-Woong Moon(mjw8585@gmail.com)
 * @Date	: 2017/08/16
 */
public class HttpHelper {

	private final String USER_AGENT = "Mozilla/5.0";
	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8082));
	
	public String send(HttpPayload payload){
		if(payload.getHttpMethod() == HttpMethod.GET){
			System.out.println("url : " + payload.getUrl());
			return sendGet(payload.getUrl());
		}
		else if (payload.getHttpMethod() == HttpMethod.POST){
			String postParam = payload.getParamName() + "=" + payload.getParamValue();
			return sendPost(payload.getUrl(), postParam);
		}
		else{
			System.out.println("Unkown Http Method!!!");
			return "None";
		}
	}
	
	public String sendGet(String targetURL){
		HttpURLConnection con = null;
		try{
			URL obj = new URL(targetURL);
			con = (HttpURLConnection) obj.openConnection(proxy); //proxy
			//con = (HttpURLConnection) obj.openConnection();  //default
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Cookie", "uid=CgABRVnn/qigtBsKA/TCAg==; CacheControl=1; CONCRETE5=hg8dk0o2pb8dj74d90n3ktvpn1; session_id=zpz6bvn44b41scraxdxs79vghr");
		    con.setRequestProperty("Authorization", "Basic aW5ub3ZhOndyaXRlcg==");
		    
			//con.setco
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
			 return "Exception :" + e.getMessage();
		}finally {
			if (con != null) {
		      con.disconnect();
		    }
		}

	}
	

	public String sendPost(String targetURL, String urlParameters) {
		  HttpURLConnection con = null;

		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    //connection = (HttpURLConnection) url.openConnection(); //default
		    con = (HttpURLConnection) url.openConnection(proxy); //proxy
		    con.setRequestMethod("POST");
		    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    con.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
		    con.setRequestProperty("Content-Language", "en-US");  
		    
		    con.setUseCaches(false);
		    con.setDoOutput(true);
		    //con.set

		    //Send request
		    DataOutputStream wr = new DataOutputStream (con.getOutputStream());
		    wr.writeBytes(urlParameters);
		    wr.close();

		    //Get Response  
		    InputStream is = con.getInputStream();
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
		    return "Exception :" + e.getMessage();
		  } finally {
		    if (con != null) {
		    	con.disconnect();
		    }
		  }
		}
}
