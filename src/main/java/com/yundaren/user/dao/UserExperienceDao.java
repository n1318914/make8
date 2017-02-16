package com.yundaren.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.google.common.collect.ImmutableMap;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.user.po.EmployeeEduExperiencePo;
import com.yundaren.user.po.EmployeeJobExperiencePo;
import com.yundaren.user.po.EmployeeProductPo;
import com.yundaren.user.po.EmployeeTeamProjectExperiencePo;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;

public class UserExperienceDao extends SqlSessionDaoSupport {

	public List<EmployeeEduExperiencePo> getEduExpListByUID(long uid) {
		return getSqlSession().selectList("getEduExpListByUID", uid);
	}

	public List<EmployeeJobExperiencePo> getJobExpListByUID(long uid) {
		return getSqlSession().selectList("getJobExpListByUID", uid);
	}

	public List<EmployeeProductPo> getEmployeeProListByUID(long uid) {
		return getSqlSession().selectList("getEmployeeProListByUID", uid);
	}

	public List<EmployeeTeamProjectExperiencePo> getTeamProjectListByUID(long uid) {
		return getSqlSession().selectList("getTeamProjectListByUID", uid);
	}

	public int addEduExperience(List<EmployeeEduExperiencePo> teamProjectList, long uid) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("teamProjectList", teamProjectList);
		paramsMap.put("uid", uid);
		return getSqlSession().insert("addEduExperience", paramsMap);
	}

	public int addJobExperience(List<EmployeeJobExperiencePo> teamProjectList, long uid) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("teamProjectList", teamProjectList);
		paramsMap.put("uid", uid);
		return getSqlSession().insert("addJobExperience", paramsMap);
	}

	public int addEmployeeProduct(List<EmployeeProductPo> teamProjectList, long uid) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("teamProjectList", teamProjectList);
		paramsMap.put("uid", uid);
		return getSqlSession().insert("addEmployeeProduct", paramsMap);
	}

	public int addTeamProject(List<EmployeeTeamProjectExperiencePo> teamProjectList, long uid) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("teamProjectList", teamProjectList);
		paramsMap.put("uid", uid);
		return getSqlSession().insert("addTeamProject", paramsMap);
	}

	public int deleteEduExperience(long uid, int ranking) {
		return getSqlSession().delete("deleteEduExperience", ImmutableMap.of("uid", uid, "ranking", ranking));
	}

	public int deleteJobExperience(long uid, int ranking) {
		return getSqlSession().delete("deleteJobExperience", ImmutableMap.of("uid", uid, "ranking", ranking));
	}

	public int deleteEmployeeProduct(long uid, int ranking) {
		return getSqlSession().delete("deleteEmployeeProduct",
				ImmutableMap.of("uid", uid, "ranking", ranking));
	}

	public int deleteTeamProject(long uid, int ranking) {
		return getSqlSession().delete("deleteTeamProject", ImmutableMap.of("uid", uid, "ranking", ranking));
	}
	

	/**
	 * 服务商项目查询
	 */
	public List<EmployeeTeamProjectExperiencePo> getListAllProviderProjectInfo(int userType) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userType", userType);
		return getSqlSession().selectList("getTeamProjectListByUIDs", paramsMap);
	}
	public int deleteUserAllExp(long uid) {
		return getSqlSession().delete("deleteUserAllExp", uid);
	}
	/**
	 * 服务商项目经验删除
	 */
	public int deleteAllTeamProjectByUID(long uid){
		return getSqlSession().delete("deleteAllTeamProjectByUID", ImmutableMap.of("uid", uid));
	}
	/**
	 * 服务商项目经验更新
	 */
	public int updateProviderExperienceBatch(List<EmployeeTeamProjectExperiencePo> providerExperienceList,long uid) {
		deleteAllTeamProjectByUID(uid);
		addTeamProject(providerExperienceList, uid);
		return 1;
	}
}