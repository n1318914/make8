package com.yundaren.api.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.service.FlinkService;
import com.yundaren.basedata.service.RegionService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.basedata.vo.FlinkVo;
import com.yundaren.basedata.vo.RegionVo;
import com.yundaren.cache.ActiveCodeCache;
import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.RegexUtil;
import com.yundaren.common.util.DateUtil;
import com.yundaren.common.util.EmailSender;
import com.yundaren.common.util.ImageCodeUtil;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.EmailSendConfig;
import com.yundaren.user.service.UserExperienceService;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;

@Controller
public class BaseDataRest {

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private RegionService regionService;

	@Setter
	private DictService dictService;

	@Setter
	private EmailSendConfig emailSendConfig;

	@Setter
	private UserExperienceService userExperienceService;

	@Setter
	private FlinkService flinkService;

	/**
	 * 获取所有省份
	 */
	@RequestMapping(value = APIConstants.REGION_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> listProvince(HttpServletRequest request) throws IOException {
		List<RegionVo> resultList = regionService.getAllProvinces();
		return new ResponseEntity<List>(resultList, HttpStatus.OK);
	}

	/**
	 * 根据省ID获取所属城市
	 */
	@RequestMapping(value = APIConstants.REGION_CITYS, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> view(HttpServletRequest request, long id) throws IOException {
		List<RegionVo> resultList = regionService.getCitysByProvinceId(id);
		return new ResponseEntity<List>(resultList, HttpStatus.OK);
	}

	/**
	 * 获取所有擅长领域
	 */
	@RequestMapping(value = APIConstants.TAG_CASETYPE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> getAllCaseType(HttpServletRequest request) throws IOException {
		// 获取服务商服务领域信息
		List<DictItemVo> dictItemList = dictService.getDictItemByType(CommonConstants.SERVICE_FIELD);
		return new ResponseEntity<List>(dictItemList, HttpStatus.OK);
	}

	/** 查询所有友链 **/
	@RequestMapping(value = APIConstants.FLINK_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> getFlinkList(HttpServletRequest request) {
		List<FlinkVo> listData = flinkService.getAllFlink();
		return new ResponseEntity<List>(listData, HttpStatus.OK);
	}

	/** 新增友链 **/
	@RequestMapping(value = APIConstants.FLINK_ADD, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addFlink(HttpServletRequest request, FlinkVo flinkVo) {
		String logoImg = (String) request.getSession().getAttribute(CommonConstants.UPLOAD_IMG_FLINK_LOGO);
		flinkVo.setLogo(logoImg);
		flinkService.addFlink(flinkVo);
		return new ResponseEntity(HttpStatus.OK);
	}

	/** 删除友链 **/
	@RequestMapping(value = APIConstants.FLINK_DEL, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity delFlink(HttpServletRequest request, int id) {
		flinkService.deleteFlink(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	/** 修改友链 **/
	@RequestMapping(value = APIConstants.FLINK_MODIFY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modifyFlink(HttpServletRequest request, FlinkVo flinkVo) {
		flinkService.modifyFlink(flinkVo);
		return new ResponseEntity(HttpStatus.OK);
	}

	/** 友链排序 **/
	@RequestMapping(value = APIConstants.FLINK_ORDER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity orderFlink(HttpServletRequest request, ListBaseDataParam listFlink) {
		for (FlinkVo fvo : listFlink.getList()) {
			flinkService.modifyFlink(fvo);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 获取服务器时间
	 */
	@RequestMapping(value = APIConstants.COMMON_SERVER_TIME, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity hostTime(HttpServletRequest request) throws IOException {
		String datetimeStr = DateUtil.formatTime(new Date(), DateUtil.DATA_STAND_FORMAT_STR);
		return new ResponseEntity(datetimeStr, HttpStatus.OK);
	}

	/**
	 * 发送邮箱验证绑定
	 */
	@RequestMapping(value = APIConstants.SEND_MAIL_VCODE, method = RequestMethod.GET)
	@ResponseBody
	public void sendMailVCode(String email) {
		if (DomainConfig.getIsProduceEnvironment()) {
			if (RegexUtil.isEmail(email)) {
				// 生成邮件验证码
				String vcode = String.valueOf(new Random().nextInt(9000) + 1000);
				ActiveCodeCache.getInstance().add(email, vcode);

				StringBuffer sb = new StringBuffer();
				sb.append("<p>尊敬的会员，您的邮箱验证码为<font color='red'><b>" + vcode
						+ "</b></font>，请在10分钟内完成验证，勿将验证码透露给他人，谢谢！</p>");
				sb.append("<p></p>");

				EmailSendConfig mailConfig = emailSendConfig.clone();
				mailConfig.setReceiver(email);
				EmailSender.send(mailConfig, "[码客帮]邮箱验证", sb.toString());
			}
		} else {
			// 测试环境邮箱验证码为1111
			ActiveCodeCache.getInstance().add(email, "1111");
		}
	}

	/**
	 * 校验图片验证码
	 */
	@RequestMapping(value = APIConstants.CAPTCHA_IMG_CHECK, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> checkCaptcha(HttpServletRequest request, String vCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 校验验证码
		boolean iRet = ImageCodeUtil.checkRandomCode(request, vCode);
		if (!iRet) {
			result = ResponseMapUtil.getFailedResponseMap("验证码错误");
		}
		request.getSession().setAttribute("ischeckCaptchaOK", true);
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 获取所有标签信息
	 */
	@RequestMapping(value = APIConstants.COMMON_LABEL_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity labelList(HttpServletRequest request) throws IOException {
		List<DictItemVo> listDictItem = dictService.getAllDictItem();
		return new ResponseEntity(listDictItem, HttpStatus.OK);
	}

	/**
	 * 获取所有标签信息
	 */
	@RequestMapping(value = "/api/test", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity doTest(HttpServletRequest request) throws IOException {

		List listData = new ArrayList();
		EmployeeTeamProjectExperienceVo vo = new EmployeeTeamProjectExperienceVo();
		vo.setUserId(1);
		vo.setProjectName("frist t");
		vo.setLink("http://waibao.me");
		vo.setDescription("OK");
		vo.setStartTime("2015");
		vo.setEndTime("2016");
		listData.add(vo);

		EmployeeTeamProjectExperienceVo vo1 = new EmployeeTeamProjectExperienceVo();
		vo1.setUserId(1);
		vo1.setProjectName("st t");
		vo1.setLink("http://waibao.me");
		vo1.setDescription("OK");
		vo1.setStartTime("2015");
		vo1.setEndTime("2016");
		listData.add(vo1);

//		userExperienceService.addUserExperience(listData, 1);
//		System.out
//				.println(userExperienceService.getUserExpListById(1, EmployeeTeamProjectExperienceVo.class));

		return new ResponseEntity("", HttpStatus.OK);
	}
}

@Data
class ListBaseDataParam {
	private List<FlinkVo> list;
}