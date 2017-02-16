package com.yundaren.user.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.user.dao.UserExperienceDao;
import com.yundaren.user.po.EmployeeEduExperiencePo;
import com.yundaren.user.po.EmployeeJobExperiencePo;
import com.yundaren.user.po.EmployeeProductPo;
import com.yundaren.user.po.EmployeeTeamProjectExperiencePo;
import com.yundaren.user.vo.EmployeeEduExperienceVo;
import com.yundaren.user.vo.EmployeeJobExperienceVo;
import com.yundaren.user.vo.EmployeeProductVo;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;

@Slf4j
public class UserExperienceBiz {

	@Setter
	private UserExperienceDao userExperienceDao;

	public List<EmployeeEduExperienceVo> getEduExpListByUID(long uid) {
		List<EmployeeEduExperienceVo> listUserInfoVo = new ArrayList<EmployeeEduExperienceVo>();
		List<EmployeeEduExperiencePo> listUserInfoPo = userExperienceDao.getEduExpListByUID(uid);

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, EmployeeEduExperienceVo.class);
		}
		return listUserInfoVo;
	}

	public List<EmployeeJobExperienceVo> getJobExpListByUID(long uid) {
		List<EmployeeJobExperienceVo> listUserInfoVo = new ArrayList<EmployeeJobExperienceVo>();
		List<EmployeeJobExperiencePo> listUserInfoPo = userExperienceDao.getJobExpListByUID(uid);

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, EmployeeJobExperienceVo.class);
		}
		return listUserInfoVo;
	}

	public List<EmployeeProductVo> getEmployeeProListByUID(long uid) {
		List<EmployeeProductVo> listUserInfoVo = new ArrayList<EmployeeProductVo>();
		List<EmployeeProductPo> listUserInfoPo = userExperienceDao.getEmployeeProListByUID(uid);

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, EmployeeProductVo.class);
		}
		return listUserInfoVo;
	}

	public List<EmployeeTeamProjectExperienceVo> getTeamProjectListByUID(long uid) {
		List<EmployeeTeamProjectExperienceVo> listUserInfoVo = new ArrayList<EmployeeTeamProjectExperienceVo>();
		List<EmployeeTeamProjectExperiencePo> listUserInfoPo = userExperienceDao.getTeamProjectListByUID(uid);

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, EmployeeTeamProjectExperienceVo.class);
		}
		return listUserInfoVo;
	}

	public int addEduExperience(List<EmployeeEduExperienceVo> eduList, long uid) {
		List<EmployeeEduExperiencePo> listUserInfoPo = new ArrayList<EmployeeEduExperiencePo>();
		if (!CollectionUtils.isEmpty(eduList)) {
			listUserInfoPo = BeanMapper.mapList(eduList, EmployeeEduExperiencePo.class);
		}
		return userExperienceDao.addEduExperience(listUserInfoPo, uid);
	}

	public int addJobExperience(List<EmployeeJobExperienceVo> jobList, long uid) {
		List<EmployeeJobExperiencePo> listUserInfoPo = new ArrayList<EmployeeJobExperiencePo>();
		if (!CollectionUtils.isEmpty(jobList)) {
			listUserInfoPo = BeanMapper.mapList(jobList, EmployeeJobExperiencePo.class);
		}
		return userExperienceDao.addJobExperience(listUserInfoPo, uid);
	}

	public int addEmployeeProduct(List<EmployeeProductVo> productList, long uid) {
		List<EmployeeProductPo> listUserInfoPo = new ArrayList<EmployeeProductPo>();
		if (!CollectionUtils.isEmpty(productList)) {
			listUserInfoPo = BeanMapper.mapList(productList, EmployeeProductPo.class);
		}
		return userExperienceDao.addEmployeeProduct(listUserInfoPo, uid);
	}

	public int addTeamProject(List<EmployeeTeamProjectExperienceVo> teamProjectList, long uid) {
		List<EmployeeTeamProjectExperiencePo> listUserInfoPo = new ArrayList<EmployeeTeamProjectExperiencePo>();
		if (!CollectionUtils.isEmpty(teamProjectList)) {
			listUserInfoPo = BeanMapper.mapList(teamProjectList, EmployeeTeamProjectExperiencePo.class);
		}
		return userExperienceDao.addTeamProject(listUserInfoPo, uid);
	}

	public int deleteEduExperience(long uid, int ranking) {
		return userExperienceDao.deleteEduExperience(uid, ranking);
	}

	public int deleteJobExperience(long uid, int ranking) {
		return userExperienceDao.deleteJobExperience(uid, ranking);
	}

	public int deleteEmployeeProduct(long uid, int ranking) {
		return userExperienceDao.deleteEmployeeProduct(uid, ranking);
	}

	public int deleteTeamProject(long uid, int ranking) {
		return userExperienceDao.deleteTeamProject(uid, ranking);
	}
	
	public int deleteUserAllExp(long uid) {
		return userExperienceDao.deleteUserAllExp(uid);
	}
	
	public Map<Long,List<EmployeeTeamProjectExperienceVo>>  getAllDisplayProviderProject(int userType){
		Map<Long,List<EmployeeTeamProjectExperienceVo>> employeeTeamProjectExperienceMaps = new HashMap<Long,List<EmployeeTeamProjectExperienceVo>>();
		List<EmployeeTeamProjectExperienceVo> employeeTeamProjectExperienceVos = new ArrayList<EmployeeTeamProjectExperienceVo>();
		List<EmployeeTeamProjectExperiencePo> employeeTeamProjectExperiencPos = userExperienceDao.getListAllProviderProjectInfo(userType);

		if (!CollectionUtils.isEmpty(employeeTeamProjectExperiencPos)) {
			employeeTeamProjectExperienceVos = BeanMapper.mapList(employeeTeamProjectExperiencPos, EmployeeTeamProjectExperienceVo.class);
		}

		for(EmployeeTeamProjectExperienceVo vo:employeeTeamProjectExperienceVos){
			if(employeeTeamProjectExperienceMaps.get(vo.getUserId())==null)employeeTeamProjectExperienceMaps.put(vo.getUserId(), new ArrayList<EmployeeTeamProjectExperienceVo>());
			employeeTeamProjectExperienceMaps.get(vo.getUserId()).add(vo);
		}
		return employeeTeamProjectExperienceMaps;
	}
	
	public int updateProviderExperienceBatch(List<EmployeeTeamProjectExperienceVo> providerExperienceList,long uid){
		List<EmployeeTeamProjectExperiencePo>  providerExperiencePoList = BeanMapper.mapList(providerExperienceList, EmployeeTeamProjectExperiencePo.class);
		return userExperienceDao.updateProviderExperienceBatch(providerExperiencePoList,uid);
	}
}
