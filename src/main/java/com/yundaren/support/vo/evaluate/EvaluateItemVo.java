package com.yundaren.support.vo.evaluate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EvaluateItemVo {

	// ID
	@Getter
	@Setter
	private int id;
	// 名称
	@Getter
	@Setter
	private String name;
	// 模块ID
	@Getter
	@Setter
	private int groupId;
	@Getter
	@Setter
	// 在什么产品类型下显示(all、android、ios、web、weixin)，多个用逗号分隔
	private String showOn;
	@Getter
	@Setter
	// 价格范围(100-1000)，范围用`-`号分隔
	private String defaultPrice;
	@Getter
	@Setter
	// 周期范围(1-10)，范围用`-`号分隔
	private String defaultPeroid;
	@Getter
	@Setter
	// 安卓价格周期系数
	private float androidRate;
	@Getter
	@Setter
	// IOS价格周期系数
	private float iosRate;
	@Getter
	@Setter
	// 网站价格周期系数
	private float webRate;
	@Getter
	@Setter
	// 微信价格周期系数
	private float weixinRate;
	@Getter
	@Setter
	// 备注
	private String remark;

	private float getMaxPrice;
	private float getMinPrice;
	private float getMaxPeroid;
	private float getMinPeroid;

	public float getGetMaxPrice() {
		float result = 0;
		try {
			result = Float.parseFloat(defaultPrice.split("-")[1]);
		} catch (Exception e) {
			log.info("getGetMaxPrice faild.", e);
		}
		return result;
	}

	public float getGetMinPrice() {
		float result = 0;
		try {
			result = Float.parseFloat(defaultPrice.split("-")[0]);
		} catch (Exception e) {
			log.info("getGetMinPrice faild.", e);
		}
		return result;
	}

	public float getGetMaxPeroid() {
		float result = 0;
		try {
			result = Float.parseFloat(defaultPeroid.split("-")[1]);
		} catch (Exception e) {
			log.info("getGetMaxPeroid faild.", e);
		}
		return result;
	}

	public float getGetMinPeroid() {
		float result = 0;
		try {
			result = Float.parseFloat(defaultPeroid.split("-")[0]);
		} catch (Exception e) {
			log.info("getGetMinPeroid faild.", e);
		}
		return result;
	}
}
