package com.yundaren.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yundaren.support.config.DomainConfig;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.UserInfoVo;

@Slf4j
@Component
public class GrowingJob {

	private static Random random = new Random();
	private static char[] charSequence_digt = "0123456789".toCharArray();
	private static char[] charSequence = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	private static Map<String, String> monthGrowMap = new HashMap<String, String>();
	static {
		monthGrowMap.put("2015-11", "5-10");
		monthGrowMap.put("2015-12", "10-20");
		monthGrowMap.put("2016-01", "20-30");
		monthGrowMap.put("2016-02", "35-40");
		monthGrowMap.put("2016-03", "40-60");
		monthGrowMap.put("2016-04", "50-70");
		monthGrowMap.put("2016-05", "60-80");
		monthGrowMap.put("2016-06", "60-80");
		monthGrowMap.put("2016-07", "20-40");
		monthGrowMap.put("2016-08", "20-40");
		monthGrowMap.put("2016-09", "20-40");
		monthGrowMap.put("2016-10", "20-40");
		monthGrowMap.put("2016-11", "20-40");
		monthGrowMap.put("2016-12", "20-40");
	}

	private static Map<String, Integer> emailSuffix = new HashMap<String, Integer>();
	static {
		emailSuffix.put("@qq.com", 6);
		emailSuffix.put("@163.com", 3);
		emailSuffix.put("@126.com", 2);
		emailSuffix.put("@gmail.com", 1);
		emailSuffix.put("@yahoo.com", 1);
		emailSuffix.put("@sina.com", 1);
		emailSuffix.put("@sohu.com", 1);
		emailSuffix.put("@aliyun.com", 1);
		emailSuffix.put("@foxmail.com", 1);
		emailSuffix.put("@tom.com", 1);
	}

	@Autowired
	private UserService userService;

	@Scheduled(cron = "0 0 3 * * ?")
	public void timingUserDayUp() throws Exception {

		if (DomainConfig.getIsProduceEnvironment()) {
			// 当前时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String range = monthGrowMap.get((sdf.format(new Date())));
			int min = Integer.parseInt(range.split("-")[0]);
			int max = Integer.parseInt(range.split("-")[1]);
			int loopSize = random.nextInt(max) % (max - min + 1) + min;
			for (int i = 0; i < loopSize; i++) {
				// 随机8-12分钟
				int sleepMin = 8;
				int sleepMax = 12;
				int sleep = random.nextInt(sleepMax) % (sleepMax - sleepMin + 1) + sleepMin;
				Thread.sleep(sleep * 60 * 1000);

				UserInfoVo userInfo = new UserInfoVo();
				String mailFix = getRandomEmail();
				String indexFix;
				if (mailFix.contains("qq.com")) {
					indexFix = generateQQ();
				} else {
					indexFix = generateAccount();
				}
				String email = indexFix + mailFix;
				userInfo.setFileSecretKey("AAAA");
				userInfo.setEmail(email);
				userService.createUserInfo(userInfo);
				
			}
			log.info("end dayup size : " + loopSize);
		}
	}

	private static String generateQQ() {
		String resultStr = "";
		for (int i = 0; i < 9; i++) {
			int index = random.nextInt(charSequence_digt.length);
			resultStr += charSequence_digt[index];
		}
		return resultStr;
	}

	private static String generateAccount() {
		String resultStr = "";
		for (int i = 0; i < 6; i++) {
			int index = random.nextInt(charSequence.length);
			resultStr += charSequence[index];
		}
		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(charSequence_digt.length);
			resultStr += charSequence_digt[index];
		}
		return resultStr;
	}

	private static String getRandomEmail() {
		List<String> listEmailPool = new ArrayList<String>();
		for (Entry<String, Integer> item : emailSuffix.entrySet()) {
			int weights = item.getValue();
			while (weights > 0) {
				listEmailPool.add(item.getKey());
				weights--;
			}
		}
		Collections.shuffle(listEmailPool);
		return (String) listEmailPool.toArray()[0];
	}

}
