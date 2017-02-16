package com.yundaren.support.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yundaren.common.util.PageResult;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.support.vo.WeixinProjectVo;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

/**
 * 
 * @author kai.xu 需求管理
 */
public interface ProjectService {

	String addProject(UserInfoVo userInfo, ProjectVo projectVo);

	/**
	 * 查询需求列表
	 * 
	 * @param userId
	 *            用户ID
	 * @param opType
	 *            0查询所有，1查询我的发包，2查询我的接包
	 * @param type
	 *            需求类型
	 * @param status
	 *            显示状态Code 0ALL，1竞标中，2工作中，3已完成
	 */
	List<ProjectVo> getProjectList(PageResult page, long userId, int opType, String type, int status);

	List<ProjectVo> getAllProjectList(String type, int status, PageResult page);

	int getAllProjectListCount(String type, int status);

	int getProjectCount(String type, int status);

	int getPublishListCount(long userId);

	int getJoinListCount(long userId, String type, int status);

	/**
	 * 获取项目详情信息
	 */
	ProjectVo getProjectDetailsById(String id);

	/**
	 * 获取项目基本信息
	 */
	ProjectVo getProjectInfoById(String id);

	/**
	 * 返回项目数据统计
	 */
	Map getTotalCount();

	long joinProject(ProjectJoinVo projectJoinVo);

	/**
	 * 选标
	 */
	boolean chooseProjectJoin(long joinUserId, ProjectVo projectVo);

	/**
	 * 更新审核状态 checkerId 审核人ID operateType 0通过 1打回
	 */
	boolean update2CheckStatus(int operateType, long checkerId, String projectId, String reason);

	/**
	 * 托管资金确认
	 */
	void updateTrusteeInfo(String projectId, double trusteeAmount);

	/**
	 * 根据项目ID和用户ID获取用户的竞标信息
	 */
	ProjectJoinVo getJoinInfo(String projectId, long userId);

	/**
	 * 得到当前项目选中的竞标信息
	 */
	ProjectJoinVo getSelectedJoinInfo(String projectId);

	/**
	 * 撤销选标
	 */
	void cancelSelectedJoin(String projectId);

	/**
	 * 修改发标信息
	 */
	void modifyProjectInfo(ProjectVo projectVo);

	/**
	 * 补充发标内容
	 */
	void supplementProject(String pid, String content, String attachmentUrl);

	/**
	 * 修改竞标信息
	 */
	void modifyJoinInfo(ProjectJoinVo joinVo);

	/**
	 * 关闭竞标超时的项目
	 */
	void updateTimeoutProject();

	/**
	 * 服务商申请开发完工
	 */
	void update2WokingDone(String projectId);

	/**
	 * 雇主开发验收
	 * 
	 * @param operate
	 *            0通过 1未通过
	 */
	void update2Accept(String projectId, int operate, String acceptResult);

	/**
	 * 确认工作完成，管理员审核付款
	 * 
	 * @param amount
	 *            最终实际交付金额
	 * @param peroid
	 *            最终实际开发周期
	 */
	void update2Finish(String projectId, double amount, int peroid);

	/**
	 * 预约顾问
	 */
	long weixinRequest(HttpServletRequest request, WeixinProjectVo weixinProjectVo);

	/**
	 * 修改竞标备注
	 */
	void modifyJoinRemark(String pid, long employeeId, String remark);

	/**
	 * 淘汰竞标
	 */
	void kickJoin(String pid, long employeeId);

	/**
	 * 取消淘汰竞标
	 */
	void kickJoinCancel(String pid, long employeeId);
	
	/**
	 * 竞标截止日即将到期邮件提醒发标人
	 */
	void noticeBidTimeout();
	
	/**
	 * 首页随机展示项目
	 */
	List<ProjectVo> getRecommendProjectList();
	
	/**
	 * 获取预约列表
	 */
	List<WeixinProjectVo> getReserveList(int status, PageResult page);
	
	int getReserveListCount(int status);
	
	WeixinProjectVo getReserveByID(long id);
	
	/**
	 * 更新预约信息
	 */
	int updateReserveByID(WeixinProjectVo reserve);
	
	/**
	 * 预约项目发布
	 * @return 发布项目ID
	 */
	String reservePublish(long reserveId, ProjectVo projectVo, UserInfoVo userInfoVo);
	
	/**
	 * 关闭项目
	 */
	void closeProject(String id,String closeReason);
	
	/**
	 * 调整项目排序
	 * @return 当前项目的排名Index
	 */
	int rankingProject(String id,int ranking);
	
	/**
	 * 删除项目
	 */
	void deleteProject(String id);
}
