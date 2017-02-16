package com.yundaren.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.yundaren.user.biz.UserExperienceBiz;
import com.yundaren.user.service.UserExperienceService;
import com.yundaren.user.vo.AbstractEmployeeVo;
import com.yundaren.user.vo.EmployeeEduExperienceVo;
import com.yundaren.user.vo.EmployeeJobExperienceVo;
import com.yundaren.user.vo.EmployeeProductVo;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;

@Slf4j
public class UserExperienceServiceImpl implements UserExperienceService {

	@Setter
	private UserExperienceBiz userExperienceBiz;

	@Override
	public void addUserExperience(List<? extends AbstractEmployeeVo> teamProjectList, long uid) {
		// 如果为空不做操作
		if (CollectionUtils.isEmpty(teamProjectList)) {
			return;
		}

		// 根据经历类型来调用Biz层新增方法
		Class objInstance = teamProjectList.get(0).getClass();
		if (objInstance.equals(EmployeeEduExperienceVo.class)) {
			userExperienceBiz.addEduExperience((List<EmployeeEduExperienceVo>) teamProjectList, uid);
			return;
		} else if (objInstance.equals(EmployeeJobExperienceVo.class)) {
			userExperienceBiz.addJobExperience((List<EmployeeJobExperienceVo>) teamProjectList, uid);
			return;
		} else if (objInstance.equals(EmployeeProductVo.class)) {
			userExperienceBiz.addEmployeeProduct((List<EmployeeProductVo>) teamProjectList, uid);
			return;
		} else if (objInstance.equals(EmployeeTeamProjectExperienceVo.class)) {
			userExperienceBiz.addTeamProject((List<EmployeeTeamProjectExperienceVo>) teamProjectList, uid);
			return;
		}
	}

	@Override
	public <T extends AbstractEmployeeVo> List<T> getUserExpListById(long uid, Class<T> clz) {
		List userExpList = new ArrayList();
		if (clz.equals(EmployeeEduExperienceVo.class)) {
			userExpList = userExperienceBiz.getEduExpListByUID(uid);
			return userExpList;
		} else if (clz.equals(EmployeeJobExperienceVo.class)) {
			userExpList = userExperienceBiz.getJobExpListByUID(uid);
			return userExpList;
		} else if (clz.equals(EmployeeProductVo.class)) {
			userExpList = userExperienceBiz.getEmployeeProListByUID(uid);
			return userExpList;
		} else if (clz.equals(EmployeeTeamProjectExperienceVo.class)) {
			userExpList = userExperienceBiz.getTeamProjectListByUID(uid);
			return userExpList;
		}
		return userExpList;
	}

	@Override
	public void deleteUserExpById(long uid, int ranking, Class<?> clz) {
		// 根据经历类型来调用Biz层删除方法
		if (clz.equals(EmployeeEduExperienceVo.class)) {
			userExperienceBiz.deleteEduExperience(uid, ranking);
			return;
		} else if (clz.equals(EmployeeJobExperienceVo.class)) {
			userExperienceBiz.deleteJobExperience(uid, ranking);
			return;
		} else if (clz.equals(EmployeeProductVo.class)) {
			userExperienceBiz.deleteEmployeeProduct(uid, ranking);
			return;
		} else if (clz.equals(EmployeeTeamProjectExperienceVo.class)) {
			userExperienceBiz.deleteTeamProject(uid, ranking);
			return;
		}
	}

	
	@Override
	public Map<Long,List<EmployeeTeamProjectExperienceVo>>  getAllDisplayProviderProjects(int userType){
		return userExperienceBiz.getAllDisplayProviderProject(userType);
	}

	@Override
	public void deleteUserAllExp(long uid) {
		userExperienceBiz.deleteUserAllExp(uid);
	}

	@Override
	public int updateProviderExperienceBatch(List<EmployeeTeamProjectExperienceVo> providerExperienceList,long uid) {
		// TODO Auto-generated method stub
		return userExperienceBiz.updateProviderExperienceBatch(providerExperienceList,uid);
	}
}
