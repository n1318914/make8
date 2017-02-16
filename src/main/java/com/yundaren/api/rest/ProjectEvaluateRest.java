package com.yundaren.api.rest;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.EvaluateService;
import com.yundaren.support.service.ProjectEvaluateService;
import com.yundaren.support.service.ProjectService;
import com.yundaren.support.vo.ProjectEvaluateVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.support.vo.evaluate.EvaluateSimilarVo;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.UserInfoVo;

/**
 * 评价服务API
 */
@Controller
@Slf4j
public class ProjectEvaluateRest {

	@Setter
	private ProjectEvaluateService projectEvaluateService;

	@Setter
	private ProjectService projectService;

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private UserService userService;
	
	@Setter
	private EvaluateService evaluateService;

	/**
	 * 新增评价
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = APIConstants.EVALUATE_ADD, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity add(HttpServletRequest request, ProjectEvaluateVo evaluateVo)
			throws UnsupportedEncodingException {
		Map<String, Object> result = checkArgs(evaluateVo);
		if (StringUtils.isEmpty(evaluateVo.getDescription())) {
			evaluateVo.setDescription("雇主未留下任何描述");
		} else {
//			String content = URLDecoder.decode(evaluateVo.getDescription(), "UTF-8");
			String content = evaluateVo.getDescription().replaceAll("\n", "<br/>");
			evaluateVo.setDescription(content);
		}

		projectEvaluateService.addProjectEvaluate(evaluateVo);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 服务商评价列表
	 */
	@RequestMapping(value = APIConstants.EVALUATE_ALL_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity list(HttpServletRequest request, long uid) {
		List<ProjectEvaluateVo> listResult = projectEvaluateService.getEvaluateListByUID(uid);
		return new ResponseEntity(listResult, HttpStatus.OK);
	}

	/**
	 * 项目服务评价
	 */
	@RequestMapping(value = APIConstants.EVALUATE_ITEM, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity info(HttpServletRequest request, String pid) {
		ProjectEvaluateVo evaluateVo = projectEvaluateService.getEvaluateByPID(pid);
		return new ResponseEntity(evaluateVo, HttpStatus.OK);
	}
	
	/**
	 * 相似项目
	 */
	@RequestMapping(value = APIConstants.EVALUATE_SIMILAR, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity same(HttpServletRequest request, String type, String industry, float minPrice,
			float maxPrice) {
		List<EvaluateSimilarVo> listVo = evaluateService.getCaseByQuery(type, industry, minPrice, maxPrice);
		return new ResponseEntity(listVo, HttpStatus.OK);
	}

	// 校验参数是否合法
	private Map checkArgs(ProjectEvaluateVo evaluateVo) {
		float qualityScore = evaluateVo.getQualityScore();
		float speedScore = evaluateVo.getSpeedScore();
		float attitudeScore = evaluateVo.getAttitudeScore();
		String pid = evaluateVo.getProjectId();

		if (qualityScore <= 0 || speedScore <= 0 || attitudeScore <= 0 || qualityScore > 5 || speedScore > 5
				|| attitudeScore > 0 || StringUtils.isEmpty(pid)) {
			return ResponseMapUtil.getFailedResponseMap("参数不正确");
		}

		ProjectVo pVo = projectService.getProjectInfoById(pid);
		if (pVo == null) {
			return ResponseMapUtil.getFailedResponseMap("没有找到此项目");
		}

		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		// 判断是否为当前用户发布的项目
		if (pVo.getCreatorId() != userInfo.getId()) {
			return ResponseMapUtil.getFailedResponseMap("不允许执行此操作");
		}

		// 返回空表示校验通过
		return null;
	}

}
