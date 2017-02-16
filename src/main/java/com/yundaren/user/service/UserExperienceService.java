package com.yundaren.user.service;

import java.util.List;
import java.util.Map;

import com.yundaren.user.vo.AbstractEmployeeVo;
import com.yundaren.user.vo.EmployeeEduExperienceVo;
import com.yundaren.user.vo.EmployeeJobExperienceVo;
import com.yundaren.user.vo.EmployeeProductVo;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;

/**
 * 
 * @author kai.xu 服务商用户经历
 */
public interface UserExperienceService {

	/**
	 * 新增服务商经历
	 */
	void addUserExperience(List<? extends AbstractEmployeeVo> teamProjectList, long uid);

	/**
	 * 获取服务商经历列表
	 */
	<T extends AbstractEmployeeVo> List<T> getUserExpListById(long uid, Class<T> clz);

	/**
	 * 更具UID和RANKING删除具体某一条用户经历
	 * 
	 * @param ranking
	 *            值如果为-1，则删除当前UID下所有数据
	 */
	void deleteUserExpById(long uid, int ranking, Class<?> clz);

	/**
	 * 删除用户所有经历
	 */
	void deleteUserAllExp(long uid);

	Map<Long,List<EmployeeTeamProjectExperienceVo>>  getAllDisplayProviderProjects(int userType);
	
	int updateProviderExperienceBatch(List<EmployeeTeamProjectExperienceVo> providerExperienceList,long uid);
}
