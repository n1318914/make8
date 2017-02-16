package com.yundaren.common.util;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.yundaren.common.constants.CommonConstants;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.user.vo.SsoUserVo;

public class GitHttpClient{
	// 创建CookieStore实例
	CookieStore cookieStore = null;
	HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL = DomainConfig.getGogsBaseUrl();
	static HttpPost postRequest = null;
	static HttpGet getRequest = null;
	public  String doHttpPost(String url, Map<String, String> parameter) {
		try {
			if (StringUtils.isBlank(url) || null == parameter) {
				return null;
			}

			buiderParameterMap(url, parameter);
			List<NameValuePair> parameterArray = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = parameter.entrySet();
			String[] parameterNames = new String[parameter.size()];
			int i = 0;
			for (Entry<String, String> entry : entrySet) {
				parameterArray.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				parameterNames[i] = entry.getKey();
				i++;
			}
			//abortAllRequest();
			postRequest = new HttpPost();
			postRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
//			request.setHeader("JESSIONID", SID);;
			postRequest.setURI(URI.create(url));
			// 缁戝畾鍒拌姹� Entry
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameterArray, HTTP.UTF_8);
			postRequest.setEntity(entity);
			// 鍙戦�佽姹�
			
			HttpResponse httpResponse = httpClient.execute(postRequest);

			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			return retSrc;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public  String doHttpGet(String url, Map<String, String> params) {
		try {

			if (StringUtils.isBlank(url) || params == null)
				return null;
			buiderParameterMap(url, params);

			StringBuffer sbParam = new StringBuffer();
			Set<Entry<String, String>> entrySet = params.entrySet();
			String[] parameterNames = new String[params.size()];
			int i = 0;
			for (Entry<String, String> entry : entrySet) {
				sbParam.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
				parameterNames[i] = entry.getKey();
				i++;
			}
			//abortAllRequest();
			getRequest = new HttpGet(url + (url.contains("?") ? "&" : "?") + sbParam);

			HttpResponse httpResponse = httpClient.execute(getRequest);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());

			return retSrc;
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return url;
	}
	public static void abortAllRequest(){
		if(postRequest!=null)postRequest.abort();
		if(getRequest!=null)getRequest.abort();
	}
	public static void buiderParameterMap(String url, Map<String, String> params) {
		String str[] = url.split("\\?");
		if (str.length > 1) {
			String parameter = str[1];
			String parameters[] = parameter.split("&");
			for (String pstr : parameters) {
				String key = "";
				String value = "";
				String keyValue[] = pstr.split("=");
				key = keyValue[0];
				if(keyValue.length==1)value = "";
				else value = keyValue[1];
				params.put(key ,value);
			}
		}
	}
	private String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }    
	
	
	/**页面API**/
	public String login(String mobile,SsoUserVo currentUser,boolean isInternal){
		Map map =new HashMap<String,String>();
		map.put("user_name", mobile);
		map.put("password", currentUser.getPassword());
		map.put("is_internal",isInternal+"");
		return doHttpPost(BASE_URL+"/user/login", map);
	}
		
	/**页面API**/
	public String loginAdmin(){
		Map map =new HashMap<String,String>();
		map.put("user_name", DomainConfig.getGogsAdmin());
		map.put("password", DomainConfig.getGogsAdminPwd());
		map.put("is_internal",true+"");
		return doHttpPost(BASE_URL+"/user/login", map);
	}
	
	public String register(String mobile,SsoUserVo currentUser){
		Map map =new HashMap<String,String>();
		String email = currentUser.getUserInfoVo().getEmail()==null?currentUser.getUserInfoVo().getMobile()+"@gogs.com":currentUser.getUserInfoVo().getEmail();
		//String passWord = getRandomString(16);
		String passWord = currentUser.getPassword();
		map.put("user_name", mobile);
		map.put("email", email);
		map.put("password", MD5Util.encodeByMD5(passWord));
		map.put("retype", MD5Util.encodeByMD5(passWord));
		return doHttpPost(BASE_URL+"/user/sign_up", map);		
	}
	
