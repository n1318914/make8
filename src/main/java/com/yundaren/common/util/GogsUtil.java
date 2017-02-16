package com.yundaren.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.yundaren.common.constants.CommonConstants;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.SsoUserVo;

public class GogsUtil {
	public static GitHttpClient register(String mobile, SsoUserVo currentUser) {
		GitHttpClient gitHttpClient = new GitHttpClient();
		// 未注册关联
		String status = gitHttpClient.register(mobile, currentUser);	
		if (CommonConstants.REGISTER_SUCCESS.equals(status)) {
			return gitHttpClient;
		}		
		return null;
	}
	
	public static boolean transferRepoByAdmin(String consultMobile,String repo_name,String description){
		GitHttpClient gitHttpClient = new GitHttpClient();
		gitHttpClient.loginAdmin();
		String status = gitHttpClient.createRepo(repo_name, description);
		gitHttpClient.allocateRepo(consultMobile, repo_name);
		if(CommonConstants.CREATE_REPO_SUCCESS.equals(status)){
			return true;
		}
		return false;
	}
	
	public static GitHttpClient refreshLoginStatus(HttpServletRequest request,UserService userService) {
		SsoUserVo currentUser = CommonUtil.getCurrentLoginUser();
		String mobile = CommonUtil.getCurrentLoginUser().getUserInfoVo().getMobile();
		if(request.getParameter("url").indexOf("user/logout")>=0){
			request.getSession().removeAttribute("GOGS" + mobile);
		}
		if(StringUtils.isEmpty(mobile)){
			return null;
		}
		GitHttpClient gitHttpClient = (GitHttpClient) request.getSession().getAttribute("GOGS" + mobile);		
		if (gitHttpClient == null) {
			boolean isGogs = currentUser.getUserInfoVo().getIsGogs() == 1 ? true : false;
			if (!isGogs) {
				gitHttpClient = register(mobile, currentUser);
				if(gitHttpClient!=null){
					currentUser.getUserInfoVo().setIsGogs(1);
					userService.updateUserInfo(currentUser.getUserInfoVo());
				}
				request.getSession().setAttribute("GOGS" + mobile, gitHttpClient);
			}
			gitHttpClient = new GitHttpClient();
			String status = gitHttpClient.login(mobile, currentUser, true);
			if (!CommonConstants.LOGIN_SUCCESS.equals(status)) {
				if (CommonConstants.LOGIN_USER_NOT_EXIST.equals(status)) {
					return register(mobile, currentUser);
				}else{
					return null;	
				}
			}
			request.getSession().setAttribute("GOGS" + mobile, gitHttpClient);
		}
		return gitHttpClient;
	}
	
	public static boolean removeUserForProject(String mobile,String repo_user,String repo_name){
		GitHttpClient gitHttpClient = new GitHttpClient();
		gitHttpClient.loginAdmin();
		String id = gitHttpClient.getUserIdByName(mobile);
		if(id!=null){
			if(!id.trim().equals("")){
				String status = gitHttpClient.removeUserForProject(id, repo_user, repo_name);
				if(CommonConstants.COLLABORATION_DELETE.equals(status))
				return true;				
			}
			return false; 
		}
		return false;
	}
	
	private static boolean isInteger(String input){  
        if(input==null){
        	return false;
        }else if(input.trim().equals("")){
        	return false;
        }
        Matcher mer = Pattern.compile("^[0-9]+$").matcher(input);
        return mer.find();
    }
	
	public static boolean resetPassword(String mobile,String newPwd){
		GitHttpClient client = new GitHttpClient();
		String id = client.getUserIdByName(mobile);
		//System.out.println(id);
		if(isInteger(id)){
			if(Integer.parseInt(id)>=3){
				String page = client.loginAdmin();
				String status = client.resetPassword(id, mobile, newPwd);
				//System.out.println(status);
				if(CommonConstants.RESET_PASSWORD_SUCCESS.equals(status))return true;
			}
		}
		return false;
	}
	
	//修改用户信息
	public static boolean updateUserInfo(HttpServletRequest request,String name,String fullName,String email,String website,String location,String platName){
		GitHttpClient client = new GitHttpClient();
		String loginStatus = client.login(name, CommonUtil.getCurrentLoginUser(), true);
		if(CommonConstants.LOGIN_SUCCESS.equals(loginStatus))request.getSession().setAttribute("GOGS" + name,client);
		String status = client.updateUserInfo(name, fullName, email, website, location, platName);
		if(CommonConstants.UPDATE_INFO_SUCCESS.equals(status))return true;
		else return false;
	}
	
	//转移仓库所有者
	public static boolean transferRepoOwner(String repoName,String preOwner,String newOwner){
		GitHttpClient gitHttpClient = new GitHttpClient();
		gitHttpClient.loginAdmin();
		String result = gitHttpClient.transferRepoOwner(repoName, preOwner, newOwner);
		
		if(result == null || result.trim().equals("")){
			//System.out.println("transfer repository successfully!!!");
			return true;
		}
		
		return false;
	}
}
