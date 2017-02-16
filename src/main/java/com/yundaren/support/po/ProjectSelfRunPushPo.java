package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectSelfRunPushPo {
	// 邀请的开发者ID
	private long developerId;
	// 邀请状态 0消息未读，1消息已读，2接受邀请，3拒绝邀请, 4已承接开发 5主动报名
	private int status = -1;
	// 接受邀请时间
	private Date joinTime;
	private Date createTime;
	// 创建者ID
	private long creatorId;
	// 预约关联的项目ID
	private String projectId;
	
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
	
    //2016-05-10实际选中的角色
	private String chosenRole;
}
