package com.yundaren.support.vo;

import java.util.List;

import lombok.Data;

import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectInSelfRunHandlerVo{
	private String projectId;
	private String developerId;
	private String role;
	
	private List<DictItemVo> roleList;
	private UserInfoVo developer;
}