	/**创建仓库**/
	public String createRepo(String repo_name,String description){
		Map map =new HashMap<String,String>();
		map.put("_csrf", "IcnwKBZpGd6FS2iwsNlnUnbWmsA6MTQ1ODYzMzExODc0MTA0Njg2OQ==");
		map.put("uid", "1");
		map.put("repo_name", repo_name);
		map.put("private", "on");
		map.put("description", description);
		map.put("readme", "Default");
		map.put("auto_init", "on");
		return doHttpPost(BASE_URL+"/repo/create", map);		
	}
	/**分配仓库**/
	public String allocateRepo(String mobile,String repo_name){
		Map map =new HashMap<String,String>();
		String transferUrl = DomainConfig.getGogsAdmin()+"/"+repo_name+"/settings";
		map.put("_csrf", "IcnwKBZpGd6FS2iwsNlnUnbWmsA6MTQ1ODYzMzExODc0MTA0Njg2OQ==");
		map.put("action", "transfer");
		map.put("repo_name", repo_name);
		map.put("new_owner_name", mobile);		
		return doHttpPost(BASE_URL+transferUrl, map);
	}
	
	/**页面跳转**/
	public String viewPage(String path){
		Map map =new HashMap<String,String>();
		return doHttpGet(BASE_URL+path, map);
	}
	
	public boolean addUserForProject(String mobile,String repo_user,String repo_name){
		String path = "/"+repo_user+"/"+repo_name+"/settings/collaboration";
		Map map =new HashMap<String,String>();
		map.put("_csrf", "3gXWp1_9A588xl1cJE_l-BrNCHs6MTQ1ODcxMDU4MzU2NzA2MDU0Nw==");
		map.put("collaborator", mobile);
		String status =  doHttpPost(BASE_URL+path, map);
		if(CommonConstants.COLLABORATION_SUCCESS.equals(status)){
			return true;
		}
		return false;
	}
	
	public String removeUserForProject(String id,String repo_user,String repo_name){
		String path = "/"+repo_user+"/"+repo_name+"/settings/collaboration/delete";
		Map map =new HashMap<String,String>();
		map.put("_csrf", "49XGHmjLg0EX-Byp0f7czOXYM6U6MTQ1ODcxNDkwODk5ODk3MDAwMg==");
		map.put("id", id);
		return doHttpPost(BASE_URL+path, map);
	}
	
	public  String getUserIdByName(String name){
		Map map =new HashMap<String,String>();
		map.put("_csrf", "49XGHmjLg0EX-Byp0f7czOXYM6U6MTQ1ODcxNDkwODk5ODk3MDAwMg==");
		map.put("user_name", name);
		map.put("password", DomainConfig.getGogsAdminPwd());
		map.put("is_internal",true+"");
		return doHttpPost(BASE_URL+"/info/getid", map);
	}
	
	public  String resetPassword(String id,String mobile,String newPwd){
		Map map = new HashMap<String, String>();
		map.put("_csrf", "49XGHmjLg0EX-Byp0f7czOXYM6U6MTQ1ODcxNDkwODk5ODk3MDAwMg==");
		map.put("email", mobile + "@gogs.com");
		map.put("password", newPwd);
		map.put("login_type", "0-0");
		map.put("max_repo_creation", "-1");
		map.put("active", "on");
		return doHttpPost(BASE_URL + "/admin/users/"+id, map);
	}
	
	public String updateUserInfo(String name,String fullName,String email,String website,String location,String platName){
		Map map = new HashMap<String, String>();
		//map.put("_csrf", "49XGHmjLg0EX-Byp0f7czOXYM6U6MTQ1ODcxNDkwODk5ODk3MDAwMg==");
		map.put("name", name);
		map.put("full_name", fullName);
		map.put("email", email);
		map.put("website", website);
		map.put("location", location);
		map.put("plat_name", platName);
		return doHttpPost(BASE_URL + "/user/settings", map);
	}
	
	public String transferRepoOwner(String repoName,String preOwner,String newOwner){
		Map map = new HashMap<String,String>();
		
		String transferUrl = "/" + preOwner +"/" + repoName + "/settings";
		map.put("_csrf","9VcKx2DJpXHREgG8MXT5r9ylTeI6MTQ3MDIyNjA2MjMzMTE3NzEwMA==");
		map.put("action", "transfer");
		map.put("repo_name", repoName);
		map.put("new_owner_name",newOwner);		
		
		return doHttpPost(BASE_URL + transferUrl, map);
	}
}
