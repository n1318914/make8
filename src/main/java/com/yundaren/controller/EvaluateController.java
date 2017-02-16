package com.yundaren.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.support.service.EvaluateService;
import com.yundaren.support.vo.evaluate.EvaluateModuleVo;

@Controller
public class EvaluateController {
	
	@Setter
	private EvaluateService evaluateService;

	/**
	 * 估价首页
	 */
	@RequestMapping(value = PageForwardConstants.EVALUATE_INDEX, method = RequestMethod.GET)
	public String flink_ftl(HttpServletRequest request) throws IOException {
		List<EvaluateModuleVo> listData = evaluateService.getAllModule();
		request.setAttribute("listData", listData);
		return "public/evaluate";
	}

	/**
	 * 获取估价
	 */
	@RequestMapping(value = PageForwardConstants.EVALUATE_DETAILS, method = RequestMethod.GET)
	public String joinus_ftl(HttpServletRequest request) throws IOException {
		return "public/get_evaluate";
	}

}
