package com.yundaren.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.vo.ExamAblityVo;
import com.yundaren.support.vo.ExamPaperVo;

/**
 * E代测工具类
 */
@Slf4j
public class EdaiceUtil {
	// 登录
	private static final String LOGIN_URL = "/mcgi/user/company_login";
	// 获取试卷
	private static final String GET_PAPERS_URL = "/mcgi/topic/get_test_papers";
	// 创建测试
	private static final String CREATE_TEST_URL = "/mcgi/test/invite_test";
	// 获取考试状态
	private static final String GET_RESULT_INFO = "/mcgi/test/get_test_info";
	// 考试状态--正在测试
	private static final String STATUS_STARTING = "starting";
	// 考试状态--未开始
	private static final String STATUS_NOSTARTING = "noStarting";
	
	@Getter
	@Setter
	private static String host;
	@Getter
	@Setter
	private static String loginName;
	@Getter
	@Setter
	private static String loginPwd;
	@Getter
	@Setter
	private static String isCameraMonitor;

	private static String sessionId;
	
	private EdaiceUtil(){};

	/**
	 * 登录
	 */
	public static void login() {
		try {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("companyId", loginName);
			paramsMap.put("password", loginPwd);
			String resultJson = HttpProtocolUtil.getContent(host + LOGIN_URL, paramsMap, "GET");
			if (!StringUtils.isEmpty(checkResult(resultJson))) {
				JSONObject jsonObject = JSONObject.fromObject(resultJson);
				jsonObject = jsonObject.getJSONObject("data");
				sessionId = jsonObject.getString("sessionId");
				log.info("login Edaice successful, sessionId is {}", sessionId);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 获取试卷列表
	 * 
	 * @param testPaperId
	 * @param testPersonnel
	 * @param telphone
	 * @param email
	 */
	public static List<ExamPaperVo> getExamPapers() {
		List<ExamPaperVo> listExamPaper = new ArrayList<ExamPaperVo>();

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("sessionId", sessionId);
		String resultJson = HttpProtocolUtil.getContent(host + GET_PAPERS_URL, paramsMap, "GET");
		if (!StringUtils.isEmpty(checkResult(resultJson))) {
			JSONObject jsonObject = JSONObject.fromObject(resultJson);
			JSONArray jsonArray = jsonObject.getJSONArray("data");

			// 解析试卷列表
			for (int i = 0; i < jsonArray.size(); i++) {
				ExamPaperVo paper = new ExamPaperVo();
				String paperName = "";
				try {
					JSONObject itemObj = jsonArray.getJSONObject(i);

					paper.setId(itemObj.getString("testPaperId"));
					paperName = itemObj.getString("testPaperName");
					paper.setName(paperName);

					// 试卷命名 "JAVA_1_初级" "PHP_2_中级"
					String skill = paperName.substring(0, paperName.indexOf("_"));
					int grade = Integer.parseInt(paperName.substring(paperName.indexOf("_") + 1,
							paperName.indexOf("_") + 2));
					paper.setSkill(skill);
					paper.setGrade(grade);
					paper.setStatus(1);
					listExamPaper.add(paper);
				} catch (Exception e) {
					log.warn("generate paper name failed {}", paperName);
				}
			}
		}

		return listExamPaper;
	}

	/**
	 * 创建测试
	 * 
	 * @param testPaperId
	 * @param testPersonnel
	 * @param telphone
	 * @param email
	 */
	public static String createTest(String testPaperId, String testPersonnel, String telphone, String email) {
		String testId = "";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("sessionId", sessionId);
		paramsMap.put("testPaperId", testPaperId);
		paramsMap.put("testPersonnel", testPersonnel);
		paramsMap.put("telphone", telphone);
		paramsMap.put("email", email);
		paramsMap.put("effectiveTime", "7");
		paramsMap.put("school", "make8");
		paramsMap.put("education", "make8");
		paramsMap.put("graduationTime", "8");
		paramsMap.put("completeUrl", DomainConfig.getHost());
		paramsMap.put("mailTheme", "码客帮邀请您参加能力测试");
		paramsMap.put("isCameraMonitor", isCameraMonitor);
		paramsMap.put("mailContent", "<b>您参加测试时的姓名为:<font color='red'>" + testPersonnel
				+ "</font>，码客帮祝您好运！</b>");
		String resultJson = HttpProtocolUtil.getContent(host + CREATE_TEST_URL, paramsMap, "GET");
		if (!StringUtils.isEmpty(checkResult(resultJson))) {
			JSONObject jsonObject = JSONObject.fromObject(resultJson);
			jsonObject = jsonObject.getJSONObject("data");
			testId = jsonObject.getString("testId");
		}
		return testId;
	}

	/**
	 * 获取测试状态
	 */
	public static void getTestResult(List<ExamAblityVo> listExam) {
		Map<String, String> paramsMap = new HashMap<String, String>();

		String strIds = "";
		for (ExamAblityVo exam : listExam) {
			strIds += exam.getId() + ",";
		}
		strIds = strIds.substring(0, strIds.length() - 1);

		paramsMap.put("sessionId", sessionId);
		paramsMap.put("testId", strIds);
		String resultJson = HttpProtocolUtil.getContent(host + GET_RESULT_INFO, paramsMap, "GET");
		if (!StringUtils.isEmpty(checkResult(resultJson))) {
			JSONObject jsonObject = JSONObject.fromObject(resultJson);
			JSONArray jsonArray = jsonObject.getJSONArray("data");

			Map<String, JSONObject> dataMap = new HashMap<String, JSONObject>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject itemObj = jsonArray.getJSONObject(i);
				String testId = itemObj.getString("testId");

				dataMap.put(testId, itemObj);
			}

			// 封装返回结果
			for (ExamAblityVo exam : listExam) {
				String status = dataMap.get(exam.getId()).getString("testStatus");
				Object score = dataMap.get(exam.getId()).get("score");
				Object startTime = dataMap.get(exam.getId()).get("testStartTime");
				Object stopTime = dataMap.get(exam.getId()).get("testStopTime");

				if (score != null) {
					exam.setScore((int)score);
				}
				if (startTime != null) {
					exam.setStartTime(DateUtil.parseDateFromString((String) startTime,
							DateUtil.DATA_STAND_FORMAT_STR));
				}
				if (stopTime != null) {
					exam.setEndTime(DateUtil.parseDateFromString((String) stopTime,
							DateUtil.DATA_STAND_FORMAT_STR));
				}
				if(status.equals(STATUS_STARTING)){
					status = "正在测试";
				}
				if(status.equals(STATUS_NOSTARTING)){
					status = "未开始";
				}
				exam.setStatus(status);
			}
		}
	}

	private static String checkResult(String resultJson) {
		// 如果结果为空
		if (StringUtils.isEmpty(resultJson)) {
			log.error("result is empty.");
			return null;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj = JSONObject.fromObject(resultJson);
		int resultCode = jsonObj.getInt("errcode");

		if (resultCode != 0) {
			log.error("error mesg:" + resultJson);
			return null;
		}
		return "OK";
	}
}
