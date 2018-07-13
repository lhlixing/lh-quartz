package com.lh.weixin;

import java.io.IOException;
 
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
 
public class AccessTokenUtil {
	
	public static final String APPID = "你的APPID";
	
	public static final String APPSECRET = "你的APPSECRET";
	
	/**全局token 所有与微信有交互的前提 */
	public static String ACCESS_TOKEN;
	
	/**全局token上次获取事件 */
	public static long LASTTOKENTIME;
	
	/**
	 * 获取全局token方法
	 * 该方法通过使用HttpClient发送http请求，HttpGet()发送请求
	 * 微信返回的json中access_token是我们的全局token
	 */
	public static synchronized void getAccess_token(){
		if(ACCESS_TOKEN == null || System.currentTimeMillis() - LASTTOKENTIME > 7000*1000){
			try {
				//请求access_token地址
				String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx9c3336b0bdb29172&secret=e22ea5453c6c10326045a00112c873f4";
				//创建提交方式
				HttpGet httpGet = new HttpGet(url);
				//获取到httpclien
				HttpClient httpClient = new DefaultHttpClient();
				//发送请求并得到响应
				HttpResponse response = httpClient.execute(httpGet);
				//判断请求是否成功
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					//将得到的响应转为String类型
					String str = EntityUtils.toString(response.getEntity(), "utf-8");
					//字符串转json
					JSONObject jsonObject = new JSONObject(str);
					//输出access_token
					System.out.println((String) jsonObject.get("access_token"));
					//给静态变量赋值，获取到access_token
					ACCESS_TOKEN = (String) jsonObject.get("access_token");
					//给获取access_token时间赋值，方便下此次获取时进行判断
					LASTTOKENTIME = System.currentTimeMillis();
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		getAccess_token();
	}
}
