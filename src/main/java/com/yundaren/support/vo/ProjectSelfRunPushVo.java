package com.yundaren.support.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectSelfRunPushVo {
	// 邀请的开发者ID
	private long developerId;
	// 邀请状态 0消息未读，1消息已读，2接受邀请，3拒绝邀请, 4已承接开发 5主动报名 6主动报名选中 7 主动报名选中被撤销
	private int status = -1;
	// 接受邀请时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date joinTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;
	// 创建者ID
	private long creatorId;
	// 预约关联的项目ID
	private String projectId;

	private UserInfoVo developer;
	private UserInfoVo creator;
	private String projectName;
	private String projectStatus;
	
	private String remark;
	private int isAlternative;
	private String name;
	private String companyName;
	private int category;
	
	//2016-04-02 added by peton 胜任理由
	private String joinPlan;
	
	//2016-04-21 参与的角色
	private String enrollRole;
	private List<DictItemVo> enrollRoleList;
	
    //2016-05-10实际选中的角色
	private String chosenRole;
	private List<DictItemVo> chosenRoleList;
	
	//2016-05-18
	private int isChosen = 0;
}
