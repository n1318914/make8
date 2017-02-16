package com.yundaren.api.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundaren.common.constants.APIConstants;
import com.yundaren.news.service.NewsService;
import com.yundaren.news.vo.NewsVo;

@Controller
public class NewsRest {
	@Setter
	private NewsService newsService;
	
	/**
	 * 获取所有省份
	 */
	@RequestMapping(value = APIConstants.NEWS_GET_LATEST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> listProvince(HttpServletRequest request) throws IOException {
		List<NewsVo> newsVos = newsService.getNews();
	
		return new ResponseEntity<List>(newsVos, HttpStatus.OK);
	}
	
}
